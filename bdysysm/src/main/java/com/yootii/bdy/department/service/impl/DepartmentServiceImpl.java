package com.yootii.bdy.department.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.dao.AgencyUserMapper;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.department.dao.DepartmentMapper;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.department.service.DepartmentService;
import com.yootii.bdy.user.dao.UserDepartmentMapper;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.model.UserDepartment;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	@Resource
	private DepartmentMapper departmentMapper;
	
	@Resource
	private AgencyUserMapper agencyUserMapper;
	
	@Resource
	private UserDepartmentMapper userDepartmentMapper;
	
	@Override
	public ReturnInfo createDept(Department department, User caller) {
		ReturnInfo info = new ReturnInfo();
		//当前用户的代理机构Id
		Integer agencyId = agencyUserMapper.selectAgencyIdByUserId(caller.getUserId());
		String name = department.getName();
		if(name==null||"".equals(name)){
			info.setSuccess(false);
			info.setMessage("部门名称不能为空");
			return info;
		}
		Short level = department.getLevel();
		if(level==null||level>1){//level取值只能是0,1
			info.setSuccess(false);
			info.setMessage("部门等级不正确");
			return info;
		}
		if(level!=0&&department.getParent()==null){
			info.setSuccess(false);
			info.setMessage("上级部门不能为空");
			return info;
		}
		if(department.getParent()==null||department.getParent()==0){
			department.setParent(null);
		}
		department.setAgencyId(agencyId);
		departmentMapper.insertSelective(department);
		info.setSuccess(true);
		info.setMessage("部门创建成功");
		return info;
	}

	@Override
	public ReturnInfo deleteDept(Department department) {
		ReturnInfo info = new ReturnInfo();
		Integer id = department.getId();
		if(id==null){
			info.setSuccess(false);
			info.setMessage("部门ID不能为空");
			return info;
		}
		//判断删除时该部门下是否有用户
		List<UserDepartment> userDepartments = userDepartmentMapper.selectByDepartmentId(id);
		if(userDepartments.size()>0){
			info.setSuccess(false);
			info.setMessage("删除失败，该部门下存在用户");
			return info;
		}
		departmentMapper.deleteByPrimaryKey(id);
		info.setSuccess(true);
		info.setMessage("部门删除成功");
		return info;
	}

	@Override
	public ReturnInfo modifyDept(Department department) {
		ReturnInfo info = new ReturnInfo();
		Integer id = department.getId();
		if(id==null){
			info.setSuccess(false);
			info.setMessage("部门ID不能为空");
			return info;
		}
		Department department2 = departmentMapper.selectByPrimaryKey(id);
		if(department.getAgencyId()!=null&&(department.getAgencyId()!=department2.getAgencyId())){
			info.setSuccess(false);
			info.setMessage("代理机构不能修改");
			return info;
		}
		//不能支持用户和部门之间的修改，因为用户和部门修改在用户修改时修改完成
		departmentMapper.updateByPrimaryKeySelective(department);
		info.setSuccess(true);
		info.setMessage("部门修改成功");
		return info;
	}

	@Override
	public ReturnInfo queryDepts(User caller) {
		ReturnInfo info = new ReturnInfo();
		//当前用户的代理机构Id
		Integer agencyId = agencyUserMapper.selectAgencyIdByUserId(caller.getUserId());
		List<Department> departments1 = departmentMapper.queryDepartmentByAgencId(null,agencyId,0);//0
		for(Department department1 : departments1){
			Integer parent2 = department1.getId();
			List<Department> departments2 = departmentMapper.queryDepartmentByAgencId(parent2,agencyId,1); //0
			/*
			 *2018-03-27目前不支持3级部门
			 *
			 * for(Department department2 : departments2){
				Integer parent3 = department2.getId();
				List<Department> departments3 = departmentMapper.queryDepartmentByAgencId(parent3,agencyId,2); //2
				department2.setData(departments3);
			}*/
			department1.setChildren(departments2);
		}
		info.setData(departments1);
		info.setSuccess(true);
		info.setMessage("部门查询成功");
		return info;
	}

	@Override
	public List<Department> queryDepartmentByUserIdAndLevel(Integer userId,Integer level) {
		if(userId==null){
			return null;
		}
		return departmentMapper.queryDepartmentByUserId(userId,level);
	}
	
	@Override
	public ReturnInfo queryDepartmentByUserId(Integer userId) {
		ReturnInfo info = new ReturnInfo();
		List<Department> departments = queryDepartmentByUserIdAndLevel(userId,null);
		info.setData(departments);
		info.setSuccess(true);
		info.setMessage("部门查询成功");
		return info;
	}
	
	
	@Override
	public ReturnInfo queryDepartmentByDeptId(Integer deptId) {
		ReturnInfo info = new ReturnInfo();
		List<Department> departments = departmentMapper.queryDepartmentByDeptId(deptId);
		info.setData(departments);
		info.setSuccess(true);
		info.setMessage("部门查询成功");
		return info;
	}
	
	@Override
	public ReturnInfo queryDepartmentUserByAgencId(Integer agencyId) {
		ReturnInfo info = new ReturnInfo();
		List<Department> departments = departmentMapper.queryDepartmentUserByAgencId(agencyId);
		info.setData(departments);
		info.setSuccess(true);
		info.setMessage("部门查询成功");
		return info;
	}

	

}