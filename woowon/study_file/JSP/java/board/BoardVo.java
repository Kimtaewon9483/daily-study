package board;

import java.util.Date;

// 게시글 정보를 담을 수 있는 클래스
// "NUM","TITLE","WRITER","CONTENT","REGDATE","CONT"
public class BoardVo {
	private int num; // 번호
	private String title; // 제목
	private String writer; // 작성자
	private String content; // 내용
	private Date regDate; // 작성일
	private int cont; // 조회수
	
	//기본생성자
	public BoardVo() {}
	
	//생성자
	public BoardVo(int num, String title, String writer, String content, Date regDate, int cont) {
		super();
		this.num = num;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.regDate = regDate;
		this.cont = cont;
	}
	
	// getter,setter
	public int getNum() {return num;}
	public void setNum(int num) {this.num = num;}
	
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	
	public String getWriter() {return writer;}
	public void setWriter(String writer) {this.writer = writer;}
	
	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}
	
	public Date getRegDate() {return regDate;}
	public void setRegDate(Date regDate) {this.regDate = regDate;}
	
	public int getCont() {return cont;}
	public void setCont(int cont) {this.cont = cont;}
	
	@Override
	public String toString() {
		return "BoardVo [num=" + num + ", title=" + title + ", writer=" + writer + ", content=" + content + ", regDate="
				+ regDate + ", cont=" + cont + "]";
	}
	
}
