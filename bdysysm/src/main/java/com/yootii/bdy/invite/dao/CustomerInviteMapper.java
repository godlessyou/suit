package com.yootii.bdy.invite.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.invite.model.CustomerInvite;

public interface CustomerInviteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerInvite record);

    int insertSelective(CustomerInvite record);

    CustomerInvite selectByPrimaryKey(Integer id);
    
    CustomerInvite selectByAgencyIdAndName(@Param("agencyId") Integer agencyId,@Param("name") String name);

    int updateByPrimaryKeySelective(CustomerInvite record);

    int updateByPrimaryKey(CustomerInvite record);
    
    int updateStatusById(CustomerInvite record);

    List<CustomerInvite> selectCustomerInviteList(@Param("record") CustomerInvite record, @Param("gcon") GeneralCondition gcon);

    List<Map<String, Long>> selectCustomerInviteCount(@Param("record") CustomerInvite record, @Param("gcon") GeneralCondition gcon);
    
//    CustomerInvite selectByEmailAndAgencyId(Integer agencyId,String email);//同一个代理所不能添加两个邮箱相同的客户

}