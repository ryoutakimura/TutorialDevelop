package com.techacademy.controller;

import org.springframework.web.bind.annotation.PostMapping;

public class LogoutController {

    @PostMapping("/logout")
    public String postLogout() {
        return "redirect:/login";
    }

}
