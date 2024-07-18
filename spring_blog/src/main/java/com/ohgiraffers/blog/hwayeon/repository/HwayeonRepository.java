package com.ohgiraffers.blog.hwayeon.repository;

import com.ohgiraffers.blog.hwayeon.model.entity.HwayeonBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HwayeonRepository extends JpaRepository<HwayeonBlog, Integer> {


}
