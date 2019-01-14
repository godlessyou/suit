package com.yootii.bdy.material.dao;

import com.yootii.bdy.material.model.MaterialType;

public interface MaterialTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MaterialType record);

    int insertSelective(MaterialType record);

    MaterialType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MaterialType record);

    int updateByPrimaryKey(MaterialType record);
}