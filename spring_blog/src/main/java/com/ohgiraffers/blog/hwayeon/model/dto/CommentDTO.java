package com.ohgiraffers.blog.hwayeon.model.dto;

import java.util.Date;

public class CommentDTO {

    private Integer commentNo;
    private Integer blogNo;
    private String author;
    private String content;
    private Date creationDate;

    public CommentDTO() {
    }

    public CommentDTO(Integer commentNo, Integer blogNo, String author, String content, Date creationDate) {
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
