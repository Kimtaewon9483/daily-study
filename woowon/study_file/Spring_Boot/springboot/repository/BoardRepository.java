package com.example.springbootstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.springbootstudy.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity,Long>{
    // 조회수 증가
    // update board_table set board_hots=board_hits+1 where id=?
    // Entity를 기준으로 BoardEntity를 b로 설정 boardHits값을 1증가 시킴
    // :id는 @Param의 id와 매칭
    @Modifying // update,delete등을 실행해야하며 추가
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}
