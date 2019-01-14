package com.yootii.bdy.process.model;

public class TaskPermission {
    private Integer tpId;

    private String defId;

    private String taskName;

    private String taskKey;

    private Integer permissionKey;

    private String permissionValue;

	public Integer getTpId() {
		return tpId;
	}

	public void setTpId(Integer tpId) {
		this.tpId = tpId;
	}

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public Integer getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(Integer permissionKey) {
		this.permissionKey = permissionKey;
	}

	public String getPermissionValue() {
		return permissionValue;
	}

	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}
    
    

}