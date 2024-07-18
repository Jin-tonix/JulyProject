package com.ohgiraffers.blog.hwayeon.model.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hwayeon_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Integer commentNo;

    @Column(name = "blog_no", nullable = false)
    private Integer blogNo;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Comment() {
    }

    public Comment(Integer commentNo, Integer blogNo, String author, String content, Date creationDate) {
        this.commentNo = commentNo;
        this.blogNo = blogNo;
        this.author = author;
        this.content = content;
        this.creationDate = creationDate;
    }

    // Getters and setters
    public Integer getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(Integer commentNo) {
        this.commentNo = commentNo;
    }

    public Integer getBlogNo() {
        return blogNo;
    }

    public void setBlogNo(Integer blogNo) {
        this.blogNo = blogNo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
