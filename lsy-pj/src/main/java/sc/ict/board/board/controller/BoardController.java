package sc.ict.board.board.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sc.ict.board.board.dto.BoardDTO;
import sc.ict.board.board.service.BoardService;
import sc.ict.board.user.dto.CustomUserDetails;

import java.util.List;

// 게시판 컨트롤러
@RestController
@AllArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    // 게시판을 조회하는 메서드
    @GetMapping({"", "/list"})
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        // 지정된 페이지 번호에 해당하는 게시글 목록을 가져온다.
        List<BoardDTO> boardList = boardService.getBoardlist(pageNum);
        // 페이지 번호 목록을 가져온다.
        Integer[] pageList = boardService.getPageList(pageNum);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);
        //return ResponseEntity.ok(boardList);
        return "list";
    }

    // 게시글 작성 페이지 메서드
    @GetMapping("/post")
    public String write() {
        return "api/board/write";
    }

    // 게시글 작성 메서드
    @PostMapping("/post")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BoardDTO>> write(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody BoardDTO boardDto) {
        String username = customUserDetails.getUsername();
        boardDto.setUsername(username);
        boardService.savePost(boardDto);

        // 첫 페이지의 게시글 목록 가져오기
        List<BoardDTO> boardList = boardService.getBoardlist(1);
        return ResponseEntity.ok(boardList);
    }

    // 게시글 상세보기 메서드
    @GetMapping("/post/{no}")
    public ResponseEntity<BoardDTO> detail(@PathVariable("no") Long no) {
        try {
            // 게시글 번호에 해당하는 게시글 정보를 가져온다.
            BoardDTO boardDTO = boardService.getPost(no);
            return ResponseEntity.ok(boardDTO);
        } catch (IllegalArgumentException e) {
            // 게시글이 존재하지 않는 경우 404 Not Found 응답을 반환
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 수정하기 전 조회하는 메서드
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDTO boardDTO = boardService.getPost(no);
        model.addAttribute("boardDto", boardDTO);
        return "update";
    }

    // 게시글 수정 메서드
    @PutMapping("/post/edit/{no}")
    public ResponseEntity<BoardDTO> update(@PathVariable("no") Long no, @RequestBody BoardDTO boardDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (boardDTO.getTitle() == null || boardDTO.getTitle().isEmpty()
                || boardDTO.getContent() == null || boardDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("제목과 내용은 필수 입력 항목입니다.");
        }
        String username = customUserDetails.getUsername();
        boardDTO.setId(no);
        boardDTO.setUsername(username);
        boardService.savePost(boardDTO);

        // 수정된 게시글의 상세 정보 가져오기
        BoardDTO updatedBoard = boardService.getPost(no);
        return ResponseEntity.ok(updatedBoard);
    }

    // 게시글 삭제 메서드
    @DeleteMapping("/post/{no}")
    @PreAuthorize("@customAuthorizationService.canDeletePost(#no, authentication.principal.username)")
    public ResponseEntity<Void> delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);
        return ResponseEntity.noContent().build();
    }

    // 게시글 조회 메서드
    @GetMapping("/search")
    public ResponseEntity<List<BoardDTO>> search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDTO> boardDtoList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardDtoList);
        return ResponseEntity.ok(boardDtoList);
    }

    @GetMapping("/user/{username}/post/{no}")
    public String detailByUsername(@PathVariable("username") String username, @PathVariable("no") Long no, Model model) {
        BoardDTO boardDTO = boardService.getPostByUsername(username);
        model.addAttribute("boardDto", boardDTO);
        return "detail";
    }


}