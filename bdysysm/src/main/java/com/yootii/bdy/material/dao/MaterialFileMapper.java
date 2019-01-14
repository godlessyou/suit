package com.yootii.bdy.material.dao;

import com.yootii.bdy.material.model.MaterialFile;

public interface MaterialFileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(MaterialFile record);

    int insertSelective(MaterialFile record);

    MaterialFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(MaterialFile record);

    int updateByPrimaryKey(MaterialFile record);
}