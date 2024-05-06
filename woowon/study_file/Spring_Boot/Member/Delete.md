# Spring boot 유저 삭제

##### MemberController.java
```
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }
```
- id값을 가져와 memberService.deleteById메서드로 해당 유저를 삭제한다

##### MemberService.java
```
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
```
- jpa에서 제공하는 deleteById로 해당 id를 삭제한다