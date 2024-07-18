package com.ohgiraffers.blog.jaesuk.service;

import com.ohgiraffers.blog.jaesuk.model.entity.JaesukBlog;
import com.ohgiraffers.blog.jaesuk.model.entity.JaesukLike;
import com.ohgiraffers.blog.jaesuk.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JaesukLikeService {

    private final LikeRepository likeRepository;


    @Autowired
    public JaesukLikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Transactional
    public boolean toggleLike(JaesukBlog blog, String userId) {
        if (likeRepository.existsByBlogAndUserId(blog, userId)) {
            likeRepository.deleteByBlogAndUserId(blog, userId);
            return false;
        } else {
            JaesukLike like = new JaesukLike(blog, userId);
            likeRepository.save(like);
            return true;
        }
    }

    public long getLikeCount(JaesukBlog blog) {
        return likeRepository.countByBlog(blog);
    }
}