package com.yootii.bdy.material.dao;

import com.yootii.bdy.material.model.MaterialSort;

public interface MaterialSortMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MaterialSort record);

    int insertSelective(MaterialSort record);

    MaterialSort selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MaterialSort record);

    int updateByPrimaryKey(MaterialSort record);
}