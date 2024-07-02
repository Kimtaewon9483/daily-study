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

### 페치조인(fetch join)
- SQL조인 종류X
- JPQL에서 성능 최적화를 위해 제공하는 기능
- 연관된 엔티티나 컬렉션을 SQL한번에 함께 조회하는 기능
- join fetch명령어 사용
- 일반조인 실행시 연관된 엔티티를 함께 조회하지 않는다
    - JPQL은 결과를 반환할때 연관관계 고려X
    - select절에 지정한 엔티티만 조회
    - 페치조인을 사용할 때만 연관된 엔티티도 함께 조회
    - 페치조인은 객체그래프를 SQL한번에 조회하는 개념
- ex
    - 회원을 조회할때 연관된 팀도 함께 조회
        - JPQL
            - select m from Member m join fetch m.team
        - SQL
            - select M.*, T.* from Member M INNER JOIN TEAM T ON M.TEAM_ID = T.ID
    - 컬렉션 페치 조인
        - 일대다 관계, 컬렉션 페치 조인
            - JPQL
                - select t from Team t join fetch t.members where t.name = '팀A'
            - SQL
                - select T.*, M.* from Team t INNER JOIN Member m ON t.id = m.team_id where t.name = '팀A'
        - SQL의 DISTINCT는 중복된 결과를 제거하는 명령
            - JPQL의 DISTINCT 2가지 기능 제공
                - SQL에 DISTINCT를 추가
                - 애플리케이션에서 엔티티 중복 제거