package com.ohgiraffers.blog.jooyeon.controller;


import com.ohgiraffers.blog.jooyeon.model.dto.BlogDTO;
import com.ohgiraffers.blog.jooyeon.service.JooyeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/jooyeon")
public class JooyeonController {

    private final JooyeonService jooyeonService;
    private BlogDTO currentBlog;

    @Autowired
    public JooyeonController(JooyeonService jooyeonService) {

        this.jooyeonService = jooyeonService;
    }

    @GetMapping("/blogFirst")
    public String blogfirst() {
        return "/jooyeon/blogFirst";

    }

    @GetMapping("/regist")
    public String regist() {
        return "/jooyeon/regist";

    }

    // 등록 후 보여지는 페이지 불러오는 맵핑
    @GetMapping("/registList")
    public ModelAndView showRegistList() {
        ModelAndView mv = new ModelAndView("/jooyeon/registList");

        List<BlogDTO> blogDTOs = jooyeonService.listBlogs();

        mv.addObject("blogDTOs", blogDTOs);
        return mv;
    }

    @GetMapping("/jypage")
    public ModelAndView jypage() {
        ModelAndView mv = new ModelAndView("/jooyeon/jypage");

        if (currentBlog != null) {
            mv.addObject("blogTitle", currentBlog.getBlogTitle());
            mv.addObject("blogContent", currentBlog.getBlogContent());
        }

        return mv;
    }

    // DB에 내용을 등록시켜주는 메서드
    @PostMapping("/registList")
    public ModelAndView handlePostRequest(BlogDTO blogDTO, ModelAndView mv) {
        if (blogDTO.getBlogTitle() == null || blogDTO.getBlogTitle().isEmpty()) {
            mv.setViewName("redirect:/jooyeon/regist");
            return mv;
        }
        if (blogDTO.getBlogContent() == null || blogDTO.getBlogContent().isEmpty()) {
            mv.setViewName("redirect:/jooyeon/regist");
            return mv;
        }

        int result = jooyeonService.post(blogDTO);

        if (result <= 0) {
            mv.setViewName("error/page");
        } else {
            mv.addObject("blogDTO", blogDTO); // 등록된 블로그 정보를 모델에 추가
            mv.setViewName("redirect:/jooyeon/registList"); // 등록된 내용이 보여지는 페이지로 리다이렉트
        }

        return mv;
    }


}



