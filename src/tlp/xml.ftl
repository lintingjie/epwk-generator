<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.epwk.mapper.${className}Mapper">

    <select id="selectById" resultType="com.epwk.entity.${className}" parameterType="java.lang.Long">
        select * from ${tableName} where id= ${id}
    </select>

    <select id="selectList" resultType="com.epwk.entity.${className}" parameterType="com.epwk.form.BasePageForm">
        select * from ${tableName} where 1 = 1
        <if test="id!=null">
            and id = ${id}
        </if>
        order by create_time desc
    </select>

    <select id="selectCount" resultType="java.lang.Integer" parameterType="com.epwk.form.BasePageForm">
        select count(1) from ${tableName} where 1 = 1
        <if test="id!=null">
            and id = ${id}
        </if>
    </select>

    <insert id="insertSelective" parameterType="com.epwk.entity.${className}">
        INSERT INTO ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list fields as field>
                <if test="${field.colName} != null">
                    `${field._colName}`,
                </if>
            </#list>
        </trim>
        <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
            <#list fields as field>
                <if test="${field.colName} != null">
                    ${field.colNameForInsert},
                </if>
            </#list>
        </trim>
    </insert>

    <insert id="insert" parameterType="com.epwk.entity.${className}"<#list fields as field><#if field.autoIncrement> useGeneratedKeys="true" keyProperty="id"<#break></#if></#list>>
        insert into ${tableName}
        (
            <#list fields as field>
                <#if field.colName != "createTime" && !field.autoIncrement>
            `${field._colName}`,
                </#if>
            </#list>
            `create_time`
        )
        values
        (
            <#list fields as field>
                <#if field.colName != "createTime" && !field.autoIncrement>
            ${field.colNameForInsert},
                </#if>
            </#list>
            now()
        )
    </insert>

    <update id="updateById" parameterType="com.epwk.entity.${className}">
        update ${tableName} set
        <#list fields as field>
            `${field._colName}` = ${field.colNameForInsert},
        </#list>
            `update_time` = now()
        where `id` = ${id}
    </update>

    <update id="updateSelective" parameterType="com.epwk.entity.${className}">
        UPDATE ${tableName} set
        <#list fields as field>
            <if test="${field.colName} != null">
                `${field._colName}` = ${field.colNameForInsert},
            </if>
        </#list>
            `update_time` = now()
        where `id` = ${id}
    </update>

    <delete id="deleteById" parameterType="java.lang.Long">
        delete from ${tableName} where `id` = ${id}
    </delete>

</mapper>