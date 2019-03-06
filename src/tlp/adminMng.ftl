package ${controllerPackage}.admin;

import com.fow.core.platform.web.rest.RESTController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import javax.validation.Valid;
import ${domainPackage}.${className};
import ${servicePackage}.${className}Service;
import javax.validation.Valid;
import java.util.Map;
import ${requestPackage}.${className}AddForm;
import ${requestPackage}.${className}SearchForm;
import ${viewPackage}.${className}ListView;

@RestController
public class ${className}MngController extends RESTController {

    @Autowired
    private ${className}Service ${tableName}Service;

    @RequestMapping(value="/${tableName}/list", method = RequestMethod.POST)
    @ResponseBody
    public ${className}ListView ${tableName}List(@Valid @RequestBody ${className}SearchForm form){
        Map<String,Object> query = form.getQueryMap();

        ${className}ListView view = new ${className}ListView();
        view.setList(${tableName}Service.selectList(query));
        view.setCount(${tableName}Service.selectCount(query));
        return view;
    }

    @RequestMapping(value = "/${tableName}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ${className} getById(@PathVariable String id){
        return (${className})${tableName}Service.selectById(id);
    }

    @RequestMapping(value = "/${tableName}", method = RequestMethod.POST)
    @ResponseBody
    public ${className} add(@Valid @RequestBody ${className}AddForm form){

        ${className} ${tableName} = new ${className}();

        BeanUtils.copyProperties(form, ${tableName});

        ${tableName}Service.insert(${tableName});
        return ${tableName};
    }

    @RequestMapping(value = "/${tableName}", method = RequestMethod.PUT)
    @ResponseBody
    public ${className} update(@Valid @RequestBody ${className} ${tableName}){

        ${tableName}Service.update(${tableName});
        return ${tableName};
    }

    @RequestMapping(value = "/${tableName}/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable String id){

        ${tableName}Service.deleteById(id);

        return "successfully";
    }

}
