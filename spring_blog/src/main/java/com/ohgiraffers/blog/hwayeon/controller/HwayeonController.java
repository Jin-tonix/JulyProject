package com.ohgiraffers.blog.hwayeon.controller;

import com.ohgiraffers.blog.hwayeon.model.dto.CommentDTO;
import com.ohgiraffers.blog.hwayeon.model.dto.HwayeonBlogDTO;
import com.ohgiraffers.blog.hwayeon.service.HwayeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/hwayeon")
public class HwayeonController {

    private final HwayeonService hwayeonService;

    @Autowired
    public HwayeonController(HwayeonService hwayeonService) {
        this.hwayeonService = hwayeonService;
    }

    // 메인 페이지 불러오기
    @GetMapping("/main")
    public ModelAndView main() {
        ModelAndView mv = new ModelAndView("/hwayeon/main");

        List<HwayeonBlogDTO> allPosts = hwayeonService.findAllPosts();

        // 게시글 목록이 비어있는 경우 메시지 설정
        if (allPosts.isEmpty()) {
            mv.addObject("message", "아직 등록된 글이 없습니다.");

        } else {
            mv.addObject("allPosts", allPosts);
        }


        } else {
            mv.addObject("allPosts", allPosts);
        }
        return mv;
    }

    // 리스트 페이지 불러오기
    @GetMapping("/list")
    public ModelAndView listpage() {
        ModelAndView mv = new ModelAndView("/hwayeon/listpage");

        List<HwayeonBlogDTO> latestPosts = hwayeonService.findAllPosts();
        mv.addObject("latestPosts", latestPosts);

        return mv;
    }

    // 등록 페이지 불러오기
    @GetMapping("/newpost")
    public ModelAndView showNewPostPage() {
        ModelAndView mv = new ModelAndView("/hwayeon/editpage"); // ModelAndView 객체 생성 및 View 설정

        mv.addObject("blog", new HwayeonBlogDTO()); // 뷰에 빈 HwayeonBlogDTO 객체를 blog 이름으로 전달

        return mv; // ModelAndView 객체 반환
    }

    // 수정 페이지 불러오기
    @GetMapping("/editpage")
    public ModelAndView showEditPage(@RequestParam(name = "blogNo", required = true) Integer blogNo) {
        ModelAndView mv = new ModelAndView("/hwayeon/modifypage"); // ModelAndView 객체 생성 및 View 설정

    @GetMapping("/modifypage")
    public ModelAndView showEditPage(@RequestParam(name = "blogNo", required = false) Integer blogNo) {
        ModelAndView mv = new ModelAndView("/hwayeon/modifypage");
        System.out.println("HTTP 요청 파라미터 blogNo: " + blogNo); // 로그 추가

        if (blogNo != null) {
            HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo);
            System.out.println("hwayeonService.findByBlogNo(blogNo) blog: " + blogDTO); // 로그 추가
            mv.addObject("blog", blogDTO);
        } else {
            mv.addObject("blog", new HwayeonBlogDTO());
            mv.addObject("error", "Invalid blog number");
        }
        return mv;
    }
//    @GetMapping("/modifypage")
//    public ModelAndView showEditPage(@RequestParam(name = "blogNo", required = true) Integer blogNo) {
//        ModelAndView mv = new ModelAndView("/hwayeon/modifypage");
//        HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo);
//        mv.addObject("blog", blogDTO);
//        return mv;
//    }

//    @GetMapping("/modifypage")
//    public ModelAndView showEditPage(@RequestParam(name = "blogNo", required = true) Integer blogNo) {
//        ModelAndView mv = new ModelAndView("/hwayeon/modifypage"); // ModelAndView 객체 생성 및 View 설정
//
//        if (blogNo != null) {
//            HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo); // blogNo에 해당하는 게시글 조회
//            mv.addObject("blog", blogDTO); // 뷰에 조회된 블로그 객체를 blog 이름으로 전달
//        } else {
//            mv.addObject("blog", new HwayeonBlogDTO()); // blogNo가 없으면 빈 HwayeonBlogDTO 객체를 blog 이름으로 전달
//        }
//        return mv; // ModelAndView 객체 반환
//    }

    // 수정 완료 처리
    @PostMapping("/update")
    public String updatePost(@ModelAttribute HwayeonBlogDTO blogDTO) {
        boolean isUpdated = hwayeonService.updatePost(blogDTO);
        if (isUpdated) {
            return "redirect:/hwayeon/postpage?blogNo=" + blogDTO.getBlogNo();
        } else {
            return "redirect:/hwayeon/modifypage?blogNo=" + blogDTO.getBlogNo() + "&error=true";
        }
    }
//    @PostMapping("/update")
//    public ModelAndView updatePost(HwayeonBlogDTO blogDTO) {
//        boolean isUpdated = hwayeonService.updatePost(blogDTO); // 수정 작업 수행 및 성공 여부 확인
//
//        ModelAndView mv = new ModelAndView(); // ModelAndView 객체 생성
//
//        if (isUpdated) {
//            // 수정 후 상세 페이지로 리다이렉트
//            mv.setViewName("redirect:/hwayeon/postpage?blogNo=" + blogDTO.getBlogNo());
//        } else {
//            // 수정 실패 시 다시 수정 페이지로 이동
//            mv.setViewName("/hwayeon/modifypage");
//            mv.addObject("blog", blogDTO);
//            mv.addObject("error", "수정에 실패했습니다. 다시 시도해주세요.");
//        }
//
//        return mv; // ModelAndView 객체 반환
//    }

    // 내용 등록하기
    @PostMapping("/postpage")
    public ModelAndView submitPost(HwayeonBlogDTO blogDTO) {
        ModelAndView mv = new ModelAndView(); // ModelAndView 객체 생성

        // 게시글 제목이 null이거나 빈 문자열인 경우
        if (blogDTO.getBlogTitle() == null || blogDTO.getBlogTitle().isEmpty() ||
                // 게시글 내용이 null이거나 빈 문자열인 경우
                blogDTO.getBlogContent() == null || blogDTO.getBlogContent().isEmpty()) {

            // 오류 메시지를 담아서 새 글 작성 페이지로 리다이렉트
            mv.setViewName("redirect:/hwayeon/newpost");
            mv.addObject("error", "제목과 내용은 필수 입력 항목입니다!");
            return mv; // 메서드 종료
        }

        // 게시글 저장 서비스 메소드 호출
        hwayeonService.savePost(blogDTO);

        // 게시글 저장 후 세부 페이지로 이동할 ModelAndView 설정
        mv.setViewName("/hwayeon/postpage");
        mv.addObject("blogTitle", blogDTO.getBlogTitle()); // 게시글 제목 전달
        mv.addObject("blogContent", blogDTO.getBlogContent()); // 게시글 내용 전달
        mv.addObject("blogNo", blogDTO.getBlogNo()); // 게시글 번호 전달

        return mv; // ModelAndView 객체 반환

    public String submitPost(HwayeonBlogDTO blogDTO) {
        HwayeonBlogDTO savedBlog = hwayeonService.savePost(blogDTO);
        System.out.println("컨트롤러 포스트맵핑 : /hwayeon/postpage?blogNo=" + savedBlog.getBlogNo());
        return "redirect:/hwayeon/postpage?blogNo=" + savedBlog.getBlogNo();

    }
//    @PostMapping("/postpage")
//    public ModelAndView submitPost(HwayeonBlogDTO blogDTO) {
//        ModelAndView mv = new ModelAndView(); // ModelAndView 객체 생성
//
//        // 게시글 제목이 null이거나 빈 문자열인 경우
//        if (blogDTO.getBlogTitle() == null || blogDTO.getBlogTitle().isEmpty() ||
//                // 게시글 내용이 null이거나 빈 문자열인 경우
//                blogDTO.getBlogContent() == null || blogDTO.getBlogContent().isEmpty()) {
//
//            // 오류 메시지를 담아서 새 글 작성 페이지로 리다이렉트
//            mv.setViewName("redirect:/hwayeon/newpost");
//            mv.addObject("error", "제목과 내용은 필수 입력 항목입니다!");
//            return mv; // 메서드 종료
//        }
//
//        // 게시글 저장 서비스 메소드 호출
//        hwayeonService.savePost(blogDTO);
//
//        // 게시글 저장 후 세부 페이지로 이동할 ModelAndView 설정
//        mv.setViewName("/hwayeon/postpage");
//        mv.addObject("blogNo", blogDTO.getBlogNo()); // 게시글 번호 전달
//        mv.addObject("blogTitle", blogDTO.getBlogTitle()); // 게시글 제목 전달
//        mv.addObject("blogContent", blogDTO.getBlogContent()); // 게시글 내용 전달
//
//        return mv; // ModelAndView 객체 반환
//    }

      
    // 내용 수정하기
    @PostMapping("/update")
    public ModelAndView updatePost(HwayeonBlogDTO blogDTO) {
        boolean isUpdated = hwayeonService.updatePost(blogDTO); // 수정 작업 수행 및 성공 여부 확인

        ModelAndView mv = new ModelAndView("/hwayeon/postpage"); // ModelAndView 객체 생성 및 View 설정

        mv.addObject("blogTitle", blogDTO.getBlogTitle()); // 뷰에 게시글 제목을 blogTitle 이름으로 전달
        mv.addObject("blogContent", blogDTO.getBlogContent()); // 뷰에 게시글 내용을 blogContent 이름으로 전달
        mv.addObject("blogNo", blogDTO.getBlogNo()); // 뷰에 게시글 번호를 blogNo 이름으로 전달
        mv.addObject("isUpdated", isUpdated); // 수정 여부를 뷰에 전달

        return mv; // ModelAndView 객체 반환

    // 상세 페이지 조회
    @GetMapping("/postpage")
    public ModelAndView showPostPage(@RequestParam("blogNo") Integer blogNo) {
        ModelAndView mv = new ModelAndView("/hwayeon/postpage");
        HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo);
        mv.addObject("blog", blogDTO);
        return mv;

    }
//    @GetMapping("/postpage")
//    public ModelAndView showPostPage(@RequestParam(name = "blogNo", required = true) Integer blogNo) {
//        ModelAndView mv = new ModelAndView("/hwayeon/postpage");
//
//        HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo);
//
//        mv.addObject("blogTitle", blogDTO.getBlogTitle());
//        mv.addObject("blogContent", blogDTO.getBlogContent());
//        mv.addObject("blogNo", blogDTO.getBlogNo());  // 이 부분이 중요합니다
//
//        System.out.println("Sending to postpage - BlogNo: " + blogDTO.getBlogNo()); // 로그 추가
//
//        return mv;
//    }

    // 삭제 처리하기 (DELETE 메소드)
    @DeleteMapping("/delete/{blogNo}")
    public String deletePost(@PathVariable("blogNo") Integer blogNo) {

        boolean isDeleted = hwayeonService.deletePost(blogNo);  // 게시글 삭제 메소드 호출


        boolean isDeleted = hwayeonService.deletePost(blogNo);  // 게시글 삭제 메소드 호출

        if (isDeleted) {
            return "redirect:/hwayeon/list";  // 삭제 성공 시 리다이렉트
        } else {
            // 삭제 실패 처리
            ModelAndView errorMv = new ModelAndView("/hwayeon/list");
            errorMv.addObject("error", "게시글 삭제에 실패했습니다.");
            return "redirect:/hwayeon/list";  // 삭제 실패 시 리다이렉트
        }
    }


        } else {
            mv.addObject("allPosts", allPosts);
        }
        return mv;
    }

    // 리스트 페이지 불러오기
    @GetMapping("/list")
    public ModelAndView listpage() {
        ModelAndView mv = new ModelAndView("/hwayeon/listpage");

        List<HwayeonBlogDTO> latestPosts = hwayeonService.findAllPosts();
        mv.addObject("latestPosts", latestPosts);

        return mv;
    }

    // 등록 페이지 불러오기
    @GetMapping("/newpost")
    public ModelAndView showNewPostPage() {
        ModelAndView mv = new ModelAndView("/hwayeon/editpage"); // ModelAndView 객체 생성 및 View 설정

        mv.addObject("blog", new HwayeonBlogDTO()); // 뷰에 빈 HwayeonBlogDTO 객체를 blog 이름으로 전달

        return mv; // ModelAndView 객체 반환
    }

    // 수정 페이지 불러오기
    @GetMapping("/modifypage")
    public ModelAndView showEditPage(@RequestParam(name = "blogNo", required = false) Integer blogNo) {
        ModelAndView mv = new ModelAndView("/hwayeon/modifypage");
        System.out.println("Received blogNo: " + blogNo); // 로그 추가

        if (blogNo != null) {
            HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo);
            System.out.println("Found blog: " + blogDTO); // 로그 추가
            mv.addObject("blog", blogDTO);
        } else {
            mv.addObject("blog", new HwayeonBlogDTO());
            mv.addObject("error", "Invalid blog number");
        }
        return mv;
    }
//    @GetMapping("/modifypage")
//    public ModelAndView showEditPage(@RequestParam(name = "blogNo", required = true) Integer blogNo) {
//        ModelAndView mv = new ModelAndView("/hwayeon/modifypage");
//        HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo);
//        mv.addObject("blog", blogDTO);
//        return mv;
//    }

//    @GetMapping("/modifypage")
//    public ModelAndView showEditPage(@RequestParam(name = "blogNo", required = true) Integer blogNo) {
//        ModelAndView mv = new ModelAndView("/hwayeon/modifypage"); // ModelAndView 객체 생성 및 View 설정
//
//        if (blogNo != null) {
//            HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo); // blogNo에 해당하는 게시글 조회
//            mv.addObject("blog", blogDTO); // 뷰에 조회된 블로그 객체를 blog 이름으로 전달
//        } else {
//            mv.addObject("blog", new HwayeonBlogDTO()); // blogNo가 없으면 빈 HwayeonBlogDTO 객체를 blog 이름으로 전달
//        }
//        return mv; // ModelAndView 객체 반환
//    }

    // 수정 완료 처리
    @PostMapping("/update")
    public String updatePost(@ModelAttribute HwayeonBlogDTO blogDTO) {
        boolean isUpdated = hwayeonService.updatePost(blogDTO);
        if (isUpdated) {
            return "redirect:/hwayeon/postpage?blogNo=" + blogDTO.getBlogNo();
        } else {
            return "redirect:/hwayeon/modifypage?blogNo=" + blogDTO.getBlogNo() + "&error=true";
        }
    }
//    @PostMapping("/update")
//    public ModelAndView updatePost(HwayeonBlogDTO blogDTO) {
//        boolean isUpdated = hwayeonService.updatePost(blogDTO); // 수정 작업 수행 및 성공 여부 확인
//
//        ModelAndView mv = new ModelAndView(); // ModelAndView 객체 생성
//
//        if (isUpdated) {
//            // 수정 후 상세 페이지로 리다이렉트
//            mv.setViewName("redirect:/hwayeon/postpage?blogNo=" + blogDTO.getBlogNo());
//        } else {
//            // 수정 실패 시 다시 수정 페이지로 이동
//            mv.setViewName("/hwayeon/modifypage");
//            mv.addObject("blog", blogDTO);
//            mv.addObject("error", "수정에 실패했습니다. 다시 시도해주세요.");
//        }
//
//        return mv; // ModelAndView 객체 반환
//    }

    // 내용 등록하기
    @PostMapping("/postpage")
    public String submitPost(HwayeonBlogDTO blogDTO) {
        HwayeonBlogDTO savedBlog = hwayeonService.savePost(blogDTO);
        System.out.println("컨트롤러 포스트맵핑 : /hwayeon/postpage?blogNo=" + savedBlog.getBlogNo());
        return "redirect:/hwayeon/postpage?blogNo=" + savedBlog.getBlogNo();
    }
//    @PostMapping("/postpage")
//    public ModelAndView submitPost(HwayeonBlogDTO blogDTO) {
//        ModelAndView mv = new ModelAndView(); // ModelAndView 객체 생성
//
//        // 게시글 제목이 null이거나 빈 문자열인 경우
//        if (blogDTO.getBlogTitle() == null || blogDTO.getBlogTitle().isEmpty() ||
//                // 게시글 내용이 null이거나 빈 문자열인 경우
//                blogDTO.getBlogContent() == null || blogDTO.getBlogContent().isEmpty()) {
//
//            // 오류 메시지를 담아서 새 글 작성 페이지로 리다이렉트
//            mv.setViewName("redirect:/hwayeon/newpost");
//            mv.addObject("error", "제목과 내용은 필수 입력 항목입니다!");
//            return mv; // 메서드 종료
//        }
//
//        // 게시글 저장 서비스 메소드 호출
//        hwayeonService.savePost(blogDTO);
//
//        // 게시글 저장 후 세부 페이지로 이동할 ModelAndView 설정
//        mv.setViewName("/hwayeon/postpage");
//        mv.addObject("blogNo", blogDTO.getBlogNo()); // 게시글 번호 전달
//        mv.addObject("blogTitle", blogDTO.getBlogTitle()); // 게시글 제목 전달
//        mv.addObject("blogContent", blogDTO.getBlogContent()); // 게시글 내용 전달
//
//        return mv; // ModelAndView 객체 반환
//    }

    // 상세 페이지 조회
    @GetMapping("/postpage")
    public ModelAndView showPostPage(@RequestParam("blogNo") Integer blogNo) {
        ModelAndView mv = new ModelAndView("/hwayeon/postpage");
        HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo);
        mv.addObject("blog", blogDTO);
        return mv;
    }
//    @GetMapping("/postpage")
//    public ModelAndView showPostPage(@RequestParam(name = "blogNo", required = true) Integer blogNo) {
//        ModelAndView mv = new ModelAndView("/hwayeon/postpage");
//
//        HwayeonBlogDTO blogDTO = hwayeonService.findByBlogNo(blogNo);
//
//        mv.addObject("blogTitle", blogDTO.getBlogTitle());
//        mv.addObject("blogContent", blogDTO.getBlogContent());
//        mv.addObject("blogNo", blogDTO.getBlogNo());  // 이 부분이 중요합니다
//
//        System.out.println("Sending to postpage - BlogNo: " + blogDTO.getBlogNo()); // 로그 추가
//
//        return mv;
//    }

    // 삭제 처리하기 (DELETE 메소드)
    @DeleteMapping("/delete/{blogNo}")
    public String deletePost(@PathVariable("blogNo") Integer blogNo) {

        boolean isDeleted = hwayeonService.deletePost(blogNo);  // 게시글 삭제 메소드 호출


        if (isDeleted) {
            return "redirect:/hwayeon/list";  // 삭제 성공 시 리다이렉트
        } else {
            // 삭제 실패 처리
            ModelAndView errorMv = new ModelAndView("/hwayeon/list");
            errorMv.addObject("error", "게시글 삭제에 실패했습니다.");
            return "redirect:/hwayeon/list";  // 삭제 실패 시 리다이렉트
        }
    }

    // 상세 페이지 조회
    @GetMapping("/detail/{blogNo}")
    public ModelAndView showDetailPage(@PathVariable Integer blogNo) {
        ModelAndView mv = new ModelAndView();

        // 해당 blogNo에 해당하는 게시물 정보 가져오기
        HwayeonBlogDTO blogDetail = hwayeonService.findBlogByNo(blogNo);

        // 모델에 추가
        mv.addObject("blogDetail", blogDetail);

        // 뷰 이름 설정
        mv.setViewName("/hwayeon/detailpage"); // Thymeleaf 템플릿의 경로

        return mv;
    }

    //   댓글 등록
    @PostMapping("/addComment")
    public String addComment(@RequestParam("blogNo") Integer blogNo,
                             @RequestParam("author") String author,
                             @RequestParam("content") String content) {
        CommentDTO newComment = new CommentDTO();
        newComment.setBlogNo(blogNo);
        newComment.setAuthor(author);
        newComment.setContent(content);
        newComment.setCreationDate(new Date());

        hwayeonService.addComment(newComment);
        return "redirect:/modifypage?blogNo=" + blogNo;
    }

}
