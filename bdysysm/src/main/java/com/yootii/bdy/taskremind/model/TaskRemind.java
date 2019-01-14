package com.yootii.bdy.taskremind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TaskRemind {
    private Integer trid;

    private String title;

    private String message;

    private Integer type;

    private Integer isclose;

    private Integer agencyid;
    
    @JsonIgnore
    private String taskid;

	public Integer getTrid() {
		return trid;
	}

	public void setTrid(Integer trid) {
		this.trid = trid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsclose() {
		return isclose;
	}

	public void setIsclose(Integer isclose) {
		this.isclose = isclose;
	}

	public Integer getAgencyid() {
		return agencyid;
	}

	public void setAgencyid(Integer agencyid) {
		this.agencyid = agencyid;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

    
}