package com.ohgiraffers.blog.jaesuk.model.dto;

import com.ohgiraffers.blog.hwayeon.model.dto.HwayeonBlogDTO;

public class BlogDTO extends HwayeonBlogDTO {

    private String blogTitle;
    private String blogContent;

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
