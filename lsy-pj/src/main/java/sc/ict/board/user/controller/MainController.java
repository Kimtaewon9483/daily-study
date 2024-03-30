package sc.ict.board.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sc.ict.board.user.jwt.JWTUtil;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

    private final JWTUtil jwtUtil;

    public MainController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/")
    public String mainP(Model model, HttpServletRequest request) {

        System.out.println("Welcome");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        // SecurityContextHolder를 통해 현재 인증된 사용자의 username을 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        // SecurityContextHolder를 통해 현재 인증 정보를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 정보에서 사용자의 권한 목록을 가져온다.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 권한 목록의 Iterator를 생성합니다.
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        //첫 번째 권한을 가져온다.
        GrantedAuthority auth = iter.next();
        // 권한의 문자열을 가져옵니다 ( ex."ROLE_ADMIN" )
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("token: " + token);
            String role = jwtUtil.getRole(token);
            System.out.println("role : " + role);
            model.addAttribute("role", role);
        }

        return "List/home";
    }
}
