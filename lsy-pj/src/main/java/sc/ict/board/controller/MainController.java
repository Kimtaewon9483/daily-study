package sc.ict.board.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Iterator;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainP() {
        // SecurityContextHolder를 통해 현재 인증된 사용자의 username을 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // SecurityContextHolder를 통해 현재 인증 정보를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 정보에서 사용자의 권한 목록을 가져온다.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 권한 목록의 Iterator를 생성합니다.
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        //첫 번째 권한을 가져온다.
        GrantedAuthority auth = iter.next();
        // 권한의 문자열을 가져옵니다 ( ex."ROLE_ADMIN" )
        String role = auth.getAuthority();

        return "Main Controller username = " + username + " role =" + role;
    }
}
