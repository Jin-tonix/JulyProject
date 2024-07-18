package com.ohgiraffers.blog.jinhee.restcontroller;

import com.ohgiraffers.blog.jinhee.model.dto.CommentDTO;
import com.ohgiraffers.blog.jinhee.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jinhee/postpage")
public class CommentRestController {

    private final CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{blogId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable Long blogId) {
        List<CommentDTO> comments = commentService.getAllComments(blogId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{blogId}/comment")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long blogId, @RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(blogId, commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
