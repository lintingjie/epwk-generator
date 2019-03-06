package service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suggestion on 2015/3/19.
 */
public class GenerateFeignClientCallbackFile extends AbsGenerate {
    @Override
    public void generate(GenerateContext context) throws IOException, TemplateException {

        Template template = context.getCfg().getTemplate("src/tlp/feignClientFallback.ftl");

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("className", WordUtils.capitalize(context.getTableName()));
        data.put("tableName", context.getTableName());
        data.put("domainPackage", context.getDomainPackage());
        data.put("requestPackage",context.getRequestPackage());
        data.put("feignClientPackage", context.getFeignClientPackage());
        data.put("viewPackage",context.getViewPackage());

        print(template, data, context.getFeignClientCallbackPath());
    }
}
