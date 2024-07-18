package com.ohgiraffers.blog.hwayeon.controller;

import com.ohgiraffers.blog.hwayeon.service.HwayeonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @RestController는 클래스 전체가 RESTful API Controller임을 나타냄
@RequestMapping("/hwayeon") // 이 클래스에서 처리하는 모든 요청의 기본 경로(prefix)를 지정
public class LikeController {

    private final HwayeonService hwayeonService;

    public LikeController(HwayeonService hwayeonService) {
        this.hwayeonService = hwayeonService;
    }

    @PostMapping("/hwayeon/listpage/like/{blogNo}")
    public ResponseEntity<Void> likePost(@PathVariable Integer blogNo) {
        hwayeonService.likePost(blogNo);
        return ResponseEntity.ok().build();
    }
    
//    @GetMapping("/test")
//    public ResponseEntity test(Map<String, String> test){
//
//        if(test == null){
//            return ResponseEntity.status(404).body("데이터가 없습니다.");
//        }
//        boolean result = false;
//        if(result){
//            return ResponseEntity.status(500).body("서버 오류가 발생하였습니다.");
//        }
//
//        ResponseEntity.ok("성공")
//        return ResponseEntity.status(200).body("성공");
//    }

}
