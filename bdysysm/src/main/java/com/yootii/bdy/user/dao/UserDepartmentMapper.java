package com.yootii.bdy.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.user.model.UserDepartment;

public interface UserDepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDepartment record);

    int insertSelective(UserDepartment record);

    UserDepartment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDepartment record);

    int updateByPrimaryKey(UserDepartment record);
    
    List<UserDepartment> selectByDepartmentId(Integer departmentId);
    
    UserDepartment selectByUserIdAndDepartmentId(@Param("userId") Integer userId,@Param("departmentName") String departmentName);
}