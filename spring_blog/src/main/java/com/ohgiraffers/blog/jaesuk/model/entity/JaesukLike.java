package com.ohgiraffers.blog.jaesuk.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jaesuk_like")
public class JaesukLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blog_no")
    private JaesukBlog blog;

    private String userId;

    public JaesukLike() {}

    public JaesukLike(JaesukBlog blog, String userId) {
        this.blog = blog;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public JaesukBlog getBlog() { return blog; }
    public void setBlog(JaesukBlog blog) { this.blog = blog; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}