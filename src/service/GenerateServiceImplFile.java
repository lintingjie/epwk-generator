package service;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Greg.Chen on 2015/3/18.
 */
public class GenerateServiceImplFile extends AbsGenerate {

    @Override
    public void generate(GenerateContext context) throws IOException, TemplateException {
        Template template = context.getCfg().getTemplate("src/tlp/serviceImpl.ftl");
        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("className",context.getClassName());
        data.put("lowClassName",context.getLowClassName());

        print(template, data, context.getServiceImplPath());
    }
}
