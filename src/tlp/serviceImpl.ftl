package com.epwk.service.impl;

import com.epwk.entity.${className};
import com.epwk.form.${className}CreateForm;
import com.epwk.form.${className}SearchForm;
import com.epwk.form.${className}UpdateForm;
import com.epwk.vo.${className}DetailVo;
import com.epwk.vo.${className}PageVo;
import com.epwk.mapper.${className}Mapper;
import com.epwk.param.PageResult;
import com.epwk.service.${className}Service;
import com.epwk.util.Convert;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${className}ServiceImpl implements ${className}Service {
    @Autowired
    ${className}Mapper ${lowClassName}Mapper;

    @Override
    public void insert(${className}CreateForm form) {
        ${className} entity = new ${className}();
        BeanUtils.copyProperties(form, entity);
        ${lowClassName}Mapper.insert(entity);
    }

    @Override
    public void update(${className}UpdateForm form) {
        ${className} entity = new ${className}();
        BeanUtils.copyProperties(form, entity);
        ${lowClassName}Mapper.updateSelective(entity);
    }

    @Override
    public void delete(Long id) {
        ${lowClassName}Mapper.deleteById(id);
    }

    @Override
    public PageResult<${className}PageVo> list(${className}SearchForm form) {
        Page<${className}> page = ${lowClassName}Mapper.selectList(form);
        return PageResult.getPageResult(page, ${className}PageVo.class);
    }

    @Override
    public int selectCount(${className}SearchForm form) {
        return ${lowClassName}Mapper.selectCount(form);
    }

    @Override
    public ${className}DetailVo getDetail(Long id) {
        ${className} ${lowClassName} = ${lowClassName}Mapper.selectById(id);
        if(${lowClassName} == null){
            throw new RuntimeException("resource not found");
        }
        ${className}DetailVo detail = new ${className}DetailVo();
        BeanUtils.copyProperties(${lowClassName}, detail);
        return detail;
    }
}

