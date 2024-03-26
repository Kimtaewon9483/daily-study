package sc.ict.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sc.ict.board.board.entity.BoardEntity;
import sc.ict.board.user.entity.UserEntity;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    //키워드를 입력받아 title을 조회
    List<BoardEntity> findByTitleContaining(String keyword);

    //UserEntity를 파라미터로 받아 해당 사용자와 연관된 BoardEntity를 조회
    BoardEntity findByUsername(UserEntity username);

    List<BoardEntity> findByUsernameContaining(String keyword);
    List<BoardEntity> findByUsername(String username);
    void deleteByUsername(String username);
}