package com.example.springbootstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springbootstudy.entity.MemberEntity;
import java.util.Optional;


// MemberEntity 클래스를 사용한다는 것, Long은 pk의 타입
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
    // 이메일로 회원 조회(select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
