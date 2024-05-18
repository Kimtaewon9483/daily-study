package com.example.springbootstudy.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.example.springbootstudy.dto.CommentDTO;
import com.example.springbootstudy.entity.BoardEntity;
import com.example.springbootstudy.entity.CommentEntity;
import com.example.springbootstudy.repository.BoardRepository;
import com.example.springbootstudy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성
    public Long save(CommentDTO commentDTO){
        // 부모엔티티 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getId(); 
        }else{
            return null;
        }
    }

    public List<CommentDTO> finadAll(Long boardId) {
        // select * from comment_table where board_id=? order by desc;
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        // Entity->DTO
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity:commentEntityList){
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity,boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
