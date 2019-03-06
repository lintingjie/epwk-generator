package com.epwk.controller;

import com.epwk.form.${className}CreateForm;
import com.epwk.form.${className}SearchForm;
import com.epwk.form.${className}UpdateForm;
import com.epwk.param.ApiResult;
import com.epwk.param.PageResult;
import com.epwk.service.${className}Service;
import com.epwk.vo.${className}PageVo;
import com.epwk.vo.${className}DetailVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
public class ${className}Controller {
    private Logger logger = LogManager.getLogger(UsualApplicantController.class);
    @Autowired
    private ${className}Service ${lowClassName}Service;

    @RequestMapping(value = "/${lowClassName}/add", method = RequestMethod.POST)
    public ApiResult add(@RequestBody @Valid ${className}CreateForm form) {
        ${lowClassName}Service.insert(form);
        return new ApiResult.Builder().success().build();
    }

    @RequestMapping(value = "/${lowClassName}/list", method = RequestMethod.POST)
    public ApiResult<${className}PageVo> list(@RequestBody @Valid ${className}SearchForm form) {
        PageResult page = ${lowClassName}Service.list(form);
        return new ApiResult.Builder().success(page).build();
    }

    @RequestMapping(value = "/${lowClassName}/detail/{id}", method = RequestMethod.POST)
    public ApiResult get(@PathVariable @NotNull Long id) {
        ${className}DetailVo detail = ${lowClassName}Service.getDetail(id);
        return new ApiResult.Builder().success(detail).build();
    }

    @RequestMapping(value = "/${lowClassName}/update", method = RequestMethod.POST)
    public ApiResult update(@RequestBody @Valid ${className}UpdateForm form) {
        ${lowClassName}Service.update(form);
        return new ApiResult.Builder().success().build();
    }

    @RequestMapping(value = "/${lowClassName}/delete/{id}", method = RequestMethod.POST)
    public ApiResult delete(@PathVariable @NotNull Long id) {
        ${lowClassName}Service.delete(id);
        return new ApiResult.Builder().success().build();
    }

}
