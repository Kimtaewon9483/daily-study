package sc.ict.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 로그인을 한 회원만 진입 가능한 페이지
@Controller
@ResponseBody
public class AdminController {

    @GetMapping("/admin")
    public String adminP() {
        return "Admin Controller";
    }
}
