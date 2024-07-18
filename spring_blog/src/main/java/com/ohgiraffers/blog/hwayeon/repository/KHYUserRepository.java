package com.ohgiraffers.blog.hwayeon.repository;

import com.ohgiraffers.blog.jinhee.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KHYUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
