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