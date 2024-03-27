준비물 
    1. 익스텐션 -> Java Extension Pack , Spring Boot Extension Pack, Lombok Annotations Support for VS Code 설치, 자바 17버전 설치
    2. mysql 설치
    3. 포스트맨 설치 
        * 유저 테이블
        - 로그인 : http://localhost:8080/login
        - 회원가입 : http://localhost:8080/join
        - admin : http://localhost:8080/admin
        - 로그아웃 : http://localhost:8080/logout
        - 홈 화면 : http://localhost:8080/

        * 게시판 테이블
        - 게시판 작성 : http://localhost:8080/api/board/post
        - 게시판 목록 : http://localhost:8080/api/board/list
        - 게시판 삭제 : http://localhost:8080/api/board/post/{no}
        - 게시판 조회 : http://localhost:8080/api/board/search?keyword={keuword}
        - 게시판 수정 : http://localhost:8080/api/board/post/edit/{no}
        - 게시판 상세 : http://localhost:8080/api/board/post/{no}

```
Section 1
    - 전체적인 흐름
        화면(프론트 영역) -> 컨트롤러(백엔드 영역) -> 서비스(백엔드 영역) -> 데이터베이스(백엔드 영역) -> 역순

Section 2
    - 각 package의 역활
        1.config
            애플리케이션의 설정을 한 곳에 모아서 관리하고 설정 관련 코드와 비즈니스 로직을 명확하게 분리하기 위해 사용

        2.controller
            리모콘 역활을 수행

        3.dto
             사용자로부터 입력받은 데이터를 DTO에 담아 서버로 전송하는 역활을 수행(데이터를 안전하고 효율적으로 전달하기 위함)
             클라이언트와 서버 간 데이터 전송을 위해 설계된 객체

        4.entity
            데이터베이스에 저장되는 데이터 객체로, 데이터베이스와 직접적으로 연결
            
        *DTO와 Entity를 분리하여 얻는 장점(MVC 패턴)
            DTO는 View와 Controller 간의 인터페이스 역할을 하며, Entity는 Model의 역할을 한다.
            이러한 분리를 통해 MCV 패턴을 적용하여, 코드의 가독성과 유지보수를 용이하게 할 수 있다.

        5.jwt
            사용자가 서버에 요청을 보낼 때마다 이 JWT를 함께 보내서, 자신이 누구인지와 서버에서 인정한 사용자임을 증명하는 역활을 수행

        6.repository
            데이터베이스에 저장하고 조회하는 기능을 수행(저장소)

        7.service
             Repository에서 얻어온 정보를 바탕으로 자바 문법을 이용하여 가공 후 다시 Controller에 정보를 잔딜(리모콘버튼을 눌렀을때의 기능을 정의)
```