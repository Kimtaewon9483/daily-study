package sc.ict.board.board.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sc.ict.board.board.dto.BoardDTO;
import sc.ict.board.board.entity.BoardEntity;
import sc.ict.board.board.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 게시글 로직을 담당하는 클래스
@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 10;
    private static final int PAGE_POST_COUNT = 9;

    // DTO를 Entity로 변환
    private BoardDTO convertEntityToDto(BoardEntity board) {
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .username(board.getUsername())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();
    }

    // Entity를 DTO로 변환
    private BoardEntity dtoToEntity(BoardDTO boardDto) {
        BoardEntity boardEntity = BoardEntity.builder()
                .id(boardDto.getId())
                .title(boardDto.getTitle())
                .content(boardDto.getContent() != null ? boardDto.getContent() : "")
                .username(boardDto.getUsername() != null ? boardDto.getUsername() : "")
                .build();
        return boardEntity;
    }

    // 지정된 페이지의 게시글 목록 가져오기
    @Transactional
    public List<BoardDTO> getBoardlist(Integer pageNum) {
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(
                pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));

        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDTO> boardDtoList = new ArrayList<>();

        for (BoardEntity board : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    // 게시글 상세페이지 조회
    @Transactional
    public BoardDTO getPost(Long id) {
        Optional<BoardEntity> boardWrapper = boardRepository.findById(id);
        BoardEntity board = boardWrapper.get();

        BoardDTO boardDTO = BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .username(board.getUsername())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();

        return boardDTO;
    }

    // 게시글 저장 or 업데이트
    @Transactional
    public Long savePost(BoardDTO boardDto) {
        BoardEntity boardEntity = dtoToEntity(boardDto);

        if (boardDto.getId() != null) {
            BoardEntity existingBoard = boardRepository.findById(boardDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + boardDto.getId()));

            boardEntity.setCreatedDate(existingBoard.getCreatedDate());
        }

        return boardRepository.save(boardEntity).getId();
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시글 검색
    @Transactional
    public List<BoardDTO> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDTO> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtoList;

        for (BoardEntity board : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    // 전체 게시글 수 가져오기
    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    // 게시글 퍼지네이션으로 조회
    public Integer[] getPageList(Integer curPageNum) {
        long totalCount = this.getBoardCount(); // 총 게시글 수를 가져옴
        int totalPageCount = (int) Math.ceil((double) totalCount / PAGE_POST_COUNT); // 총 페이지 수 계산

        int startPage = Math.max(1, curPageNum - BLOCK_PAGE_NUM_COUNT / 2); // 시작 페이지 번호 계산
        int endPage = Math.min(totalPageCount, startPage + BLOCK_PAGE_NUM_COUNT - 1); // 종료 페이지 번호 계산

        // 실제 보여줄 페이지 수 계산
        int displaySize = Math.min(BLOCK_PAGE_NUM_COUNT, totalPageCount);

        // 페이지 리스트 초기화
        List<Integer> pageList = new ArrayList<>(displaySize);

        // 페이지 리스트 채우기
        for (int i = 0; i < displaySize; i++) {
            if(startPage + i <= totalPageCount) {
                pageList.add(startPage + i);
            }
        }

        // Integer[] 배열로 변환하여 반환
        return pageList.toArray(new Integer[0]);
    }


    // username으로 CRUD 작업을 수행하는 메소드 추가
    @Transactional
    public List<BoardDTO> getBoardlistByUsername(String username) {
        List<BoardEntity> boardEntities = boardRepository.findByUsername(username);
        List<BoardDTO> boardDtoList = new ArrayList<>();

        for (BoardEntity board : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    // 해당 유저네임의 첫번째 게시글 조회
    @Transactional
    public BoardDTO getPostByUsername(String username) {
        List<BoardEntity> boardEntities = boardRepository.findByUsername(username);

        if (boardEntities.isEmpty()) {
            throw new IllegalArgumentException(username + "님의 게시물이 없습니다.");
        }

        BoardEntity board = boardEntities.get(0);

        return this.convertEntityToDto(board);
    }

    // 유저네임으로 게시판 삭제
    @Transactional
    public void deletePostByUsername(String username) {
        boardRepository.deleteByUsername(username);
    }
}