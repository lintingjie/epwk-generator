package com.epwk.service;

import com.epwk.form.${className}CreateForm;
import com.epwk.form.${className}SearchForm;
import com.epwk.form.${className}UpdateForm;
import com.epwk.param.PageResult;
import com.epwk.vo.${className}PageVo;
import com.epwk.vo.${className}DetailVo;

public interface ${className}Service {

    void insert(${className}CreateForm form);

    void update(${className}UpdateForm form);

    void delete(Long id);

    PageResult<${className}PageVo> list(${className}SearchForm form);

    int selectCount(${className}SearchForm form);

    ${className}DetailVo getDetail(Long id);

}

