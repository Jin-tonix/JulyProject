package com.ohgiraffers.blog.jinhee.restcontroller;

import com.ohgiraffers.blog.jinhee.model.dto.ReplyDTO;
import com.ohgiraffers.blog.jinhee.model.entity.Reply;
import com.ohgiraffers.blog.jinhee.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jinhee/postpage/comment")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PutMapping("/reply/{replyId}")
    public ResponseEntity<?> editReply(@PathVariable Long replyId, @RequestBody ReplyDTO replyDTO) {
        try {
            Reply updatedReply = replyService.editReply(replyId, replyDTO);
            return ResponseEntity.ok(updatedReply);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대댓글 수정 중 오류 발생");
        }
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId) {
        try {
            replyService.deleteReply(replyId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대댓글 삭제 중 오류 발생");
        }
    }
}
