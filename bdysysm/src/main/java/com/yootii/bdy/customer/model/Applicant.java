package com.yootii.bdy.customer.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yootii.bdy.material.model.Material;

public class Applicant {
    private Integer id;

    private String applicantName;

    private String applicantAddress;

    private String applicantEnName;

    private String applicantEnAddress;

    private String usertName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date modifyTime;
    
    private String country;
    
    private String appType;
    
    private String sendType;
    
    private String unifiedNumber;
    
    private String cardName;
    
    private String cardNumber;
    
    private Integer mainAppId;
    
    private Integer hasTm;
    
    //逻辑申请人所代表的所有申请人的商标总数
    private Integer tradeMarkCount;
        
    //当前申请人的商标数
    private Integer tmCount;
    
    //统一社会信用代码
    private String certCode;
    
    
    private List<Applicant> applicants;
    
    private List<Trademark> trademarks;
    
    private List<Material> material;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName == null ? null : applicantName.trim();
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress == null ? null : applicantAddress.trim();
    }

    public String getApplicantEnName() {
        return applicantEnName;
    }

    public void setApplicantEnName(String applicantEnName) {
        this.applicantEnName = applicantEnName == null ? null : applicantEnName.trim();
    }

    public String getApplicantEnAddress() {
        return applicantEnAddress;
    }

    public void setApplicantEnAddress(String applicantEnAddress) {
        this.applicantEnAddress = applicantEnAddress == null ? null : applicantEnAddress.trim();
    }

    public String getUsertName() {
        return usertName;
    }

    public void setUsertName(String usertName) {
        this.usertName = usertName == null ? null : usertName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

	public List<Trademark> getTrademarks() {
		return trademarks;
	}

	public void setTrademarks(List<Trademark> trademarks) {
		this.trademarks = trademarks;
	}

	public List<Material> getMaterial() {
		return material;
	}

	public void setMaterial(List<Material> material) {
		this.material = material;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getUnifiedNumber() {
		return unifiedNumber;
	}

	public void setUnifiedNumber(String unifiedNumber) {
		this.unifiedNumber = unifiedNumber;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getMainAppId() {
		return mainAppId;
	}

	public void setMainAppId(Integer mainAppId) {
		this.mainAppId = mainAppId;
	}

	public Integer getHasTm() {
		return hasTm;
	}

	public void setHasTm(Integer hasTm) {
		this.hasTm = hasTm;
	}

	public List<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}

	public Integer getTradeMarkCount() {
		return tradeMarkCount;
	}

	public void setTradeMarkCount(Integer tradeMarkCount) {
		this.tradeMarkCount = tradeMarkCount;
	}

	public Integer getTmCount() {
		return tmCount;
	}

	public void setTmCount(Integer tmCount) {
		this.tmCount = tmCount;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}


	
	
    
    
}