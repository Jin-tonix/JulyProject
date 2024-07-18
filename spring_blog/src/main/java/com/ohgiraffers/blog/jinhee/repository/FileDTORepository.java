package com.ohgiraffers.blog.jinhee.repository;

import com.ohgiraffers.blog.jinhee.model.dto.FileDTO;
import com.ohgiraffers.blog.jinhee.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDTORepository extends JpaRepository<File, Long> {
    // 특별히 추가할 메서드가 없다면 기본적으로 JpaRepository의 메서드를 상속받아 사용
}
