# section9

### JPQL
- 다양한 쿼리 방벙르 지원
    - JPQL
        - 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
        - SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
        - 단순한 조회 방법
            - EntityManager.find()
            - 객체 그래프 탐색(a.getB().getC())
        - 객체 지향 SQL이다
            - JPA를 사용하면 엔티티 객체를 중심으로 개발
            - 문제는 검색쿼리이다
            - 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
            - 모든 DB데이터를 객체로 변환해서 검색은 불가능하다
            - 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요하다
            - JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
            - SQL과 문법 유사
            - JPQL은 엔티티 객체를 대상으로 쿼리
            - SQL은 데이터베이스 테이블을 대상으로 쿼리
    - JPA Criteria
        - 문자가 아닌 자바코드로 JPQL을 작성 가능하다
        - JPQL빌더 역할, JPA공식 기능
        - 너무 복잡하고 실용성이 없다
    - QueryDSL
        - 문자가 아닌 자바코드로 JPQL을 작성이 가능
        - JPQL빌더 역할
        - 컴파일 시점에 문법 오류를 찾을 수 있다
        - 동적쿼리 작성이 편리하다, 단순하고 쉽다
        - 실무 사용을 권장
    - 네이티브SQL
        - JPA가 제공하는 SQL을 직접 사용하는 기능
        - JPQL로 해결할 수 없는 특정 테이터베이스에 의존적인 기능
    - JDBC API직접사용,MyBatis, SpringJdbcTemplate함께 사용
        - JPA를 사용하면서 JDBC커넥션응 직접 사용하거나, 스프링 JdbcTemplate, 마이바티스등을 함께 사용 가능
        - 영속성 컨텍스트를 적절한 시점에 강제로 플러시가 필요하다

### 기본문법, 쿼리API
- JPQL은 객체지향 쿼리언어, 엔티티 객체를 대상으로 쿼리
- SQL을 추상화 해서 특정 데이터베이스 SQL에 의존하지 않는다
- JPQL은 결국 SQL로 변환된다
- 엔티티와 속성은 대소문자 구분한다
- JPQL키워드는 대소문자 구분하지 않는다
- 엔티티 이름 사용, 테이블 이름이 아니다
- 별칭은 필수(as는 생략이 가능하다)
- TypeQuery는 반환타입이 명확할 때 사용
- Query는 반환타입이 명확하지 않을 때 사용
- query.getResultList() : 결과가 하나 이상일 때 리스트 반환
    - 결과가 없다면 빈 리스트 반환
- query.getSingleResult() : 결과가 정확히 하나일때, 단일 객체 반환
    - 결과가 없으면 NoResultException
    - 결과가 두개 이상이면 NonUniqueResultException
- 파리미터 바인딩
    - select m from Member m where m.username = :username
    - setParameter("username", "member1")
    - ?로도 가능하다 ?1로하면 serParameter를 1로 정한다

### 프로젝션
- SELECT 절에 조회할 대상을 지정하는것
- 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입
- 엔티티 프로젝션
    - select m from Member m
    - select m.team from Member m
- 임베디드 타입 프로젝션
    - select m.address From Member m
- 스칼라 타입 프로젝션
    - select m.username, m.age from Member m
- DISTINCT로 중복제거가 가능하다
- 여러 값 조회
    - Query 타입으로 조회
    - Object[] 타입으로 조회
        ```
        List resultList = em.createQuery("select distinct m.username, m.age from Member m").getResultList();
            Object o = resultList.get(0);
            Object[] result = (Object[])o;
            System.out.println("username =>>>>> " + result[0]);
            System.out.println("age =>>>>> " + result[1]);

            List<Object[]> resultList2 = em.createQuery("select distinct m.username, m.age from Member m").getResultList();
            Object[] result2 = resultList2.get(0);
            System.out.println("username =>>>>> " + result2[0]);
            System.out.println("age =>>>>> " + result2[1]);
        ```
    - new 명령어로 조회
        - 단순 값을 DTO로 바로 조회
        - 패키지 명을 포함한 전체 클래스 명 입력
        - 순서와 타입이 일치하는 생성자 필요
        ```
        List<MemberDTO> result3 = em.createQuery("select new hellojpa.jpashop.domain.example.query.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
            MemberDTO memberDTO = result3.get(0);
            System.out.println("memberDTO >> " + memberDTO.getUsername());
            System.out.println("memberDTO >> " + memberDTO.getAge());
        ```

### 페이징
- JPA는 페이징을 두 API로 추상화
    - setFirstResult : 조회 시작 위치
    - setmaxResults : 조회할 데이터 수

```
List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();
```
```
  /* select
        m 
    from
        Member m 
    order by
        m.age desc */ select
            member0_.id as id1_0_,
            member0_.age as age2_0_,
            member0_.TEAM_ID as TEAM_ID4_0_,
            member0_.username as username3_0_ 
        from
            Member member0_ 
        order by
            member0_.age desc limit ? offset ?
```
- 이런식으로 쿼리가 나가게된다

### 조인
- 내부조인 : select m from Member m [INNER] join m.team t
    - Member가 있고 Team이 없다면 데이터가 나오지 않는다
- 외부조인 : select m from Member m LEFT[OUTER] join m.team t
    - Member가 있고 Team이 없어도 Member는 조회가 된다
- 세타 조인 : select count(m) from Member m, Team t where m.username = t.name
    - 연관관계가 없는 관계를 비교해볼수 있다
- ON절을 활용한 조인(JPA 2.1부터 지원)
    - 조인대상 필터링
        - select m,t from Memer m LEFT JOIN m.team t on t.name='A'
            - 팀의 이름이 A인 애만 조인
    - 연관관계가 없는 엔티티 외부조인(하이버네이트5.1부터)
        - select m,t from Member m LEFT JOIN Team t on m.username = t.name 

### 서브쿼리
- SQL에서의 서브쿼리와 같음
- 나이가 평균보다 많은 회원
    - select m from Member m where m.age > (select avg(m2.age) from Member m2)
- 지원함수
    - [NOT]EXISYS(subquery) : 서브쿼리에 결과가 존재하면 참
        - {ALL|ANY|SOME}(subquery)
        - ALL 모두 만족하면 참
        - ANY,SOME 같은 의미, 조건을 하나라도 만족하면 참
        - 팀 A 소속인 회원
            - select m from Member m where exists (select t from m.team t where t.name = '팀A')
        - 전체 상품 각각의 재고보다 주문량이 많은 주문들
            - select o from Order o where o.orderAmount > ALL (select p.stockAmount from Product p)
        - 어떤 팀이든 팀에 소속된 회원
            - select m from Member m where m.team = ANY (select t from Team t)
    - [NOT] IN (subquery) : 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참
- JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용이 가능
- SELECT 절도 가능(하이버네이트에서 지원)
- FROM 절의 서브 쿼리는 JPQL에서 불가능
    - 조인으로 풀수있으면 풀어서 해결

### JPQL 타입 표현
- 문자 : 'HELLO', 'She''s'
- 숫자 : 10L(Long), 10D(Double), 10F(Float)
- Boolean : TRUE, FALSE
- ENUM : 패키지명
- 엔티티 타입 : TYPE(m) = Member(상속 관계에서 사용)

### 조건식
- 기본 CASE식
```
select
    case when m.age<=10 then '학생요금'
        when m.age>=60 then '경로요금'
        else'학생요금'
    end
from Member m
```
- 단순 CASE식
```
select
    case t.name
        when '팀A' then '110%'
        when '팀B' then '120%'
        else '105%'
    end
from Team t
```
- COALESCE : 하나씩 조회해서 null이 아니면 반환
```
select coalesce(m.username,'이름없는회원') from Member m"
```
- NULLIF : 두 값이 같으면 null반환, 다르면 첫번째 값 반환
```
select nullif(m.username,'관리자') as username from Member m
```

### 기본함수
- JPQL에서 제공하는 표준함수
    - CONCAT,SUBSTRING,TRIM 등등
- 사용자 정의 함수
    - 하이버네이트는 사용전 방언에 추가해야 한다
    - 사용하는 DB 방언을 상속받고, 사용자 함수를 등록한다
```
    public class MyH2Dialect extends H2Dialect{
    public MyH2Dialect(){
        registerFunction("group_concat", new StandardSQLFunction("group_concat",StandardBasicTypes.STRING));
    }
}
```
```
"select function('group_concat', m.username) From Member m"
```