package com.yootii.bdy.remind.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.remind.model.Remind;

public interface RemindMapper {
	

    int insertSelective(Remind remind);

    int updateByPrimaryKeySelective(Remind remind);
    
    List<Remind> selectByRemind(@Param("remind")Remind remind, @Param("gcon") GeneralCondition gcon);

    Long selectByRemindCount(@Param("remind")Remind remind, @Param("gcon") GeneralCondition gcon);
    
    
    List<Remind> selectRemindList(@Param("remind")Remind remind);
}