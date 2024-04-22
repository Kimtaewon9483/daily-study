package test.manager_test;

import java.util.Date;
import java.util.List;

public class Main2 {
	public static void main(String[] args) {
		MemberDao memberDao = new MemberDao();
		
		//C(INSERT)
		MemberVo vo1 = new MemberVo(1,"test1","1234","nick1");
		vo1.setRegDate(new Date());
		MemberVo vo2 = new MemberVo(2,"test2","1234","nick2");
		vo2.setRegDate(new Date());
		MemberVo vo3 = new MemberVo(3,"test3","1234","nick3");
		vo3.setRegDate(new Date());
		
		memberDao.insertMember(vo1);
		memberDao.insertMember(vo2);
		memberDao.insertMember(vo3);
		System.out.println("저장 완료");
		
		//R(SELECT)
		List<MemberVo> ls = memberDao.selectMemverAll();
		for(MemberVo tmp:ls) {
			System.out.println(tmp);
		}
		System.out.println("전체 조회 완료");
		
		MemberVo vo = null;
		vo = memberDao.selectMember(1);
		System.out.println(vo);
		vo = memberDao.selectMember(4);
		System.out.println(vo);
		System.out.println("조회 완료");
		
		//U(UPDATE)
		vo = memberDao.selectMember(1);
		System.out.println(vo);
		if(vo != null) {
			vo.setMemberPw("4321");
			vo.setNickName("1nick");
			memberDao.updateMamber(vo);
		}
		vo = memberDao.selectMember(1);
		System.out.println(vo);
		System.out.println("수정 완료");
		
		//D(DELETE)
		memberDao.deleteMember(2);
		ls = memberDao.selectMemverAll();
		for(MemberVo tmp:ls) {
			System.out.println(tmp);
		}
		System.out.println("삭제 완료");
		
		memberDao.deleteMemberAll();
		ls = memberDao.selectMemverAll();
		for(MemberVo tmp:ls) {
			System.out.println(tmp);
		}
		System.out.println("전체 삭제 완료");	
	}
}
