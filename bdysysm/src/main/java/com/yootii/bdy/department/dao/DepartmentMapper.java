package com.yootii.bdy.department.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.user.model.User;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

	Integer selectDepartmentCountByAgencId(@Param("agencyId")Integer id);
	//查询当前用户代理机构的部门列表
	List<Department> queryDepartmentByAgencId(@Param("parent")Integer parent,@Param("agencyId") Integer agencyId,@Param("level") Integer level);
	
	//指定用户所在的部门
	List<Department> queryDepartmentByUserId(@Param("userId")Integer userId,@Param("level")Integer level);
	
	//指定部门下的所有子部门
	List<Department> queryDepartmentByDeptId(@Param("deptId") Integer deptId);
	
	List<Department> queryDepartmentUserByAgencId(@Param("agencyId") Integer agencyId);
	
}