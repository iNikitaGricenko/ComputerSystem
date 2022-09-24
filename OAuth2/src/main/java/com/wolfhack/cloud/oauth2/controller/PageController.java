package com.wolfhack.cloud.oauth2.controller;

import com.wolfhack.cloud.oauth2.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/login")
    public String oauth2LoginPage(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String oauth2LoginPageError(Model model) {
        model.addAttribute("message", "Bad Credentials");
        return "login";
    }

    @GetMapping("/register")
    public String oauth2RegisterPage() {
        return "register";
    }

}
