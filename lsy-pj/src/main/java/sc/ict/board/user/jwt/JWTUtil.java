package sc.ict.board.user.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sc.ict.board.user.repository.RefreshRepository;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey secretKey;
    private final RefreshRepository refreshRepository;

    // 생성자에서 비밀 키와 리프레시 토큰 저장소를 초기화
    public JWTUtil(@Value("${spring.jwt.secret}") String secret, RefreshRepository refreshRepository) {
        // 비밀 키를 설정. 설정 파일에서 주입받은 문자열을 바이트 배열로 변환하여 비밀 키 생성
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.refreshRepository = refreshRepository;
    }

    // 주어진 토큰에서 사용자 이름을 추출
    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    // 주어진 토큰에서 사용자 역할을 추출
    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // 주어진 토큰에서 카테고리를 추출
    public String getCategory(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    // 토큰이 만료되었는지 확인
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // 새 JWT를 생성
    public String createJwt(String category, String username, String role, Long expiredMs) {
        // category, username, role을 클레임으로 추가하고, 만료 시간을 설정하여 JWT 생성
        return Jwts.builder()
                .claim("category", category)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(secretKey)
                .compact();
    }

    // 리프레시 토큰의 유효성 검사
    public boolean isValidRefreshToken(String refreshToken) {
        return refreshRepository.existsByRefresh(refreshToken);
    }

    // 리프레시 토큰을 기반으로 새 액세스 토큰 생성
    public String createAccessTokenFromRefreshToken(String refreshToken) {
        String username = getUsername(refreshToken);
        String role = getRole(refreshToken);
        // 새 액세스 토큰 반환
        return createJwt("access", username, role, 60000L);
    }
}