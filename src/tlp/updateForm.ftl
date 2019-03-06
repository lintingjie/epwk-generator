package com.epwk.form;

<#list columnClassNames as item>
import ${item};
</#list>
<#list fields as field>
    <#if field.nullable==0&&field.colType!="String">
import javax.validation.constraints.NotNull;
        <#break>
    </#if>
</#list>
<#list fields as field>
    <#if field.nullable==0&&field.colType=="String">
import javax.validation.constraints.NotBlank;
        <#break>
    </#if>
</#list>

public class ${className}UpdateForm {
    <#list fields as field>
        <#if field.nullable==0>
            <#if field.colType=="String">
    @NotBlank
            <#else>
    @NotNull
            </#if>
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