package com.security.bearer.dto;

import java.io.Serializable;
import java.util.List;

public class UserRDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String username;
    private String uid;
    private List<RoleDTO> roles;

    public UserRDTO(String username, String uid, List<RoleDTO> roles) {
        super();
        this.username = username;
        this.uid = uid;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getUid() {
        return uid;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

}
