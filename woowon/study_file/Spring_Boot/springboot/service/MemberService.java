package com.example.springbootstudy.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.example.springbootstudy.dto.MemberDTO;
import com.example.springbootstudy.entity.MemberEntity;
import com.example.springbootstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    
    // 회원 가입
    public void save(MemberDTO memberDTO){
        // 1. dto -> entity 변환
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        // 2. repository의 save 메서드 호출
        // jpa가 제공하는 메서드
        memberRepository.save(memberEntity);
        // repository의 save메서드 호출(조건. entity객체를 넘겨줘야 함)
    }

    // 로그인
    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 회원이 입력한 이메일로 DB에서 조회
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            // 조회 결과가 있다면
            MemberEntity memberEntity = byMemberEmail.get();
            
            // password 비교
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // Entity -> dto 변환후 return
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }else{
                return null;
            }
        }else{
            // 조회 결과가 없다면
            return null;
        }
    }
    // 회원목록
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for(MemberEntity memberEntity: memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    // 회원정보 조회
    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){

            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    // 회원정보 수정
    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        // DB에 id가 있는 Entity객체가 넘어오면 update를 해줌
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    // 회원삭제
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    // email 중복체크
    public String emailCheck(String memberEmail){
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if(byMemberEmail.isPresent()){
            // 결과가 있음, 중복
            return null;
        }else{
            // 결과가 없음, 사용가능
            return "ok";
        }
    }
}
