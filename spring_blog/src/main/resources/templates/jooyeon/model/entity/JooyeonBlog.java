package com.ohgiraffers.blog.jooyeon.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "jooyeon_blog")
public class JooyeonBlog {

    @Id
    @Column(name = "blog_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogNo;

    @Column(name = "blog_title",unique = true, nullable = false)
    private String blogTitle;

    //블로그 글자제한 length
    @Column(name = "blog_content",nullable = false,length = 5000)
    private String blogContent;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public JooyeonBlog() {
    }

    public JooyeonBlog(int blogNo, String blogTitle, String blogContent, Date createDate) {
        this.blogNo = blogNo;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.createDate = createDate;
    }

    public int getBlogNo() {
        return blogNo;
    }

    public void setBlogNo(int blogNo) {
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



    // 추가된 setId 메서드
    public void setId(int blogNo) {
        this.blogNo = blogNo;
    }

    public String getId() {
        return getId();
    }
}
