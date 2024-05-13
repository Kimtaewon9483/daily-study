package com.example.springbootstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.springbootstudy.dto.MemberDTO;
import com.example.springbootstudy.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class MemberController {
    // 셍성자 주입
    // @RequiredArgsConstructor로 memberService를 매개변수로 하는 컨트롤러 생성자를 만들어준다
    // MemberController 빈으로 등록시 memberService에 대한 객체를 사용할 수 있다
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {return "save";}

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        // @RequestParam으로 name의 값을 가져올 수 있다
        System.out.println("MemberController.save");

        // memberDTO와 name의 값이 같다면 자동으로 생성해줌
        System.out.println("memberDTO = " + memberDTO);
        // 값 저장
        memberService.save(memberDTO);
        return "login";
    }
    
    // 로그인
    @GetMapping("/member/login")
    public String getMethodName() {
        return "login";
    }
    
    @PostMapping("/member/login")
    public String postMethodName(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            // 로그인 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }else{
            // 로그인 실패
            return "login";
        }
    }
    
    // 회원목록
    @GetMapping("/member/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // html로 가져갈 데이터가 있다면 Model을 사용
        model.addAttribute("memberList",memberDTOList);
        return "list";
    }
    
    // 유저정보 조회
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
        // 경로상의 값을 @PathVariable로 값을 담아온다
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }
    
    // 회원정보 수정
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        // 로그인시 session에 값을 담아두기 떄문에 session의 값을 가져온다
        String myEmail = (String)session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/"+memberDTO.getId();
    }
    
    // 유저 삭제
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }
    
    // 로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 모두 종료
        return "index";
    }

    // 이메일 중복 체크
    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        if(checkResult!=null){
            return "ok";
        }else{
            return "no";
        }
    }
}
