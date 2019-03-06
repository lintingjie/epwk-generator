package com.epwk.mapper;

import com.epwk.entity.${className};
import com.epwk.form.BasePageForm;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public interface ${className}Mapper {

    ${className} selectById(Long id);

    Page<${className}> selectList(BasePageForm form);

    int selectCount(BasePageForm form);

    int insert(${className} entity);

    int insertSelective(${className} entity);

    int updateSelective(${className} entity);

    int updateById(${className} entity);

    int deleteById(Long id);



}

