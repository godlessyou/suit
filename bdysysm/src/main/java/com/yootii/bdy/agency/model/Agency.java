package com.yootii.bdy.agency.model;

import java.util.List;

import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.material.model.MaterialFile;
import com.yootii.bdy.user.model.User;

public class Agency {
    private Integer id;

    private String name;

    private String address;

    private String tel;

    private String logo;

    private String status;
    
    private String fax;
    
    private String postcode;
    
    private Integer level;
    
    private Integer appOnline;//是否支持网申
    
    private Integer appChannel;//采用哪个代理所的网申通道
    
    
    private String channelName;//网申通道所在的代理所的名称
    
    
    
    List<MaterialFile> materialFile;
    
    List<Agency> cooperationAgency;
    
    List<Customer> agencyCustomer;
    
    List<User> user;
    

	private String contactName;
    
    private String contactTel;

    public List<MaterialFile> getMaterialFile() {
		return materialFile;
	}

	public void setMaterialFile(List<MaterialFile> materialFile) {
		this.materialFile = materialFile;
	}

	public List<Agency> getCooperationAgency() {
		return cooperationAgency;
	}

	public void setCooperationAgency(List<Agency> cooperationAgency) {
		this.cooperationAgency = cooperationAgency;
	}

	public List<Customer> getAgencyCustomer() {
		return agencyCustomer;
	}

	public void setAgencyCustomer(List<Customer> agencyCustomer) {
		this.agencyCustomer = agencyCustomer;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	

    public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public Integer getAppOnline() {
		return appOnline;
	}

	public void setAppOnline(Integer appOnline) {
		this.appOnline = appOnline;
	}

	public Integer getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(Integer appChannel) {
		this.appChannel = appChannel;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
    
}