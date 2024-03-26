package sc.ict.board.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sc.ict.board.user.entity.UserEntity;

// 사용자 데이터에 대한 데이터베이스 접근을 추상화
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // 사용자 이름으로 데이터베이스에 해당 사용자가 존재하는지 여부를 확인
    Boolean existsByUsername(String username);

    // 사용자 이름으로 사용자를 검색
    UserEntity findByUsername(String username);
}
