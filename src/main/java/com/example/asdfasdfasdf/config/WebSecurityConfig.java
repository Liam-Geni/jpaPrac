package com.example.asdfasdfasdf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig{


    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //회원관리 처리 api( post/user/**)에 대해 CSRF 무시
        http.csrf().disable();
        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/images/**").permitAll()
                        .antMatchers("/css/**").permitAll()
                        //회원 관리 처리 API 전부를 login 없이 허용
                        .antMatchers("/user/**").permitAll()
                        //어떤 요청이든 '인증'
                        .anyRequest().authenticated()
                )
                //로그인 기능 허용
                .formLogin()
                .loginPage("/user/login")// 로그인 기능 작동
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/") //로그인 성공 시 이동 페이지 경로
                .failureUrl("/user/login?error") //로그인 실패 시 이동 페이지 경로
                .permitAll()
                .and()
                //로그아웃 기능 허용
                .logout()
                .logoutUrl("/user/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/forbidden.html");

        return http.build();
    }
}
