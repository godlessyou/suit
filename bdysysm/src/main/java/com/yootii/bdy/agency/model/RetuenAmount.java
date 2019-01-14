package com.yootii.bdy.agency.model;

import java.util.List;

public class RetuenAmount {
	private Long custAmount;
	private Long agencyAmount;
	private List<Integer> custId;
	private List<Integer> userId;
	public Long getCustAmount() {
		return custAmount;
	}
	public void setCustAmount(Long custAmount) {
		this.custAmount = custAmount;
	}
	public Long getAgencyAmount() {
		return agencyAmount;
	}
	public void setAgencyAmount(Long agencyAmount) {
		this.agencyAmount = agencyAmount;
	}
	public List<Integer> getCustId() {
		return custId;
	}
	public void setCustId(List<Integer> custId) {
		this.custId = custId;
	}
	public List<Integer> getUserId() {
		return userId;
	}
	public void setUserId(List<Integer> userId) {
		this.userId = userId;
	}
	
	
}
