package sc.ict.board.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 리프레시 토큰과 관련된 정보를 데이터베이스에 저장하기 위한 구조를 정의
@Entity
@Getter
@Setter
public class RefreshEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String refresh;

    @Column(nullable = false)
    private LocalDateTime expiration;
}
