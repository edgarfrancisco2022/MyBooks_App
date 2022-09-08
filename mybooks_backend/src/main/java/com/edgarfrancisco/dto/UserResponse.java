package com.edgarfrancisco.dto;

import com.edgarfrancisco.model.User;

import java.util.Date;

public class UserResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date displayLastLogin;
    private Date joinDate;
    private String role; // user | admin | guest
    private String[] Authorities;
    private boolean isActive;
    private boolean isLocked;

    public UserResponse() {
    }

    public UserResponse(String firstName, String lastName, String username,
                        String email, String profileImageUrl, Date lastLoginDate,
                        Date displayLastLogin, Date joinDate, String role, String[] authorities,
                        boolean isActive, boolean isLocked) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginDate = lastLoginDate;
        this.displayLastLogin = displayLastLogin;
        this.joinDate = joinDate;
        this.role = role;
        Authorities = authorities;
        this.isActive = isActive;
        this.isLocked = isLocked;
    }

    public UserResponse(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
        this.lastLoginDate = user.getLastLoginDate();
        this.displayLastLogin = user.getDisplayLastLogin();
        this.joinDate = user.getJoinDate();
        this.role = user.getRole();
        Authorities = user.getAuthorities();
        this.isActive = user.isActive();
        this.isLocked = user.isLocked();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getDisplayLastLogin() {
        return displayLastLogin;
    }

    public void setDisplayLastLogin(Date displayLastLogin) {
        this.displayLastLogin = displayLastLogin;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getAuthorities() {
        return Authorities;
    }

    public void setAuthorities(String[] authorities) {
        Authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
