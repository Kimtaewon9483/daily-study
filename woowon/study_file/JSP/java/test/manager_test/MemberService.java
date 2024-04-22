package test.manager_test;

import java.util.List;

public class MemberService {
	private MemberDao memberDao;
	
	public MemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	//등록
	public boolean regist(MemberVo vo) {
		//동일한 회원이 없을때
		if(memberDao.selectMember(vo.getNum()) == null) {
			memberDao.insertMember(vo);
			return true;
		}
		return false;
	}
	
	//조회
	public MemberVo read(int num) {
		return memberDao.selectMember(num);
	}
	
	//전체조회
	public List<MemberVo> listAll(){
		return memberDao.selectMemverAll();
	}
	
	//수정
	public void edit(MemberVo vo) {
		MemberVo searchMember = memberDao.selectMember(vo.getNum());
		//동일할때만 변경
		if(searchMember.getMemberPw().equals(vo.getMemberPw())) {
			memberDao.updateMamber(vo);
		}
	}
	
	//탈퇴
	public void remove(int num) {
		memberDao.deleteMember(num);
	}
}

