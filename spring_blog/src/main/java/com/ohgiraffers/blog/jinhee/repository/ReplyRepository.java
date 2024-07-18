package com.ohgiraffers.blog.jinhee.repository;

import com.ohgiraffers.blog.jinhee.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // JpaRepository를 상속받아 데이터베이스 조작 메서드를 제공
}
