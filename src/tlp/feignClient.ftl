package ${feignClientPackage};

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.fow.core.rest.view.SuccessView;
import ${domainPackage}.${className};
import ${feignClientConfig};
import ${requestPackage}.${className}SearchForm;
import ${requestPackage}.${className}CreateForm;
import ${requestPackage}.${className}UpdateForm;
import ${viewPackage}.${className}PageView;


@FeignClient(value = "${feignClient}",
    fallbackFactory = ${className}HystrixClientFallback.class,
    configuration = ${configName}.class)
public interface ${className}FeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/${tableName}/search")
    @ResponseBody
    ${className}PageView search(@RequestBody @Valid ${className}SearchForm form);

    @RequestMapping(method = RequestMethod.POST, value = "/${tableName}/searchOne")
    @ResponseBody
    ${className} searchOne(@RequestBody @Valid ${className}SearchForm form);

    @RequestMapping(method = RequestMethod.POST, value = "/${tableName}/count")
    @ResponseBody
    Integer count(@RequestBody @Valid ${className}SearchForm form);

    @RequestMapping(method = RequestMethod.POST, value = "/${tableName}")
    @ResponseBody
    SuccessView insert(@RequestBody @Valid ${className}CreateForm form);

    @RequestMapping(method = RequestMethod.PUT, value = "/${tableName}")
    @ResponseBody
    SuccessView update(@RequestBody @Valid ${className}UpdateForm form);

    @RequestMapping(method = RequestMethod.GET, value = "/${tableName}/{id}")
    @ResponseBody
    ${className} selectById(@PathVariable("id") Integer id);

}
