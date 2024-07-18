package com.ohgiraffers.blog.jaesuk.repository;

import com.ohgiraffers.blog.jaesuk.model.entity.JaesukBlog;
import com.ohgiraffers.blog.jaesuk.model.entity.JaesukLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<JaesukLike, Long> {
    long countByBlog(JaesukBlog blog);
    boolean existsByBlogAndUserId(JaesukBlog blog, String userId);
    void deleteByBlogAndUserId(JaesukBlog blog, String userId);
}