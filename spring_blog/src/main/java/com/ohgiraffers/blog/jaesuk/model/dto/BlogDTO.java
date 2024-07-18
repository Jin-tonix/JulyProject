package com.ohgiraffers.blog.jaesuk.model.dto;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component

public class BlogDTO {
    private Integer blogNo;

    private String blogTitle;
    private String blogContent;
    private Date createDate;

    public BlogDTO() {
    }

    public BlogDTO(String blogTitle, String blogContent) {
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
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

    @Override
    public String toString() {
        return "BlogDTO{" +
                "blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                '}';
    }
}
