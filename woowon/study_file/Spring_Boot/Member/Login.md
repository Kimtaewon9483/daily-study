# Spring boot 로그인

##### application.yml
```
        ddl-auto: update
```
- jpa설정에 create를 update로 변경시킨다
- create로 설정하면 서버를 다시 킬때마다 해당 테이블이 있으면 테이블을 삭제후 다시 만들기 때문이다

##### login.html
```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
</head>
<body>
<form action="/member/login" method="post">
    이메일 : <input type="text" name="memberEmail"> <br>
    비밀번호 : <input type="password" name="memberPassword"><br>
    <input type="submit" value="로그인">
</form>
</body>
</html>
```
- 로그인을 하기위한 간단한 로그인 폼

##### MemberController.java
```
    // 로그인
    @GetMapping("/member/login")
    public String getMethodName() {
        return "login";
    }
    
    @PostMapping("/member/login")
    public String postMethodName(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            // 로그인 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }else{
            // 로그인 실패
            return "login";
        }
    }
```
- @GetMapping은 회원가입후 로그인 폼으로 이동을시키거나 index페이지에서 로그인 버튼을 눌렀을때 login.html로 이동시킨다
- @PostMapping은 로그인 정보를 입력후 로그인 버튼을 눌렀을때 로그인을 하기위한 메서드이다
    - 로그인에 성공을 했다면 loginResult의 값이 null이 아니므로 session에 loginEmail이란 이름으로 로그인할 유저에 이메일 값을 저장시키고 main페이지로 이동시킨다
        - session에 값을 저장시키면 main페이지에서도 session에 값을 사용할수 있다
    - 로그인에 실패했다면 다시 login페이지로 이동시킨다


##### MemberRepository.java
```
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
```
- select * from member_table where member_email=?와 같은 메서드이다

##### MemberDTO.java
```
    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
```
- MemberEntity를 MemberDTO로 변경시키기 위해 MemberDTO에 값을 memberEntity에서 값을 가져와 세팅해준다

##### MemberService.java
```
    // 로그인
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            MemberEntity memberEntity = byMemberEmail.get();
            
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
```
- 입력한 email로 DB에서 해당 값이 있는지를 확인한다
    - isPresent로 Optional의 값이 있는지 없는지 확인하고 있다면 get으로 MemberEntity의 값을 가져온다
- 가져온 값과 입력한 값의 password가 같다면 가져온 Entity의 값을 DTO로 변환시키고 return해준다
- 값이 없거나 password가 다르다면 null을 반환해준다




##### main.html
```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>main</title>
</head>
<body>
    session 값 확인 : <p th:text="${session.loginEmail}"></p>
</body>
</html>
```
![1](../images/login/1.png)   
- thymleaf를 사용하기 위해 xmlns:th="http://www.thymeleaf.org"를 추가해준다
- p태그로 세팅해둔 session의 값을 가지고 온다
