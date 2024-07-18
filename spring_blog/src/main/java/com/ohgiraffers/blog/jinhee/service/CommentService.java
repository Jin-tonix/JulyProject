package com.ohgiraffers.blog.jinhee.service;

import com.ohgiraffers.blog.jinhee.model.dto.CommentDTO;
import com.ohgiraffers.blog.jinhee.model.entity.Comment;
import com.ohgiraffers.blog.jinhee.model.entity.JinheeBlog;
import com.ohgiraffers.blog.jinhee.repository.CommentRepository;
import com.ohgiraffers.blog.jinhee.repository.JinheeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final JinheeRepository jinheeRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, JinheeRepository jinheeRepository) {
        this.commentRepository = commentRepository;
        this.jinheeRepository = jinheeRepository;
    }

    public List<CommentDTO> getAllComments(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO createComment(Long blogId, CommentDTO commentDTO) {
        JinheeBlog blog = jinheeRepository.findById(blogId)
                .orElseThrow(() -> new IllegalArgumentException("Blog not found with id: " + blogId));

        Comment comment = new Comment();
        comment.setBlog(blog);
        comment.setContent(commentDTO.getContent());

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    @Transactional
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        comment.setContent(commentDTO.getContent());

        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        commentRepository.delete(comment);
    }

    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getContent());
    }
}
