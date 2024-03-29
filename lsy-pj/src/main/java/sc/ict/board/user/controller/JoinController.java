package sc.ict.board.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import sc.ict.board.user.dto.JoinDTO;
import sc.ict.board.user.service.JoinService;

//회원가입을 처리하기위한 리모콘
@Controller
public class JoinController {

    //JoinController가 JoinService의 기능을 사용할 수 있게 정의
    private final JoinService joinService;

    //생성자를 통해 JoinService 객체를 주입(의존성 주입)
    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    // 게시판 페이지
    @GetMapping("/join")
    public String joinForm() {
        return "List/join";
    }

    //엔드포인트에 대한 POST 요청을 처리하는 메서드
    @ResponseBody
    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {

        //JoinService의 joinProcess 메서드를 호출하여 회원가입 처리를 수행하며 결과를 boolean 값으로 반환
        boolean result = joinService.joinProcess(joinDTO);

        if (result) {
            //true
            return ResponseEntity.ok("회원가입 성공");
        } else {
            //false
            return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "List/login";
    }
}
