package com.example.springbootstudy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.springbootstudy.dto.AjaxDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;



@Controller
public class AjaxController {
    @GetMapping("/ex01")
    public String Ex01() {
        System.out.println("AjaxController.ex01");
        return "ajax/ajax_index"; // html에 작성된 내용이 출력
    }

    @PostMapping("/ex02")
    public @ResponseBody String Ex02() {
        System.out.println("AjaxController.ex02");
        return "안녕하세요"; // 리턴값이 그대로 출력
    }

    @GetMapping("/ex03")
    public @ResponseBody String Ex03(@RequestParam("param1") String param1,
                                        @RequestParam("param2") String param2) {
         System.out.println("param1 => "+ param1 + ", param2 => "+ param2);
        return "ex03메서드 호출 완료!";
    }

    @PostMapping("/ex04")
    public @ResponseBody String Ex04(@RequestParam("param1") String param1,
                                        @RequestParam("param2") String param2) {
         System.out.println("param1 => "+ param1 + ", param2 => "+ param2);
        return "ex04메서드 호출 완료!";
    }

    @GetMapping("/ex05")
    public @ResponseBody AjaxDTO Ex05(@ModelAttribute AjaxDTO ajaxDTO){
        System.out.println("ajaxDTO >> " + ajaxDTO);
        return ajaxDTO;
    }
    
    @PostMapping("/ex06")
    public @ResponseBody AjaxDTO Ex06(@ModelAttribute AjaxDTO ajaxDTO) {
        System.out.println("ajaxDTO >> " + ajaxDTO);
        return ajaxDTO;
    }
    
    @PostMapping("/ex07")
    public @ResponseBody AjaxDTO ex07(@RequestBody AjaxDTO ajaxDTO){
        System.out.println("ajaxDTO >> " + ajaxDTO);
        return ajaxDTO;
    }
    
    // List 생성 메소드
    private List<AjaxDTO> DTOList(){
        List<AjaxDTO> dtoList = new ArrayList<>();
        dtoList.add(new AjaxDTO("data1","data11"));
        dtoList.add(new AjaxDTO("data2","data22"));
        return dtoList;
    }

    @PostMapping("/ex08")
    public @ResponseBody List<AjaxDTO> ex08(@RequestBody AjaxDTO ajaxDTO){
        System.out.println("ajaxDTO >> " + ajaxDTO);
        List<AjaxDTO> dtoList = DTOList();
        dtoList.add(ajaxDTO);
        return dtoList;
    }

    @PostMapping("/ex09")
    // body정보 뿐만 아니라 header,status등도 리턴
    public ResponseEntity ex09(@RequestBody AjaxDTO ajaxDTO){
        System.out.println("ajaxDTO >> " + ajaxDTO);
        
        // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ajaxDTO, HttpStatus.OK);
    }
    
    @PostMapping("/ex10")
    public ResponseEntity ex10(@RequestBody AjaxDTO ajaxDTO){
        System.out.println("ajaxDTO >> " + ajaxDTO);
        List<AjaxDTO> dtoList = DTOList();
        dtoList.add(ajaxDTO);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
