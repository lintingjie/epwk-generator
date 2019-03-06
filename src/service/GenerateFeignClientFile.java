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
public class GenerateFeignClientFile extends AbsGenerate {
    @Override
    public void generate(GenerateContext context) throws IOException, TemplateException {

        Template template = context.getCfg().getTemplate("src/tlp/feignClient.ftl");

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("className", WordUtils.capitalize(context.getTableName()));
        data.put("tableName", context.getTableName());
        data.put("domainPackage", context.getDomainPackage());
        data.put("requestPackage",context.getRequestPackage());
        data.put("feignClientPackage", context.getFeignClientPackage());
        data.put("feignClientConfig", context.getFeignClientConfig());
        data.put("viewPackage",context.getViewPackage());
        data.put("feignClient", context.getFeignClient());
        data.put("configName", context.getConfigName());

        print(template, data, context.getFeignClientPath());
    }
}
