package com.ohgiraffers.blog.hwayeon.model.dto;

import javax.xml.crypto.Data;

public class HwayeonBlogDTO {

    private String blogTitle;
    private String blogContent;
    private Data createDate;
    private String imgUrl;
    private String category;

    public HwayeonBlogDTO() {
    }

    public HwayeonBlogDTO(Integer blogNo, String blogTitle, String blogContent, Data createDate, String imgUrl, String category) {
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.createDate = createDate;
        this.imgUrl = imgUrl;
        this.category = category;
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

    public Data getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Data createDate) {
        this.createDate = createDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "hwayeonBlogDTO{" +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", createDate=" + createDate +
                ", imgUrl='" + imgUrl + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
