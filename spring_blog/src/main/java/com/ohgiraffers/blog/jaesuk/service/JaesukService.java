package com.ohgiraffers.blog.jaesuk.service;

import com.ohgiraffers.blog.jaesuk.model.entity.JaesukBlog;
import com.ohgiraffers.blog.jaesuk.model.entity.JaesukComment;
import com.ohgiraffers.blog.jaesuk.repository.JaesukRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JaesukService {

    private static final Logger logger = LoggerFactory.getLogger(JaesukService.class);
    private final JaesukRepository jaesukRepository;

    @Autowired
    public JaesukService(JaesukRepository jaesukRepository) {
        this.jaesukRepository = jaesukRepository;
    }

    public List<JaesukBlog> getAllPosts() {
        List<JaesukBlog> posts = jaesukRepository.findAll();
        posts.forEach(this::setFormattedDate);
        logger.info("Fetched {} posts", posts.size());
        return posts;
    }

    public JaesukBlog getPostById(Integer id) {
        logger.info("Fetching post with id: {}", id);
        return jaesukRepository.findById(id)
                .map(post -> {
                    setFormattedDate(post);
                    return post;
                })
                .orElseThrow(() -> {
                    logger.error("Post not found with id: {}", id);
                    return new RuntimeException("Post not found with id: " + id);
                });
    }

    @Transactional
    public JaesukBlog createPost(JaesukBlog post) {
        if (post.getTitle() == null || post.getTitle().trim().isEmpty()) {
            post.setTitle("(제목 없음)");
        }
        post.setCreationDate(new Date());
        JaesukBlog savedPost = jaesukRepository.save(post);
        setFormattedDate(savedPost);
        logger.info("Created new post: id={}, title={}", savedPost.getId(), savedPost.getTitle());
        return savedPost;
    }

    @Transactional
    public JaesukBlog updatePost(Integer id, JaesukBlog updatedPost) {
        return jaesukRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    JaesukBlog savedPost = jaesukRepository.save(post);
                    setFormattedDate(savedPost);
                    logger.info("Updated post: id={}, title={}", savedPost.getId(), savedPost.getTitle());
                    return savedPost;
                })
                .orElseThrow(() -> {
                    logger.error("Post not found for update with id: {}", id);
                    return new RuntimeException("Post not found for update with id: " + id);
                });
    }

    @Transactional
    public void addCommentToPost(Integer postId, JaesukComment comment) {
        JaesukBlog post = getPostById(postId);
        if (post != null) {
            comment.setBlog(post);
            post.addComment(comment);
            jaesukRepository.save(post);
            logger.info("Added comment to post with id: {}", postId);
        } else {
            logger.error("Failed to add comment. Post not found with id: {}", postId);
            throw new RuntimeException("Post not found with id: " + postId);
        }
    }

    @Transactional
    public boolean deletePost(Integer id) {
        try {
            Optional<JaesukBlog> postOptional = jaesukRepository.findById(id);
            if (postOptional.isPresent()) {
                jaesukRepository.delete(postOptional.get());
                logger.info("Deleted post: id={}", id);
                return true;
            } else {
                logger.warn("Post not found for deletion with id: {}", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error deleting post with id: {}", id, e);
            return false;
        }
    }

    @Transactional
    public void deleteComment(Integer postId, Long commentId) {
        JaesukBlog post = getPostById(postId);
        if (post != null) {
            boolean removed = post.getComments().removeIf(comment -> comment.getId().equals(commentId));
            if (removed) {
                jaesukRepository.save(post);
                logger.info("Deleted comment with id: {} from post with id: {}", commentId, postId);
            } else {
                logger.warn("Comment with id: {} not found in post with id: {}", commentId, postId);
                throw new RuntimeException("Comment not found with id: " + commentId);
            }
        } else {
            logger.error("Failed to delete comment. Post not found with id: {}", postId);
            throw new RuntimeException("Post not found with id: " + postId);
        }
    }

    private void setFormattedDate(JaesukBlog post) {
        if (post.getCreationDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 HH:mm");
            post.setFormattedDate(sdf.format(post.getCreationDate()));
        }
    }
}