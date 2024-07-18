package com.ohgiraffers.blog.jinhee.service;

import com.ohgiraffers.blog.jinhee.model.dto.LoginRequestDTO;
import com.ohgiraffers.blog.jinhee.model.dto.SignupRequestDTO;
import com.ohgiraffers.blog.jinhee.model.entity.User;
import com.ohgiraffers.blog.jinhee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void signup(SignupRequestDTO signupRequest) {
        User newUser = new User();
        newUser.setUsername(signupRequest.getUsername());
        newUser.setPassword(signupRequest.getPassword());
        // 추가 필드 값 설정 (이메일, 이름 등)

        userRepository.save(newUser);
    }

    public User login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return user;
        }

        return null;
    }
}
