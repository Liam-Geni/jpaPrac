package com.example.asdfasdfasdf.controller;

import com.example.asdfasdfasdf.dto.SignupRequestDto;
import com.example.asdfasdfasdf.service.KakaoUserService;
import com.example.asdfasdfasdf.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;
    private final KakaoUserService kakaoUserService;

    //회원 로그인 페이지
    @GetMapping("/user/login")
    public String login(){
        return "login";
    }

    //회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup(){
        return "signup";
    }

    //회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerMember(SignupRequestDto signupRequestDto){
        memberService.registerMember(signupRequestDto);
        return "redirect:/user/login";
    }

    //카카오에서 보내주는 인가코드 처리
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCodde: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }

}
