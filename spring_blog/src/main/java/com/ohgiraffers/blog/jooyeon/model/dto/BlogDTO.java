package com.ohgiraffers.blog.jooyeon.model.dto;

import com.ohgiraffers.blog.jinhee.model.entity.User;

import java.util.Date;

public class BlogDTO {

    private String blogTitle;
    private String blogContent;
    private int id;
    private User user;
    private String registerDate;


    public BlogDTO() {
    }

    public BlogDTO(String blogTitle, String blogContent, int id, User user, String registerDate) {
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.id = id;
        this.user = user;
        this.registerDate = registerDate;
    }

    public void setId(String s) {
    }

    public void setCreateDate(Date createDate) {
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "BlogDTO{" +
                "blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", id=" + id +
                ", user=" + user +
                ", registerDate='" + registerDate + '\'' +
                '}';
    }

    public Integer getId() {
        return id; // getId() 메서드가 id 필드를 반환하도록 수정
    }

    public void setId(Integer id) {
        this.id = id; // setId() 메서드가 id 필드를 설정하도록 수정
    }


}

