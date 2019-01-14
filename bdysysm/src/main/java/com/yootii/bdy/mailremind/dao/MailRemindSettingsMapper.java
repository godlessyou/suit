package com.yootii.bdy.mailremind.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.mailremind.model.MailRemindSettings;

public interface MailRemindSettingsMapper {
    int deleteByPrimaryKey(Integer setId);

    int insert(MailRemindSettings record);

    int insertSelective(MailRemindSettings record);

    MailRemindSettings selectByPrimaryKey(Integer setId);

    int updateByPrimaryKeySelective(MailRemindSettings record);

    int updateByPrimaryKey(MailRemindSettings record);
    
    MailRemindSettings selectByCustIdAndRemindType(@Param("custId")Integer custId,@Param("remindType")Integer remindType);
    
    List<Map<String, Object>> selectByCustId(Integer custId);
}