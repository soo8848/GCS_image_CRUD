package com.example.gcsimage.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
