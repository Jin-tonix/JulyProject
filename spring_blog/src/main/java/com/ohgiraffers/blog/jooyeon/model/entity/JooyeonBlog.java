package com.ohgiraffers.blog.jooyeon.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "jooyeon_blog")
public class JooyeonBlog {

    @Id
    @Column(name = "blog_id") // 컬럼 이름도 변경
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // blogNo를 id로 변경

    @Column(name = "blog_title", unique = true, nullable = false)
    private String blogTitle;

    @Column(name = "blog_content", nullable = false, length = 5000)
    private String blogContent;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public JooyeonBlog() {


    }

    public JooyeonBlog(int id, String blogTitle, String blogContent, Date createDate) {
        this.id = id;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "JooyeonBlog{" +
                "id=" + id +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", createDate=" + createDate +
                '}';
    }


}

