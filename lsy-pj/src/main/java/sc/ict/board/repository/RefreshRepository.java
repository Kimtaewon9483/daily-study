package sc.ict.board.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import sc.ict.board.entity.RefreshEntity;

// RefreshRepository 인터페이스는 리프레시 토큰을 관리하기 위한 데이터베이스 작업을 추상화한다.
public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    // 주어진 리프레시 토큰 값으로 데이터베이스에 해당 토큰이 존재하는지 여부를 확인
    Boolean existsByRefresh(String refresh);

    // 주어진 리프레시 토큰 값으로 데이터베이스에서 해당 토큰을 삭제
    @Transactional
    void deleteByRefresh(String refresh);
}
