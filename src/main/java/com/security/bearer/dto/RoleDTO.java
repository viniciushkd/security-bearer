package com.security.bearer.dto;

import java.io.Serializable;

import com.security.bearer.repository.data.ERole;

public class RoleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ERole role;
	
	public RoleDTO(ERole role) {
		super();
		this.role = role;
	}

	public ERole getRole() {
		return role;
	}

	public void setRole(ERole role) {
		this.role = role;
	}
	
	
}
