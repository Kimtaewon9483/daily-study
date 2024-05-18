package com.example.springbootstudy.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.springbootstudy.dto.BoardDTO;
import com.example.springbootstudy.dto.CommentDTO;
import com.example.springbootstudy.service.BoardService;
import com.example.springbootstudy.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board") // 주소에 /board로 시작이 많다면 밑에 Mapping들은 /board를 기본으로 잡고 시작
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    // 게시글 작성 폼
    @GetMapping("/save") // /board/save와 같음
    public String saveForm(){
        return "boardSave";
    }

    // 게시글 저장
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println("boardDTO >> " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }
    
    // 게시글 목록
    @GetMapping("/")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "boardList";
    }
    
    // 게시글 조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id,Model model,
                            @PageableDefault(page=1) Pageable pageable){
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        System.out.println("pageable값 확인 >>>>> " + pageable);

        // 댓글목록
        List<CommentDTO> commentDTList = commentService.finadAll(id);
        model.addAttribute("commentList", commentDTList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "boardDetail";
    }
    
    // 게시글 수정
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id,Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "boardUpdate";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "boardDetail";
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }
    
    // 페이징
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1)Pageable pageable, Model model){
        // @PageableDefault(page = 1) 기본값은 1
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3; // 하단에 보여지는 페이지 번호 갯수
        
        System.out.println("getPageNumber()>>>>>>" + pageable.getPageNumber());
        // 시작페이지 1,4,7,10~
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit)))-1)*blockLimit+1;
        // 끝페이지 3,6,9,12~
        int endPage = ((startPage+blockLimit-1)<boardList.getTotalPages()?startPage+blockLimit-1:boardList.getTotalPages());
        System.out.println("start>>>>"+startPage+", end>>>>>"+endPage);
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }
}
