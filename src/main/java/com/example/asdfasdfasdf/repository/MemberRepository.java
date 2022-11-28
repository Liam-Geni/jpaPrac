package com.example.asdfasdfasdf.repository;


import com.example.asdfasdfasdf.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);


    Optional<Member> findByKakaoId(Long kakaoId);
}
