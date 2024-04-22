package test.manager_test;

import java.util.Date;
import java.util.List;

public class Main3 {
	public static void main(String[] args) {
		MemberService memberService = new MemberService(new MemberDao());
		
		//C(INSERT)
		MemberVo vo1 = new MemberVo(1,"test1","1234","nick1");
		vo1.setRegDate(new Date());
		MemberVo vo2 = new MemberVo(2,"test2","1234","nick2");
		vo2.setRegDate(new Date());
		MemberVo vo3 = new MemberVo(3,"test3","1234","nick3");
		vo3.setRegDate(new Date());
		
		memberService.regist(vo1);
		memberService.regist(vo2);
		memberService.regist(vo3);
		System.out.println("저장 완료");
		
		//R(SELECT)
		List<MemberVo> ls = memberService.listAll();
		for(MemberVo tmp:ls) {
			System.out.println(tmp);
		}
		System.out.println("전체 조회 완료");
		
		MemberVo vo = null;
		vo = memberService.read(1);
		System.out.println(vo);
		vo = memberService.read(4);;
		System.out.println(vo);
		System.out.println("조회 완료");
		
		//U(UPDATE)
		vo = memberService.read(1);
		System.out.println(vo);
		if(vo != null) {
			vo.setMemberPw("4321");
			vo.setNickName("1nick");
			memberService.edit(vo);
		}
		vo = memberService.read(1);
		System.out.println(vo);
		System.out.println("수정 완료");
		
		//D(DELETE)
		memberService.remove(2);
		ls = memberService.listAll();
		for(MemberVo tmp:ls) {
			System.out.println(tmp);
		}
		System.out.println("삭제 완료");
	}
}
