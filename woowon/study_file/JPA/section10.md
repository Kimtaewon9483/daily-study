# section10

### 경로 표현식
- 점을 찍어 객체 그래프를 탐색하는것
```
select m.username -> 상태필드
    from Member m
        join m.team t -> 단일 값 연관 필드
        join m.orders o -> 컬렉션 값 연관 필드
where t.name = 'TeamA'
```
- 상태필드 : 단순히 값을 저장하기 위한 필드
    - 경로탐색의 끝, 탐색x
- 연관필드 : 연관관계를 위한 필드
    - 단일값 연관필드 : @ManyToOne, @OneToOne, 대상이 엔티티
        - 묵시적 내부조인 발생, 탐색o
    - 컬렉션 값 연관필드 : @OneToMany, @ManyToMany, 대상이 컬렉션
        - 묵시적 내부조인 발생, 탐색x
        - FROM절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
- 명시적 조인 : join 키워드 직접 사용
- 묵시적 조인 : 경로 표현식에 의해 묵시적으로 SQL조인 발생(내부 조인만 가능)
    - 항상 내부조인
    - 컬렉션은 경로 탐색의 끝, 명시적조인을 통해 별칭을 얻어야한다
    - 경로탐색은 주로 SELECT, WHERE절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM(JOIN)절에 영향을준다