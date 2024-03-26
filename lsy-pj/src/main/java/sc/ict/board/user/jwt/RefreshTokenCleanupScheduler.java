package sc.ict.board.user.jwt;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sc.ict.board.user.repository.RefreshRepository;

import java.time.LocalDateTime;

// 데이터베이스에 저장된 리프레시 토큰을 삭제하는 클래스
@Component
public class RefreshTokenCleanupScheduler {

    private final RefreshRepository refreshRepository;

    public RefreshTokenCleanupScheduler(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void cleanupExpiredRefreshTokens() {
        // 현재 시간 기준으로 만료된 리프레시 토큰 삭제
        refreshRepository.deleteByExpirationLessThan(LocalDateTime.now());
    }
}