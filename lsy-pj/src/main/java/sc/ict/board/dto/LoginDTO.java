package sc.ict.board.dto;

import lombok.Getter;
import lombok.Setter;

// 사용자 로그인 정보를 담기 위한 DTO 클래스를 정의
@Getter
@Setter
public class LoginDTO {

    private String username;

    private String password;
}
