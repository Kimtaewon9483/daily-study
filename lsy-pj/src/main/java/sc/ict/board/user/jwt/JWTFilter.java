package sc.ict.board.user.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sc.ict.board.user.dto.CustomUserDetails;
import sc.ict.board.user.entity.UserEntity;
import sc.ict.board.user.repository.RefreshRepository;

import java.io.IOException;
import java.io.PrintWriter;

// JWT 인증을 처리하는 클래스
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public JWTFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    // 실제 필터링 로직을 수행하는 메서드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더에서 Authorization 헤더 값 가져오기
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header from Request: " + authorizationHeader);

        // Authorization 헤더가 있고 Bearer 형식인 경우
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 형식에서 액세스 토큰 추출
            String accessToken = authorizationHeader.substring(7);
            System.out.println("Access Token from Request: " + accessToken);

            // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
            try {
                jwtUtil.isExpired(accessToken);
                System.out.println("Access Token is not expired. Proceeding with authentication.");
            } catch (ExpiredJwtException e) {
                System.out.println("Access Token has expired.");

                // 엑세스 토큰이 만료된 경우, 쿠키에서 리프레시 토큰을 가져옴
                String refreshToken = getRefreshTokenFromCookie(request);
                System.out.println("Refresh Token from Cookie: " + refreshToken);

                // 리프레시 토큰이 유효한지 확인
                if (refreshToken != null && jwtUtil.isValidRefreshToken(refreshToken)) {
                    System.out.println("Refresh Token is valid. Generating new Access Token.");

                    // 리프레시 토큰이 유효한 경우, 새로운 엑세스 토큰을 발급
                    String newAccessToken = jwtUtil.createAccessTokenFromRefreshToken(refreshToken);
                    response.setHeader("Authorization", "Bearer " + newAccessToken);
                    System.out.println("New Access Token: " + newAccessToken);
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    System.out.println("Refresh Token is invalid or not provided.");
                }

                // 리프레시 토큰이 유효하지 않은 경우, 응답에 오류 메시지를 담고 401 상태 코드를 설정
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\": \"다시 로그인해주세요\"}");
                return;
            }

            // 토큰이 access인지 확인
            String category = jwtUtil.getCategory(accessToken);

            // 토큰의 카테고리가 access가 아닌 경우, 오류 응답 반환
            if (!category.equals("access")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\": \"유효하지 않은 엑세스 토큰입니다.\"}");
                return;
            }

            // 토큰에서 username과 role 값을 가져옴
            String username = jwtUtil.getUsername(accessToken);
            System.out.println("AccessToken username = " + username);
            String role = jwtUtil.getRole(accessToken);
            System.out.println("AccessToken role = " + role);

            // UserEntity 객체 생성 및 사용자 정보 설정
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setRole(role);
            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
            System.out.println("JWTFilter : " + customUserDetails.getUsername());
            System.out.println("JWTFilter : " + customUserDetails.getRole());

            // Authentication 객체 생성 및 SecurityContext에 설정
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // 다음 필터로 요청을 전달
            filterChain.doFilter(request, response);
        } else {
            System.out.println("Access Token is null or not in Bearer format. Passing to the next filter.");
            filterChain.doFilter(request, response);
        }
    }

    // 쿠키에서 리프레시 토큰을 가져오는 메서드
    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}