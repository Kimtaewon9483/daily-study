package sc.ict.board.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sc.ict.board.entity.RefreshEntity;
import sc.ict.board.jwt.JWTUtil;
import sc.ict.board.repository.RefreshRepository;

import java.util.Date;


//사용자의 로그인 상태를 유지하기 위해 액세스 토큰과 리프레시 토큰을 다시 발급하는 역할
@Controller
@ResponseBody
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public ReissueController(JWTUtil jwtUtil, RefreshRepository refreshRepository) {

        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 리프레시 토큰을 가져온다.
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        //리프레시 토큰이 없는 경우 400 Bad Request 응답을 반환합니다.
        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰의 만료 여부를 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            // 리프레시 토큰이 만료된 경우 400 Bad Request 응답을 반환
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰의 카테고리가 refresh인지 확인
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            // 유효하지 않은 리프레시 토큰인 경우 400 Bad Request 응답을 반환합니다.
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰이 데이터베이스에 존재하는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {

            // 유효하지 않은 리프레시 토큰인 경우 400 Bad Request 응답을 반환
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰에서 사용자 이름과 역할을 가져온다.
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // 새로운 액세스 토큰(생명주기 : 20분)과 리프레시 토큰(생명주기 : 24시간)을 생성한다.
        String newAccess = jwtUtil.createJwt("access", username, role, 1200000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        // 헤더에 새로운 액세스 토큰을 설정하고 쿠키에 새로운 리프레시 토큰을 설정한다.
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        // 200 OK 응답을 반환합니다.
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 리프레시 토큰을 쿠키로 생성하는 메서드
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    // 리프레시 토큰을 데이터베이스에 저장하는 메서드
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}