package com.example.springbootstudy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.springbootstudy.dto.BoardDTO;
import com.example.springbootstudy.entity.BoardEntity;
import com.example.springbootstudy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시글 작성
    public void save(BoardDTO boardDTO){
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
    
    // 게시글 목록
    public List<BoardDTO> findAll(){
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    // 게시글 조회
        // 조회수 증가
    @Transactional
    public void updateHits(Long id){
        boardRepository.updateHits(id);
    }
    public BoardDTO findById(Long id){
        Optional<BoardEntity>  optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }else{
            return null;
        }
    }

    // 게시글 수정
    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    // 게시글 삭제
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // 페이징
    public Page<BoardDTO> paging(Pageable pageable) {
        // pageable에 몇페이지를 요청했는지, 1을빼는이유는 값이 0부터 시작하기 때문에
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;
        // 한페이지당 3개씩의 글을 보여주고 정렬 기준은 id기준으로 내림차순 정렬
        Page<BoardEntity> boardEntities =
             boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
             // 몇페이지를 보고싶은지, 한페이지에 보여줄 글 갯수, 정렬방식, Entity에 기준(id,pk)
        
        pagingPrint(boardEntities);

        // board<-boardEntities를 하나씩 꺼내서 DTO로 옮기는 작업을 함, pagingPrint메서드에 있는 출력 메서드를 사용할수 있음
        // 목록 : id, writer, title, hits, createTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(),board.getBoardTitle(),board.getBoardHits(),board.getBoardCreatedTime()));
        return boardDTOS;
    }
    // 페이징 확인
    public void pagingPrint(Page<BoardEntity> boardEntities){
        System.out.println("boardEntities.getContent() = " +boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = "+boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = "+boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = "+boardEntities.getTotalPages()); // 전체 페이지 개수
        System.out.println("boardEntities.getSize() = "+boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = "+boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = "+boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = "+boardEntities.isLast()); // 마지막 페이지 여부
    }
}
