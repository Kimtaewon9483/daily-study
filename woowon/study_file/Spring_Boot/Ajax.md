# ajax
- 비동기 방식으로 요청 처리
- 화면에 새로고침 없이 서버와 요청을 주고받아 화면에 띄워줌
- email,id중복체크등에 사용할 수 있다

### HomeController.java
```
    // ajax study index
    @GetMapping("/ajax")
    public String ajaxIndex() {
        return "ajax/ajax_index";
    }
```
- ajax를 위한 ajax_index를 리턴해준다

### ajax_index.html
[ajax_index](./Ajax/ajax/ajax_index.html)

### 예제
[Ex1](./Ajax/ajax/ajax-ex-01.html),
[Ex2](./Ajax/ajax/ajax-ex-02.html),
[Ex3](./Ajax/ajax/ajax-ex-03.html),
[Ex4](./Ajax/ajax/ajax-ex-04.html),
[Ex5](./Ajax/ajax/ajax-ex-05.html),
[Ex6](./Ajax/ajax/ajax-ex-06.html),
[Ex7](./Ajax/ajax/ajax-ex-07.html),
[Ex8](./Ajax/ajax/ajax-ex-08.html),
[Ex9](./Ajax/ajax/ajax-ex-09.html),
[Ex10](./Ajax/ajax/ajax-ex-10.html)