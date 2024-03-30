package sc.ict.board.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.ui.Model;
import sc.ict.board.user.dto.LoginDTO;
import sc.ict.board.user.entity.RefreshEntity;
import sc.ict.board.user.repository.RefreshRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

// 로그인 요청을 처리하는 클래스
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private RefreshRepository refreshRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    // 로그인 인증 시도 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            // request에서 username과 password 값을 직접 추출
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            System.out.println("Login filter : " + authToken);

            // AuthenticationManager를 통해 인증 시도
            return authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    // 인증 성공 시 호출되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        // 인증된 사용자 정보 가져오기
        String username = authentication.getName();
        System.out.println("successfulAuthentication : " + authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        System.out.println("auth : " + auth);

        // 액세스 토큰과 리프레시 토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 1200000L);
        System.out.println("access token = " + access);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);
        System.out.println("refresh token = " + refresh);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("seccessful authentication : " + String.valueOf(SecurityContextHolder.getContext().getAuthentication()));


        // 리프레시 토큰 저장 (생명주기 : 24시간)
        addRefreshEntity(username, refresh, 86400000L);

        // 응답에 액세스 토큰과 리프레시 토큰 설정
        //response.setHeader("Authorization", "Bearer " + access);
        //response.addCookie(createCookie("refresh", refresh));
        //response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String responseBody = "{\"access_token\":\"Bearer " + access + "\", \"refresh_token\":\"" + refresh + "\"}";
        System.out.println("LoginFilter + responseBody : " + responseBody);
        response.getWriter().write(responseBody);
    }

    // 인증 실패 시 호출되는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    // 리프레시 토큰을 쿠키로 생성하는 메서드
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        return cookie;
    }

    // 리프레시 토큰을 데이터베이스에 저장하는 메서드
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiredMs / 1000);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(expirationDateTime);

        refreshRepository.save(refreshEntity);
    }
}