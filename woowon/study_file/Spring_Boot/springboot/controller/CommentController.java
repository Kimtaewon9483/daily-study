package com.example.springbootstudy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.springbootstudy.dto.CommentDTO;
import com.example.springbootstudy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("commentDTO>>"+commentDTO);
        Long saveResult = commentService.save(commentDTO);
        if(saveResult != null){
            // 작성 성공시 댓글목록을 가져와 리턴
            List<CommentDTO> commentDTOList = commentService.finadAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
        }
    }
    
}
