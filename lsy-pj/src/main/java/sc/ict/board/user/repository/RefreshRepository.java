package sc.ict.board.user.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sc.ict.board.user.entity.RefreshEntity;

import java.time.LocalDateTime;

// RefreshRepository 인터페이스는 리프레시 토큰을 관리하기 위한 데이터베이스 작업을 추상화한다.
@Repository
public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    // 주어진 리프레시 토큰 값으로 데이터베이스에 해당 토큰이 존재하는지 여부를 확인
    Boolean existsByRefresh(String refresh);

    // 주어진 리프레시 토큰 값으로 데이터베이스에서 해당 토큰을 삭제
    @Transactional
    void deleteByRefresh(String refresh);

    //만료시간이 지난 리프레시 토큰을 데이터베이스에서 삭제
    void deleteByExpirationLessThan(LocalDateTime now);
}
