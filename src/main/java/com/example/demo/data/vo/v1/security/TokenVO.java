package com.example.demo.data.vo.v1.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenVO implements Serializable{

	private static final long serialVersionUID = 1;
	
	private String username;
	private boolean authenticated;
	private Date created;
	private Date expiration;
	private String accesssToken;
	private String refreshToken;
	
	public TokenVO() {}
	
	public TokenVO(String username, boolean authenticated, 
			Date created, Date expiration, String accesssToken,
			String refreshToken) {
		
		this.username = username;
		this.authenticated = authenticated;
		this.created = created;
		this.expiration = expiration;
		this.accesssToken = accesssToken;
		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public String getAccesssToken() {
		return accesssToken;
	}

	public void setAccesssToken(String accesssToken) {
		this.accesssToken = accesssToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}	
	
}
