package com.example.springbootstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springbootstudy.entity.BoardEntity;
import com.example.springbootstudy.entity.CommentEntity;
import java.util.*;

public interface CommentRepository extends JpaRepository<CommentEntity,Long>{
    //select * from comment_table where board_id=? order by desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
