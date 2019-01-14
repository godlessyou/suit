package com.yootii.bdy.taskremind.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.task.model.UserTask;
import com.yootii.bdy.taskremind.model.TaskRemind;

public interface TaskRemindMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskRemind taskRemind);

    int insertSelective(TaskRemind taskRemind);

    TaskRemind selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(TaskRemind taskRemind);
    
    int updateByPrimaryKeySelective(TaskRemind taskRemind);
    
    List<TaskRemind> selectTaskRemindList(@Param("taskRemind") TaskRemind taskRemind, @Param("userlist") List<UserTask> userlist, @Param("gcon") GeneralCondition gcon);
    
    Map<String, Long> selectTaskRemindCount(@Param("taskRemind") TaskRemind taskRemind,@Param("userlist") List<UserTask> userlist, @Param("gcon") GeneralCondition gcon);
    
}