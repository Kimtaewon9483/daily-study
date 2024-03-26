package sc.ict.board.user.dto;

import lombok.Getter;
import lombok.Setter;

// 사용자 가입 정보를 담기 위한 DTO 클래스를 정의
@Getter
@Setter
public class JoinDTO {

    private String username;

    private String password;
}
