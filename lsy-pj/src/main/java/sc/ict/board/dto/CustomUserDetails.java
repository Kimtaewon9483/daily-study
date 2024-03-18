package sc.ict.board.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sc.ict.board.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티에서 사용자 정보를 담기 위한 클래스
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {

        this.userEntity = userEntity;
    }


    // 사용자가 가진 권한을 반환하는 메소드, 사용자의 역할을 기반으로 권한을 생성
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    // 사용자의 비밀번호를 반환
    @Override
    public String getPassword() {

        return userEntity.getPassword();
    }

    // 사용자의 이름을 반환
    @Override
    public String getUsername() {

        return userEntity.getUsername();
    }

    // 계정이 만료되었는지 여부를 반환(수정필요)
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    // 계정이 잠겨 있는지 여부를 반환(수정필요)
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    // 비밀번호가 만료되었는지 여부를 반환(수정필요)
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    // 사용 가능 상태인지 여부를 반환(수정필요)
    @Override
    public boolean isEnabled() {

        return true;
    }
}