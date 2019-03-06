import freemarker.template.TemplateException;
import service.AbsGenerate;
import service.GenerateContext;
import service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    private static GenerateContext context;

    static {

        try {
            context = new GenerateContext();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException, IOException, TemplateException {

        List<AbsGenerate> list = new ArrayList<AbsGenerate>();
        list.add(new GenerateDomainFile());
        list.add(new GenerateXmlFile());
        list.add(new GenerateMapperFile());
        list.add(new GenerateServiceFile());
        list.add(new GenerateServiceImplFile());
        list.add(new GenerateSearchFormFile());
        list.add(new GenerateCreateFormFile());
        list.add(new GenerateUpdateFormFile());
        list.add(new GenerateDomainDetailVoFile());
        list.add(new GenerateDomainPageVoFile());
        list.add(new GenerateControllerFile());

//        //list.add(new GenerateMngControllerFile());
//        //list.add(new GenerateMngHtmlFile());
//        //list.add(new GenerateMngFormHtmlFile());
//        //list.add(new GenerateMngJSFile());
//        list.add(new GeneratePageViewFile());
//        list.add(new GenerateFeignClientFile());
//        list.add(new GenerateFeignClientCallbackFile());
        //list.add(new GenerateAddFormFile());


        for(AbsGenerate generate : list){
            generate.generate(context);
        }
    }



}
