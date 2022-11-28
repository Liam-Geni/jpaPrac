package com.example.asdfasdfasdf.controller;

import com.example.asdfasdfasdf.domain.MemberRoleEnum;
import com.example.asdfasdfasdf.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        model.addAttribute("username", userDetailsImpl.getUsername());

        if (userDetailsImpl.getMember().getRole() == MemberRoleEnum.ADMIN){
            model.addAttribute("admin_role", true);
        }
        return "index";
    }
}
