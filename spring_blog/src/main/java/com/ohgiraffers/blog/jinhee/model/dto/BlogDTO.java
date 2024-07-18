package com.ohgiraffers.blog.jinhee.model.dto;

import java.util.Date;
import java.util.List;

public class BlogDTO {
    private Long id;
    private String blogTitle;
    private String blogContent;
    private Date createDate;
    private int likes;
    private List<FileDTO> imageFiles;

    public BlogDTO() {
    }

    public BlogDTO(Long id, String blogTitle, String blogContent, Date createDate, int likes, List<FileDTO> imageFiles) {
        this.id = id;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.createDate = createDate;
        this.likes = likes;
        this.imageFiles = imageFiles;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<FileDTO> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<FileDTO> imageFiles) {
        this.imageFiles = imageFiles;
    }

    @Override
    public String toString() {
        return "BlogDTO{" +
                "id=" + id +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", createDate=" + createDate +
                ", likes=" + likes +
                ", imageFiles=" + imageFiles +
                '}';
    }
}
