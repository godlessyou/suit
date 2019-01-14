package com.yootii.bdy.invite.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.invite.model.AgencyInvite;

public interface AgencyInviteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AgencyInvite record);

    int insertSelective(AgencyInvite record);

    AgencyInvite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgencyInvite record);

    int updateByPrimaryKey(AgencyInvite record);
    
    int updateStatusById(AgencyInvite record);
    
    List<AgencyInvite> selectAgencyInviteList(@Param("record") AgencyInvite record, @Param("gcon") GeneralCondition gcon);
    
    List<Map<String, Long>> selectAgencyInviteCount(@Param("record") AgencyInvite record, @Param("gcon") GeneralCondition gcon);
    
    AgencyInvite selectByEmail(String email);
}