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
public class GenerateUpdateFormFile extends AbsGenerate {

    @Override
    public void generate(GenerateContext context) throws IOException, TemplateException {
        Template template = context.getCfg().getTemplate("src/tlp/updateForm.ftl");
        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();


        List<Field> updateFields = new ArrayList<>();
        Set<String> updateColumnClassNames = new HashSet<>(context.getColumnClassNames());
        for(Field field : context.getFields()){
            if(field.getColName().equalsIgnoreCase("createTime") || field.getColName().equalsIgnoreCase("updateTime")){
                continue;
            }
            updateFields.add(field);
        }
        Boolean containDate = false;
        Boolean containTimeStamp = false;
        for(Field field : updateFields){
            if(field.get_colType().equalsIgnoreCase("Timestamp")) {
                containTimeStamp = true;
            }
            if(field.get_colType().equalsIgnoreCase("Date")){
                containDate = true;
            }
        }
        if(!containDate){
            updateColumnClassNames.remove("com.epwk.util.DateFormat.DateSerializer");
            updateColumnClassNames.remove("com.epwk.util.DateFormat.DateDeserializer");
        }
        if(!containTimeStamp){
            updateColumnClassNames.remove("com.epwk.util.DateFormat.DateTimeSerializer");
            updateColumnClassNames.remove("com.epwk.util.DateFormat.DateTimeDeserializer");
        }
        if(!containDate && !containTimeStamp) {
            updateColumnClassNames.remove("java.util.Date");
            updateColumnClassNames.remove("com.fasterxml.jackson.databind.annotation.JsonSerialize");
        }

        data.put("fields", updateFields);
        data.put("columnClassNames", updateColumnClassNames);
        data.put("className", context.getClassName());


        print(template, data, context.getRequestUpdateFormPath());
    }
}
