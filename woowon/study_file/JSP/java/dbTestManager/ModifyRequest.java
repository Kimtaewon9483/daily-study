package dbTestManager;

public class ModifyRequest {
	private int num;
	private String memberId;
	private String memberPwOld;
	private String memberPwNew;
	private String nickName;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPwOld() {
		return memberPwOld;
	}
	public void setMemberPwOld(String memberPwOld) {
		this.memberPwOld = memberPwOld;
	}
	public String getMemberPwNew() {
		return memberPwNew;
	}
	public void setMemberPwNew(String memberPwNew) {
		this.memberPwNew = memberPwNew;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
