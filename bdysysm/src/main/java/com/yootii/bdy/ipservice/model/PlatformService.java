package com.yootii.bdy.ipservice.model;

public class PlatformService {
    private Integer serviceId;       //服务id
 
    private String serviceName;     //服务名称

    private String serviceDesc;    //服务描述
    
    private String serviceLogo;      //服务logo
    
    private Integer serviceType;  //服务的类型（高级或自助）
    
    private Integer parent;      //父节点
    
    private Object data;
    
    private String caseType;		//案件类型
    
    private Integer caseTypeId;    //类型id

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc == null ? null : serviceDesc.trim();
    }
    
    public String getServiceLogo() {
        return serviceLogo;
    }

    public void setServiceLogo(String serviceLogo) {
        this.serviceLogo = serviceLogo == null ? null : serviceLogo.trim();
    }

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getCaseTypeId() {
		return caseTypeId;
	}

	public void setCaseTypeId(Integer caseTypeId) {
		this.caseTypeId = caseTypeId;
	}


}