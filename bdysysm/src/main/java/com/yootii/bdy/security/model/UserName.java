package com.yootii.bdy.security.model;

public class UserName {
	private String username;
	   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}
