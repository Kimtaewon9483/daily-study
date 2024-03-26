package sc.ict.board.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sc.ict.board.user.dto.CustomUserDetails;
import sc.ict.board.user.entity.UserEntity;
import sc.ict.board.user.repository.UserRepository;

//사용자 정보를 로드
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 주어진 username으로 UserEntity 객체를 데이터베이스에서 조회
        UserEntity userData = userRepository.findByUsername(username);

        // 조회된 사용자 데이터가 존재하는 경우
        if (userData != null) {
            // CustomUserDetails 객체를 생성하여 반환
            return new CustomUserDetails(userData);
        }

        // 사용자 데이터가 존재하지 않는 경우 null 반환
        return null;
    }
}