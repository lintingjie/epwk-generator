package service;

import domain.Field;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by Greg.Chen on 2015/3/18.
 */
public class GenerateCreateFormFile extends AbsGenerate {

    @Override
    public void generate(GenerateContext context) throws IOException, TemplateException {
        Template template = context.getCfg().getTemplate("src/tlp/createForm.ftl");
        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        //data.put("requestPackage", context.getRequestPackage());


        List<Field> createFields = new ArrayList<>();
        Set<String> createColumnClassNames = new HashSet<>(context.getColumnClassNames());
        for(Field field : context.getFields()){
            if(field.getColName().equalsIgnoreCase("createTime") || field.getColName().equalsIgnoreCase("updateTime")){
                continue;
            }
            createFields.add(field);
        }
        Boolean containDate = false;
        Boolean containTimeStamp = false;
        for(Field field : createFields){
            if(field.get_colType().equalsIgnoreCase("Timestamp")) {
                containTimeStamp = true;
            }
            if(field.get_colType().equalsIgnoreCase("Date")){
                containDate = true;
            }
        }
        if(!containDate){
            createColumnClassNames.remove("com.epwk.util.DateFormat.DateSerializer");
            createColumnClassNames.remove("com.epwk.util.DateFormat.DateDeserializer");
        }
        if(!containTimeStamp){
            createColumnClassNames.remove("com.epwk.util.DateFormat.DateTimeSerializer");
            createColumnClassNames.remove("com.epwk.util.DateFormat.DateTimeDeserializer");
        }
        if(!containDate && !containTimeStamp){
            createColumnClassNames.remove("java.util.Date");
            createColumnClassNames.remove("com.fasterxml.jackson.databind.annotation.JsonSerialize");
            createColumnClassNames.remove("com.fasterxml.jackson.databind.annotation.JsonDeserialize");
        }

        data.put("fields", createFields);
        data.put("className", context.getClassName());
        data.put("createColumnClassNames", createColumnClassNames);


        print(template, data, context.getRequestCreateFormPath());
    }
}
