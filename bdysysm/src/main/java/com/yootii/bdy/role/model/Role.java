package com.yootii.bdy.role.model;

import java.util.List;

import com.yootii.bdy.permission.model.Permission;

public class Role {
    private Integer id;

    private String name;

    private String description;
    
    private Integer agencyId;
    
    private String scope;
    
    private Integer noedit;
    
    private List<Permission> permission;
    
    
    
    
    
    public Integer getNoedit() {
		return noedit;
	}

	public void setNoedit(Integer noedit) {
		this.noedit = noedit;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public List<Permission> getPermission() {
		return permission;
	}

	public void setPermission(List<Permission> permission) {
		this.permission = permission;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + ", agencyId=" + agencyId
				+ ", scope=" + scope + ", noedit=" + noedit + ", permission=" + permission + "]";
	}
    
    
}