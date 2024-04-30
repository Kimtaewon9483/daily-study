# Spring Boot Study

### 파일 수정
##### application.yml
```
server:
    port: 8081
```
- 기존에 있던 application.properties을 삭제한후 새롭게 생성한다
- resource폴더 내에 생성
- 포트번호를 8081로 설정한다
    - 서버를 열면 포트번호가 8081로 변경된다


### 주소 요청
#### HomeController.java
```
@Controller
public class HomeController {
    // 기본페이지 요청 메서드
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
}
```
- @Controller 어노테이션은 해당 클래스가 Controller로 사용된다는것을 알려준다
- GetMapping은 해당 경로에 요청으 오면 실행된다
    - return이 index이므로 templates 폴더의 index.html을 찾아간다

#### index.html
```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>index</title>
</head>
<body>
    <h2>Hello SpringBoot!!!</h2>
</body>
</html>
```
![1](./images/base/1.png)   
- http://localhost:8081/ 를 주소창에 검색하면 브라우저에 화면이 출력된다