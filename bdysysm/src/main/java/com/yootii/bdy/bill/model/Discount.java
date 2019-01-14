package com.yootii.bdy.bill.model;

import java.math.BigDecimal;
import java.util.List;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.customer.model.Customer;

public class Discount {
    private Integer discountId;

    private String agencyId;

    private Integer custId;

    private String caseType;

    private BigDecimal value;

    private String startDate;

    private String endDate;

    private Integer status;
    
    private String customerName;
    
    private Integer coagencyId;
    
    private List<Customer> customer;
    
    private List<Agency> coAgency;
    
    
    
    public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public List<Agency> getCoAgency() {
		return coAgency;
	}

	public void setCoAgency(List<Agency> coAgency) {
		this.coAgency = coAgency;
	}

	public Integer getCoagencyId() {
		return coagencyId;
	}

	public void setCoagencyId(Integer coagencyId) {
		this.coagencyId = coagencyId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId == null ? null : agencyId.trim();
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType == null ? null : caseType.trim();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}