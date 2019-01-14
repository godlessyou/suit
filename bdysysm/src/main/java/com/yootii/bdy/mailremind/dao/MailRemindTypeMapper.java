package com.yootii.bdy.mailremind.dao;

import java.util.List;

import com.yootii.bdy.mailremind.model.MailRemindType;

public interface MailRemindTypeMapper {
    int deleteByPrimaryKey(Integer reTypeId);

    int insert(MailRemindType record);

    int insertSelective(MailRemindType record);

    MailRemindType selectByPrimaryKey(Integer reTypeId);

    int updateByPrimaryKeySelective(MailRemindType record);

    int updateByPrimaryKey(MailRemindType record);
    
    List<MailRemindType> selectAllType();
}