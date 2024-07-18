package com.ohgiraffers.blog.hwayeon.model.dto;

public class KHYSignupRequestDTO {

    private String username;
    private String password;

    public KHYSignupRequestDTO() {
    }

    public KHYSignupRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
