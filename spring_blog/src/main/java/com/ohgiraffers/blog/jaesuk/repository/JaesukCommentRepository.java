package com.ohgiraffers.blog.jaesuk.repository;

import com.ohgiraffers.blog.jaesuk.model.entity.JaesukComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JaesukCommentRepository extends JpaRepository<JaesukComment, Long> {
    // 필요한 경우 여기에 추가 쿼리 메서드를 정의할 수 있습니다.
}