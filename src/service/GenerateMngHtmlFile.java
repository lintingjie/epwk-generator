package service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Greg.Chen on 2015/7/9.
 */
public class GenerateMngHtmlFile extends AbsGenerate {

    @Override
    public void generate(GenerateContext context) throws IOException, TemplateException {
        Template template = context.getCfg().getTemplate("src/tlp/adminMngHtml.ftl");

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("className", WordUtils.capitalize(context.getTableName()));
        data.put("tableName", context.getTableName());
        data.put("domainPackage", context.getDomainPackage());
        data.put("servicePackage", context.getServicePackage());
        data.put("controllerPackage", context.getControllerPackage());

        data.put("fields", context.getFields());

        print(template, data, context.getMngHtmlPath());
    }
}
