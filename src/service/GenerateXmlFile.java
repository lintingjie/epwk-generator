package service;

import domain.Field;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.text.WordUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Greg.Chen on 2015/3/18.
 */
public class GenerateXmlFile extends AbsGenerate {


    @Override
    public void generate(GenerateContext context) throws IOException, TemplateException {

        Template template = context.getCfg().getTemplate("src/tlp/xml.ftl");
        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("domainPackage", context.getDomainPackage());
        data.put("className", context.getClassName());
        data.put("tableName", context.getTableName());
        data.put("id", "#{id}");
        data.put("pageNum","#{pageNum}");
        data.put("pageSize","#{pageSize}");

        List<Field> xmlFields = new ArrayList<>();
        for(Field field : context.getFields()){
            if(field.getColName().equalsIgnoreCase("createTime") || field.getColName().equalsIgnoreCase("updateTime")){
                continue;
            }
            xmlFields.add(field);
        }

        data.put("fields", xmlFields);

        print(template, data, context.getXmlPath());

        //changeConfig(context);
    }

    private void changeConfig(GenerateContext context) throws IOException {
        String path = context.getProjectPath() + "/resources/sqlMapConfig.xml";
        File xmlFile = new File(path);
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(xmlFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        String xpath = "mapper[@resource='mapper/" + context.getTableName() + ".xml']";

        Node root = document.selectSingleNode("//mappers");

        List mapperList = root.selectNodes(xpath);

        if (mapperList == null || mapperList.size() == 0) {

            Element mappers = (Element) root;

            Element mapper = mappers.addElement("mapper");
            mapper.addAttribute("resource", "mybatis/mapper/" + context.getTableName() + ".xml");

            XMLWriter writer = new XMLWriter(new FileOutputStream(xmlFile));
            writer.write(document);
            writer.close();

            return;
        }
    }

        /*Element root = document.getRootElement();
        Iterator i = root.elementIterator();

        while(i.hasNext()){
            Element mappers = (Element) i.next();

            if("mappers".equals(mappers.getName())){
                Element mapper = mappers.addElement("mapper");
                mapper.addAttribute("resource","mapper/" + context.getTableName() + ".xml");

                XMLWriter writer = new XMLWriter(new FileOutputStream(xmlFile));
                writer.write(document);
                writer.close();

                break;
            }
        }*/

}

