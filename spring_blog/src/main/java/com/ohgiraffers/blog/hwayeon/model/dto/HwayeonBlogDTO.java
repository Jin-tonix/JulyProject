package com.ohgiraffers.blog.hwayeon.model.dto;

import java.util.Date;

public class HwayeonBlogDTO {

    private Integer blogNo;
    private String blogTitle;
    private String blogContent;
    private Date createDate;
    private String imgUrl;
    private String category;
    private Integer  likes;

    public HwayeonBlogDTO() {
    }

    public HwayeonBlogDTO(Integer blogNo, String blogTitle, String blogContent, Date createDate, String imgUrl, String category, Integer likes) {
        this.blogNo = blogNo;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.createDate = createDate;
        this.imgUrl = imgUrl;
        this.category = category;
        this.likes = likes;
    }

    // Getters and setters
    public Integer getBlogNo() {
        return blogNo;
    }

    public void setBlogNo(Integer blogNo) {
        this.blogNo = blogNo;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "HwayeonBlogDTO{" +
                "blogNo=" + blogNo +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", createDate=" + createDate +
                ", imgUrl='" + imgUrl + '\'' +
                ", category='" + category + '\'' +
                ", likes=" + likes +
                '}';
    }
}
