package com.ohgiraffers.blog.jooyeon.controller;


import com.ohgiraffers.blog.jooyeon.model.dto.BlogDTO;
import com.ohgiraffers.blog.jooyeon.service.JooyeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/jooyeon")
public class JooyeonController {

    private final JooyeonService jooyeonService;
    private BlogDTO currentBlog; // 현재 선택된 블로그를 저장하는 변수

    @Autowired
    public JooyeonController(JooyeonService jooyeonService) {
        this.jooyeonService = jooyeonService;
    }

    // 초기 블로그First 페이지를 표시하는 핸들러
    @GetMapping("/blogFirst")
    public String blogfirst() {
        return "/jooyeon/blogFirst"; // 블로그First 페이지의 뷰 이름 반환
    }

    // 등록 페이지를 표시하는 핸들러
    @GetMapping("/regist")
    public String regist() {
        return "/jooyeon/regist"; // 등록 페이지의 뷰 이름 반환
    }

    // 등록된 블로그 목록을 표시하는 핸들러
    @GetMapping("/registList")
    public ModelAndView showRegistList() {
        ModelAndView mv = new ModelAndView("/jooyeon/registList"); // ModelAndView를 등록된 블로그 목록 뷰로 초기화

        List<BlogDTO> blogDTOs = jooyeonService.listBlogs(); // 서비스에서 블로그 목록을 가져옴

        mv.addObject("blogDTOs", blogDTOs); // 블로그 목록을 ModelAndView에 추가

        return mv; // 블로그 목록이 포함된 ModelAndView 반환
    }

    // jypage(블로그 상세 페이지)를 현재 블로그가 설정된 경우에만 표시하는 핸들러
    @GetMapping("/jypage")
    public ModelAndView jypage() {
        ModelAndView mv = new ModelAndView("/jooyeon/jypage"); // ModelAndView를 jypage 뷰로 초기화

        if (currentBlog != null) {
            mv.addObject("blogTitle", currentBlog.getBlogTitle()); // 현재 블로그 제목을 ModelAndView에 추가
            mv.addObject("blogContent", currentBlog.getBlogContent()); // 현재 블로그 내용을 ModelAndView에 추가
        }

        return mv; // 현재 블로그 세부 정보가 포함된 ModelAndView 반환
    }

    // 블로그 등록 양식 제출을 처리하는 핸들러
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


    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Integer id) {
        ModelAndView mv = new ModelAndView("/jooyeon/edit");
        com.ohgiraffers.blog.jooyeon.model.dto.BlogDTO blogDTO = jooyeonService.getBlogById(id);
        if (blogDTO != null) {
            mv.addObject("blogDTO", blogDTO);
        } else {
            mv.setViewName("error/404"); // 블로그를 찾을 수 없는 경우 404 오류 페이지로 리다이렉트
        }
        return mv;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView handleEditForm(@PathVariable("id") Integer id, @ModelAttribute BlogDTO blogDTO) {
        jooyeonService.updateBlog(blogDTO); // 블로그 업데이트 서비스 호출
        return new ModelAndView("redirect:/jooyeon/registList"); // 수정된 내용이 보여지는 페이지로 리다이렉트
    }

    @GetMapping("/delete/{id}")
    public ModelAndView handleDelete(@PathVariable("id") Integer id) {
        jooyeonService.deleteBlogPost(id); // 블로그 포스트 삭제
        return new ModelAndView("redirect:/jooyeon/registList"); // 삭제 후 목록 페이지로 리다이렉트
    }


}