package com.chukapoka.server.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/auth/login")
    public String login() {
        return "loginForm";
    }
}
