package sc.ict.board.board.dto;



import lombok.*;
import sc.ict.board.board.entity.BoardEntity;

import java.time.LocalDateTime;

// DTO : 데이터 전달 목적
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BoardEntity toEntity(){
        BoardEntity board = BoardEntity.builder()
                .id(id)
                .username(username)
                .title(title)
                .content(content)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        return board;
    }

    @Builder
    public BoardDTO(Long id, String title, String content, String username, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}