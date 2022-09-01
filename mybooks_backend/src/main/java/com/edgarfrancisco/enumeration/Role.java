package com.edgarfrancisco.enumeration;

import static com.edgarfrancisco.constant.Authority.*;

public enum Role {

    ROLE_USER(USER_AUTHORITIES); //constructor initializes private field with this value

    private String[] authorities;

    Role(String... authorities) { this.authorities = authorities; }

    public String[] getAuthorities() { return authorities; }
}
