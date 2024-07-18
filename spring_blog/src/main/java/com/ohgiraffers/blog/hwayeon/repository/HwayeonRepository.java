package com.ohgiraffers.blog.hwayeon.repository;

import com.ohgiraffers.blog.hwayeon.model.dto.HwayeonBlogDTO;
import com.ohgiraffers.blog.hwayeon.model.entity.HwayeonBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HwayeonRepository extends JpaRepository<HwayeonBlog, Integer> {

    HwayeonBlogDTO findByBlogNo(Integer blogNo);

}