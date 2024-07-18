package com.ohgiraffers.blog.jaesuk.model.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "jaesuk_blog")
public class JaesukBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 5000)
    private String content = "";

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate = new Date();

    @Transient
    private String formattedDate;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JaesukComment> jaesukComments = new ArrayList<>();

    public JaesukBlog() {
    }

    public JaesukBlog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public List<JaesukComment> getComments() {
        return jaesukComments;
    }

    public void setComments(List<JaesukComment> jaesukComments) {
        this.jaesukComments = jaesukComments;
    }

    public void addComment(JaesukComment jaesukComment) {
        jaesukComments.add(jaesukComment);
        jaesukComment.setBlog(this);
    }

    public void removeComment(JaesukComment jaesukComment) {
        jaesukComments.remove(jaesukComment);
        jaesukComment.setBlog(null);
    }

    @Override
    public String toString() {
        return "JaesukBlog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", formattedDate='" + formattedDate + '\'' +
                '}';
    }
}