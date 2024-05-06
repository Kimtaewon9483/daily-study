# Spring boot 로그아웃

##### main.html
```
    <a href="/member/logout">로그아웃</a>
```
- 로그아웃을 하기위한 a태그를 추가한다

##### MemberController.java
```
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
```
- session.invalidate() 으로 세션을 모두 종료시킨다
- 로그인시 세션에 추가되기 때문에 세션을 종료시키면 다시 로그인을 해야한다