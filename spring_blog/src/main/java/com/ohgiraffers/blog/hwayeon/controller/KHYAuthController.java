package com.ohgiraffers.blog.hwayeon.controller;

import com.ohgiraffers.blog.jinhee.model.dto.LoginRequestDTO;
import com.ohgiraffers.blog.jinhee.model.dto.SignupRequestDTO;
import com.ohgiraffers.blog.jinhee.model.entity.User;
import com.ohgiraffers.blog.jinhee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hwayeon")
public class KHYAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/menu-signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO signupRequest) {
        userService.signup(signupRequest);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/menu-login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        User user = userService.login(loginRequest);

        if (user != null) {
            // 로그인 성공 시 세션에 사용자 정보 저장 또는 JWT 토큰 생성 등의 작업 수행
            return ResponseEntity.ok("로그인 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        // 로그아웃 처리 (세션 삭제 또는 JWT 토큰 만료 등)
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
