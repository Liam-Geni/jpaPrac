package com.example.asdfasdfasdf.service;


import com.example.asdfasdfasdf.domain.Member;
import com.example.asdfasdfasdf.domain.MemberRoleEnum;
import com.example.asdfasdfasdf.dto.KakaoUserInfoDto;
import com.example.asdfasdfasdf.dto.SignupRequestDto;
import com.example.asdfasdfasdf.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    public void registerMember(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<Member> findMember = memberRepository.findByUsername(username);
        if(findMember.isPresent()){
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }

        String email = signupRequestDto.getEmail();
        //사용자 role 확인
        MemberRoleEnum role = MemberRoleEnum.USER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw  new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = MemberRoleEnum.ADMIN;
        }

        Member member = new Member(username, password, email, role);
        memberRepository.save(member);
    }
}
