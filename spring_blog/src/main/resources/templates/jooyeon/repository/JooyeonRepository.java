package com.ohgiraffers.blog.jooyeon.repository;

import com.ohgiraffers.blog.jooyeon.model.entity.JooyeonBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JooyeonRepository extends JpaRepository<JooyeonBlog, Integer> {

}
