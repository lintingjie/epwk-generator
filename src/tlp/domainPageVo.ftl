package com.epwk.vo;

<#list columnClassNames as item>
import ${item};
</#list>
import java.io.Serializable;

public class ${className}PageVo implements Serializable {
<#list fields as field>
    <#if field._colType == "Date">
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    <#elseif field._colType == "Timestamp">
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    </#if>
    private ${field.colType} ${field.colName};
</#list>

    <#list fields as field>
    public ${field.colType} get${field.colName?cap_first}() {
        return ${field.colName};
    }
    public void set${field.colName?cap_first}(${field.colType} ${field.colName}) {
        this.${field.colName} = ${field.colName};
    }

    </#list>
}