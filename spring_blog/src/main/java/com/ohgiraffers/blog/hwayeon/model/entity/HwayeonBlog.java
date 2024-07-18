package com.ohgiraffers.blog.hwayeon.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "hwayeon_blog")
public class HwayeonBlog {

    @Id
    @Column(name = "blog_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogNo;

    @Column(name = "blog_title", unique = true, nullable = false)
    private String blogTitle;

    @Column(name = "blog_content", nullable = false, length = 3000)
    private String blogContent;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "category")
    private String category;

    public HwayeonBlog() {
    }

    public HwayeonBlog(Integer blogNo, String blogTitle, String blogContent, Date createDate, String imgUrl, String category) {
        this.blogNo = blogNo;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.createDate = createDate;
        this.imgUrl = imgUrl;
        this.category = category;
    }

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

    @Override
    public String toString() {
        return "HwayeonBlog{" +
                "blogNo=" + blogNo +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", createDate=" + createDate +
                ", imgUrl='" + imgUrl + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
