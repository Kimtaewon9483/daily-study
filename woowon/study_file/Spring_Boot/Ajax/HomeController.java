package com.example.springbootstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {
    // 기본페이지 요청 메서드
    @GetMapping("/")
    public String index() {
        return "index"; // => templates 폴더의 index.html을 찾아간다
    }
    
    // ajax study index
    @GetMapping("/ajax")
    public String ajaxIndex() {
        return "ajax/ajax_index";
    }
    
}
