package sc.ict.board.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sc.ict.board.user.dto.JoinDTO;
import sc.ict.board.user.entity.UserEntity;
import sc.ict.board.user.repository.UserRepository;

// 회원가입 로직을 담당하는 클래스
@Service
public class JoinService {

    private final UserRepository userRepository;

    // BCryptPasswordEncoder 비밀번호를 암호화하는 데 사용한다
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 회원가입 처리를 수행하는 메서드
    public boolean joinProcess(JoinDTO joinDTO) {

        // 사용자가 입력한 username과 password를 joinDTO에서 가져온다
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        // 이미 존재하는 사용자인지 확인
        Boolean isExist = userRepository.existsByUsername(username);

        // 이미 존재하는 사용자라면 false를 반환하여 회원가입을 중단
        if(isExist) {

            return false;
        }

        // 새로운 사용자 정보를 저장하기 위해 UserEntity 객체를 생성
        UserEntity data = new UserEntity();

        // 사용자 정보를 UserEntity 객체에 설정
        data.setUsername(username);
        // 비밀번호는 BCryptPasswordEncoder를 사용하여 암호화한 후 설정
        data.setPassword(bCryptPasswordEncoder.encode(password));
        // 사용자의 역할을 "ROLE_ADMIN"으로 설정
        data.setRole("ROLE_ADMIN");

        // UserEntity 객체를 데이터베이스에 저장
        userRepository.save(data);

        // 회원가입이 성공적으로 완료되었으므로 true를 반환
        return true;
    }
}
