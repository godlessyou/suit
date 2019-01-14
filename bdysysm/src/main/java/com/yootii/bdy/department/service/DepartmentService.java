package com.yootii.bdy.department.service;

import java.util.List;

import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.user.model.User;

public interface DepartmentService {
	
	public ReturnInfo createDept(Department department, User caller);
	
    public ReturnInfo deleteDept(Department department);

    public ReturnInfo modifyDept(Department department);

    public ReturnInfo queryDepts(User caller);
    
    public List<Department> queryDepartmentByUserIdAndLevel(Integer userId,Integer level);
    
    public ReturnInfo queryDepartmentByDeptId(Integer deptId);

    public ReturnInfo queryDepartmentUserByAgencId(Integer agencyId);
    
    public ReturnInfo queryDepartmentByUserId(Integer userId);
}