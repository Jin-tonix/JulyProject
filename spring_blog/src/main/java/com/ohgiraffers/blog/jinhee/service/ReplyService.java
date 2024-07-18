package com.ohgiraffers.blog.jinhee.service;

import com.ohgiraffers.blog.jinhee.model.dto.ReplyDTO;
import com.ohgiraffers.blog.jinhee.model.entity.Reply;
import com.ohgiraffers.blog.jinhee.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public Reply editReply(Long replyId, ReplyDTO replyDTO) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("대댓글을 찾을 수 없습니다: " + replyId));

        // Update reply content
        reply.setContent(replyDTO.getContent());

        // Save updated reply
        return replyRepository.save(reply);
    }

    public void deleteReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("대댓글을 찾을 수 없습니다: " + replyId));

        replyRepository.delete(reply);
    }
}
