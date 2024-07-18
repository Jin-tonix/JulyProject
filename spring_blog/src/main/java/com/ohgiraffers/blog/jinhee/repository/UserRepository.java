package com.ohgiraffers.blog.jinhee.repository;

import com.ohgiraffers.blog.jinhee.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
