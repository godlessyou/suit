package com.yootii.bdy.permission.model;

import java.util.List;

import com.yootii.bdy.role.model.Role;

public class Permission {
    private Integer id;

    private String permission;

    private String note;
    
    private Integer agencyId;
    
    private String scope;
    
    private Integer noedit;
    
    private List<Role> roles;
    
    

    public Integer getNoedit() {
		return noedit;
	}

	public void setNoedit(Integer noedit) {
		this.noedit = noedit;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
    
    
}