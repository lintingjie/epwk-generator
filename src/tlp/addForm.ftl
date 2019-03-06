package ${requestPackage};

<#list columnClassNames as item>
import ${item};
</#list>

public class ${className}AddForm {

    <#list fields as field>
    <#if field.colName != "createTime" && field.colName != "createBy" && field.colName != "updateTime" && field.colName != "updateBy" && field.colName != "id">
    private ${field.colType} ${field.colName};
    </#if>
    </#list>

    <#list fields as field>
        <#if field.colName != "createTime" && field.colName != "createBy" && field.colName != "updateTime" && field.colName != "updateBy"  && field.colName != "id">
    public ${field.colType} get${field.colName?cap_first}() {
        return ${field.colName};
    }

    public void set${field.colName?cap_first}(${field.colType} ${field.colName}) {
        this.${field.colName} = ${field.colName};
    }
        </#if>
    </#list>

}