package service;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * Created by Greg.Chen on 2015/3/18.
 */
public abstract class AbsGenerate {

    public abstract void generate(GenerateContext context) throws IOException, TemplateException;


    public void print(Template template,  Map<String, Object> data, String path) throws IOException, TemplateException {
        Writer out = new OutputStreamWriter(System.out);
        template.process(data,out);
        out.flush();

        Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));

        template.process(data, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }
}
