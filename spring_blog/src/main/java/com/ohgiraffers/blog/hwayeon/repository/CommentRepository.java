package com.ohgiraffers.blog.hwayeon.repository;

import com.ohgiraffers.blog.hwayeon.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {


}
