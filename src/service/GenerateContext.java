package service;

import com.google.common.base.CaseFormat;
import domain.Field;
import freemarker.template.Configuration;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * Created by Greg.Chen on 2015/3/18.
 */
public class GenerateContext {

    public GenerateContext() throws SQLException {

        Properties properties = getProp();

        projectName = properties.getProperty("projectName");
        facadeName = properties.getProperty("facadeName");
        feignClient = "${"+properties.getProperty("feignClient")+"}";
        configName = properties.getProperty("configName");

        tableName = properties.getProperty("tableName");
        className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.substring(2));
        lowClassName = WordUtils.uncapitalize(className);
        domainPackage = generateFacadePackageName("domain");
        servicePackage = generatePackageName("service");
        clientPackage = generateFacadePackageName("service");
        controllerPackage = generatePackageName("controller");
        viewPackage = generateFacadePackageName("view");
        requestPackage = generateFacadePackageName("request");
        feignClientPackage = generateFacadePackageName("service");
        feignClientConfig = generateFacadeConfig(properties.getProperty("feignClientConfig"));

        path = properties.getProperty("outputPath");

        projectPath = properties.getProperty("projectPath") + "/src/main";
        facadePath = properties.getProperty("facadePath") + "/src/main";

        File file = new File(path);
        if(!file.exists())
            file.mkdirs();

        domainPath = projectPath + "/java/com/epwk/entity/" + className + ".java";
        domainDetailPath = projectPath + "/java/com/epwk/vo/" + className + "DetailVo.java";
        domainPageVoPath = projectPath + "/java/com/epwk/vo/" + className + "PageVo.java";
        servicePath =  projectPath + "/java/com/epwk/service/" + className + "Service.java";
        serviceImplPath =  projectPath + "/java/com/epwk/service/impl/" + className + "ServiceImpl.java";
        xmlPath =  projectPath + "/resources/mybatis/mapper/" + className + ".xml";
        mapperPath = projectPath + "/java/com/epwk/mapper/" + className +"Mapper.java";
        mngControllerPath = path + WordUtils.capitalize(tableName) + "MngController.java";
        controllerPath = projectPath + "/java/com/epwk/controller/" + className + "Controller.java";
        mngHtmlPath = path + tableName + "Mng.html";
        mngFormHtmlPath = path + tableName + "Form.html";
        mngJSPath = path + tableName + "MngCtrl.js";
        feignClientPath = facadePath + "/java/" + clientPackage.replace(".","/") + "/" + WordUtils.capitalize(tableName) + "FeignClient.java";
        feignClientCallbackPath = facadePath + "/java/" + clientPackage.replace(".","/") + "/" + WordUtils.capitalize(tableName) + "HystrixClientFallback.java";

        viewPath = facadePath + "/java/" + viewPackage.replace(".","/") + "/" + WordUtils.capitalize(tableName) + "PageView.java";
        requestSearchFormPath = projectPath + "/java/com/epwk/form/"+ className + "SearchForm.java";
        requestCreateFormPath = projectPath + "/java/com/epwk/form/" + className  + "CreateForm.java";
        requestUpdateFormPath = projectPath + "/java/com/epwk/form/" + className + "UpdateForm.java";

        requestAddFormPath = path + WordUtils.capitalize(tableName) + "AddForm.java";
        //Freemarker configuration object
        cfg = new Configuration();

        getColNames();
    }

    private String generateFacadeConfig(String config){
        return "com." + facadeName + "." + config;
    }

    private String generateFacadePackageName(String packageName){
        return "com." + facadeName + "." + packageName;
    }

    private String generatePackageName(String packageName){
        return "com." + projectName + "." + packageName;
    }

    private void getColNames()  throws SQLException {
        List<Field> fields = new ArrayList<Field>();
        HashSet<String> columnClassNames = new HashSet<String>();

        Connection conn  = DataSourceUtils.getConn();
        Statement st = conn.createStatement();

        String sql = "select * from `" + tableName + "`";
        ResultSet rs = st.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();

        int cols = rsmd.getColumnCount();

        for(int i=0;i<cols;i++){
            String _colName = rsmd.getColumnName(i + 1);
            String colName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, _colName);
            String colType = rsmd.getColumnClassName(i + 1);
            String _colType = rsmd.getColumnClassName(i + 1);

            if(colName.equalsIgnoreCase("createTime") || colName.equalsIgnoreCase("updateTime")) {

            }

            if(colType.equals("java.math.BigDecimal")){
                colType = "java.lang.Double";
            }

            if(colType.equals("java.sql.Date")){
                colType = "java.util.Date";
                columnClassNames.add("com.epwk.util.DateFormat.DateSerializer");
                columnClassNames.add("com.epwk.util.DateFormat.DateDeserializer");
                columnClassNames.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                columnClassNames.add("com.fasterxml.jackson.databind.annotation.JsonDeserialize");
            }

            if(colType.equals("java.sql.Timestamp")){
                colType = "java.util.Date";
                columnClassNames.add("com.epwk.util.DateFormat.DateTimeSerializer");
                columnClassNames.add("com.epwk.util.DateFormat.DateTimeDeserializer");
                columnClassNames.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                columnClassNames.add("com.fasterxml.jackson.databind.annotation.JsonDeserialize");
            }

            columnClassNames.add(colType);


            colType = colType.substring(colType.lastIndexOf('.') + 1);
            _colType = _colType.substring(_colType.lastIndexOf('.') + 1);


            fields.add(new Field(_colName, colName, _colType, colType, rsmd.isAutoIncrement(i+1), rsmd.isNullable(i+1)));
        }

        System.out.println("列名:" + fields);
        System.out.println("列类型:" + columnClassNames);

        this.fields =fields;
        this.columnClassNames = columnClassNames;

    }

    public Properties getProp(){
        Properties properties = new Properties();
        InputStream inputStream = GenerateContext.class.getClassLoader().getResourceAsStream("config.properties");
        if(inputStream != null){
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return  properties;
    }

    private List<Field> fields;
    private HashSet<String> columnClassNames;
    private Configuration cfg;

    private String tableName;
    private String path;
    private String domainPath;
    private String servicePath;
    private String xmlPath;
    private String domainPackage;
    private String servicePackage;
    private String mngControllerPath;
    private String controllerPackage;
    private String controllerPath;
    private String mngHtmlPath;
    private String mngFormHtmlPath;
    private String mngJSPath;
    private String viewPackage;
    private String requestPackage;
    private String viewPath;
    private String requestSearchFormPath;
    private String requestAddFormPath;
    private String requestCreateFormPath;
    private String requestUpdateFormPath;

    private String projectPath;
    private String facadePath;

    private String projectName;
    private String facadeName;
    private String feignClient;
    private String configName;
    private String feignClientPackage;
    private String feignClientConfig;
    private String feignClientPath;
    private String feignClientCallbackPath;
    private String clientPackage;
    private String className;
    private String mapperPath;
    private String serviceImplPath;
    private String lowClassName;
    private String domainDetailPath;
    private String domainPageVoPath;

    public String getFeignClientPath() {
        return feignClientPath;
    }

    public void setFeignClientPath(String feignClientPath) {
        this.feignClientPath = feignClientPath;
    }

    public String getFeignClientCallbackPath() {
        return feignClientCallbackPath;
    }

    public void setFeignClientCallbackPath(String feignClientCallbackPath) {
        this.feignClientCallbackPath = feignClientCallbackPath;
    }

    public String getClientPackage() {
        return clientPackage;
    }

    public void setClientPackage(String clientPackage) {
        this.clientPackage = clientPackage;
    }

    public String getFeignClient() {
        return feignClient;
    }

    public void setFeignClient(String feignClient) {
        this.feignClient = feignClient;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getFeignClientPackage() {
        return feignClientPackage;
    }

    public void setFeignClientPackage(String feignClientPackage) {
        this.feignClientPackage = feignClientPackage;
    }

    public String getFeignClientConfig() {
        return feignClientConfig;
    }

    public void setFeignClientConfig(String feignClientConfig) {
        this.feignClientConfig = feignClientConfig;
    }

    public String getFacadePath() {
        return facadePath;
    }

    public void setFacadePath(String facadePath) {
        this.facadePath = facadePath;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFacadeName() {
        return facadeName;
    }

    public void setFacadeName(String facadeName) {
        this.facadeName = facadeName;
    }

    public String getMngJSPath() {
        return mngJSPath;
    }

    public void setMngJSPath(String mngJSPath) {
        this.mngJSPath = mngJSPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomainPath() {
        return domainPath;
    }

    public void setDomainPath(String domainPath) {
        this.domainPath = domainPath;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getDomainPackage() {
        return domainPackage;
    }

    public void setDomainPackage(String domainPackage) {
        this.domainPackage = domainPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getXmlPath() {
        return xmlPath;
    }


    public String getMngControllerPath() {
        return mngControllerPath;
    }

    public void setMngControllerPath(String mngControllerPath) {
        this.mngControllerPath = mngControllerPath;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public HashSet<String> getColumnClassNames() {
        return columnClassNames;
    }

    public void setColumnClassNames(HashSet<String> columnClassNames) {
        this.columnClassNames = columnClassNames;
    }

    public Configuration getCfg() {
        return cfg;
    }

    public void setCfg(Configuration cfg) {
        this.cfg = cfg;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getControllerPath() {
        return controllerPath;
    }

    public void setControllerPath(String controllerPath) {
        this.controllerPath = controllerPath;
    }

    public String getMngHtmlPath() {
        return mngHtmlPath;
    }

    public void setMngHtmlPath(String mngHtmlPath) {
        this.mngHtmlPath = mngHtmlPath;
    }

    public String getMngFormHtmlPath() {
        return mngFormHtmlPath;
    }

    public void setMngFormHtmlPath(String mngFormHtmlPath) {
        this.mngFormHtmlPath = mngFormHtmlPath;
    }

    public String getViewPackage() {
        return viewPackage;
    }

    public void setViewPackage(String viewPackage) {
        this.viewPackage = viewPackage;
    }

    public String getRequestPackage() {
        return requestPackage;
    }

    public void setRequestPackage(String requestPackage) {
        this.requestPackage = requestPackage;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public String getRequestSearchFormPath() {
        return requestSearchFormPath;
    }

    public void setRequestSearchFormPath(String requestSearchFormPath) {
        this.requestSearchFormPath = requestSearchFormPath;
    }

    public String getRequestAddFormPath() {
        return requestAddFormPath;
    }

    public void setRequestAddFormPath(String requestAddFormPath) {
        this.requestAddFormPath = requestAddFormPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getRequestCreateFormPath() {
        return requestCreateFormPath;
    }

    public void setRequestCreateFormPath(String requestCreateFormPath) {
        this.requestCreateFormPath = requestCreateFormPath;
    }

    public String getRequestUpdateFormPath() {
        return requestUpdateFormPath;
    }

    public void setRequestUpdateFormPath(String requestUpdateFormPath) {
        this.requestUpdateFormPath = requestUpdateFormPath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    public String getServiceImplPath() {
        return serviceImplPath;
    }

    public void setServiceImplPath(String serviceImplPath) {
        this.serviceImplPath = serviceImplPath;
    }

    public String getLowClassName() {
        return lowClassName;
    }

    public void setLowClassName(String lowClassName) {
        this.lowClassName = lowClassName;
    }

    public String getDomainDetailPath() {
        return domainDetailPath;
    }

    public void setDomainDetailPath(String domainDetailPath) {
        this.domainDetailPath = domainDetailPath;
    }

    public String getDomainPageVoPath() {
        return domainPageVoPath;
    }

    public void setDomainPageVoPath(String domainPageVoPath) {
        this.domainPageVoPath = domainPageVoPath;
    }
}
