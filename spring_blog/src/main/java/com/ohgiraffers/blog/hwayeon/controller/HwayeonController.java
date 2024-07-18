package com.ohgiraffers.blog.hwayeon.controller;

import com.ohgiraffers.blog.hwayeon.model.entity.HwayeonBlog;
import com.ohgiraffers.blog.hwayeon.service.HwayeonService;
import com.ohgiraffers.blog.hwayeon.model.dto.HwayeonBlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hwayeon")
public class HwayeonController {

    private final HwayeonService hwayeonService; // HwayeonService 의존성 주입을 위한 필드
    private HwayeonBlogDTO currentBlog; // 현재 블로그 DTO 필드

        @Autowired
    public HwayeonController(HwayeonService hwayeonService) {
        this.hwayeonService = hwayeonService; // 생성자를 통한 의존성 주입
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        // 데이터베이스에서 모든 블로그 포스트 가져오기
        List<HwayeonBlog> allPosts = hwayeonService.getAllPosts();

        // 모든 블로그 포스트를 모델에 추가
        model.addAttribute("posts", allPosts);

//        // 데이터베이스에서 최신 블로그 정보 가져오기
//        Optional<HwayeonBlog> latestPost = hwayeonService.getLatestPost();
//
//        // 블로그 정보가 존재하면 모델에 추가
//        if (latestPost.isPresent()) {
//            model.addAttribute("blogTitle", latestPost.get().getBlogTitle());
//            model.addAttribute("blogContent", latestPost.get().getBlogContent());
//        } else {
//            model.addAttribute("blogTitle", "제목을 찾을 수 없습니다.");
//            model.addAttribute("blogContent", "내용을 찾을 수 없습니다.");
//        }

        return "hwayeon/main"; // 뷰 이름 반환
    }

    // 게시글 작성 페이지 요청 처리
    @GetMapping("/editpage")
    public String editpagePage() {
        return "hwayeon/editpage"; // 뷰 이름 반환
    }

    // 포스트 페이지 요청 처리
    @GetMapping("/postpage")
    public String postPage(Model model) {
        // 현재 블로그가 존재할 경우 데이터 전달
        if (currentBlog != null) {
            model.addAttribute("blogTitle", currentBlog.getBlogTitle());
            model.addAttribute("blogContent", currentBlog.getBlogContent());
        }
        return "hwayeon/postpage"; // 뷰 이름 반환
    }

    // 포스트 요청 처리
    @PostMapping
    public ModelAndView postBlog(HwayeonBlogDTO hyblogDTO, ModelAndView mv) {
        // 제목이나 내용이 비어있을 경우 에러 처리를 위한 리다이렉트
        if (hyblogDTO.getBlogTitle() == null || hyblogDTO.getBlogTitle().equals("")) {
            mv.setViewName("redirect:/hwayeon/editpage");
            return mv;
        }
        if (hyblogDTO.getBlogContent() == null || hyblogDTO.getBlogContent().equals("")) {
            mv.setViewName("redirect:/hwayeon/editpage");
            return mv;
        }

        // 서비스를 통해 포스트를 저장하고 결과에 따라 처리
        int result = hwayeonService.post(hyblogDTO);

        if (result <= 0) {
            mv.setViewName("error/page"); // 에러 페이지 뷰 반환
        } else {
            currentBlog = hyblogDTO; // 현재 블로그 필드 업데이트
            mv.setViewName("redirect:/hwayeon/postpage"); // 포스트 페이지 리다이렉트
        }
        return mv; // ModelAndView 반환
    }

    // 수정 페이지 요청 처리
    @GetMapping("/modifypage")
    public String modifyPage(Model model) {
        // 현재 블로그 정보를 가져와서 모델에 추가
        Optional<HwayeonBlog> latestPost = hwayeonService.getLatestPost();
        if (latestPost.isPresent()) {
            model.addAttribute("blogPost", latestPost.get());
        } else {
            // 예외 처리 로직 추가
        }
        return "hwayeon/modifypage"; // 수정 페이지 뷰 이름 반환
    }

    // 수정 처리 요청 처리
    @PostMapping("/update")
    public String updatePost(@ModelAttribute("blogPost") HwayeonBlog updatedPost) {
        // 실제 데이터베이스 업데이트 로직
        hwayeonService.updatePost(updatedPost);
        return "redirect:/hwayeon/main"; // 수정 후 메인 페이지로 리다이렉트
    }

}
