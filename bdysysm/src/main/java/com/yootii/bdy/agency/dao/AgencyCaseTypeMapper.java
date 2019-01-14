package com.yootii.bdy.agency.dao;

import com.yootii.bdy.agency.model.AgencyCaseType;

public interface AgencyCaseTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AgencyCaseType record);

    int insertSelective(AgencyCaseType record);

    AgencyCaseType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgencyCaseType record);

    int updateByPrimaryKey(AgencyCaseType record);
}