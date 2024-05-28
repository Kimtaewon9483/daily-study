# section2

### 영속성 컨텍스트
- 고객의 요청에 따라 EntityManager를 생성을해 내부적으로 DB커넥션을 사용해 DB를 사용하게된다
- 영속성 컨텍스트
    - 엔티티를 영구 저장하는 환경
    - 논리적인 개념, 눈에 보이지 않는다
    - EntityManager를 통해서 영속성 컨텍스트에 접근
    - EntityManager.persist(entity);   
        - DB에 저장하는것이 아닌 영속성 컨텍스트에 저장
- 비영속, 영속, 준영속, 삭제
    - 비영속(new/transient) : 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
        - Member를 생성후 세팅만한 상태, JPA와 관계 없음
    - 영속(managed) : 영속성 컨텍스트에 관리되는 상태
        - EntityManager를 가져와 persist를 사용한 상태
    - 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
    - 삭제(removed) : 삭제된 상태
- 내부에 1차캐시를 가지고있음
    - persist를할시 1차 캐시에 저장된다
    - 조회시 DB를 먼저 조회하는게 아닌 1차 캐시를 먼저 조회
        - 1차캐시에 없다면 DB에서 조회를한후 1차캐시에 저장한다
- 플러시
    - 영속성 컨텍스트의 변경내용을 데이터베이스에 반영한다
    - 변경감지
    - 수정된 에티티 쓰기 지연 SQL 저장소에 등록
    - em.flush() : 직접호출
    - 트랜잭션커밋, JPQL쿼리실행 : 플러시 자동 호출
    - 영속성 컨텍스트를 비우지 않는다
    - 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
    - 트랙잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 된다
- 준영속상태
    - 영속 -> 준영속
    - 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(ㅇefached)
    - 영속성 컨텍스트가 제공하는 기능을 사용하지 못한다
    - em.defach() : 특정 엔티티만 준영속 상태로 전환
    - em.clear() : 영속성 컨텍스트를 완전히 초기화
    - em.close() : 영속성 컨텍스트를 종료


##### Persistence1
```
// 비영속
Member member = new Member(2L,"Member");
member.setId(1L);
member.setName("HelloA");
			
// 영속
System.out.println("=====  BEFORE =====");
em.persist(member);
System.out.println("=====  AFTER =====");
			
Member findMember = em.find(Member.class, 2L);
System.out.println("findMember.id = " + findMember.getId());
System.out.println("findMember.name = " + findMember.getName());
			
Member a = em.find(Member.class, 2L);
Member b = em.find(Member.class, 2L);
System.out.println("a == b >> " + (a==b));
						
Member member1 = new Member(1L,"Member1");
Member member2 = new Member(2L,"Member2");
em.persist(member1);
em.persist(member2);
System.out.println("=========");
						
// 변경감지
Member member3 = em.find(Member.class, 2L);
member3.setName("changeMember");
			
tx.commit();
```
- 처음에 member는 비영속 상태이다  
    - em.persist(member)를 해야만 영속상태이다
- em.find(Member.class, 2L)로 값을 찾을수있다
    - 1차캐시에 동일한 값이있다면 1차캐시에서 값을 가져오지만 없다면 DB select쿼리로 값을 찾아와 1차캐시에 저장한다
    - 변수 a,b로 찾아온값은 같다
- em.persist()를 날려도 커밋이 되지 않는다
    - tx.commit()이때 값이 디비에 저장된다
- 값을 변경할때는 em.persist()를 날리지 않아도 된다
    - em.find()로 값을 찾으면 스냅샷에 저장후 값이 변경된다면 1차캐시와 스냅샷을 비교후 값이 변경되었다면 tx.commit()을 할때 값이 변경된다

##### Persistence2
```
// flush
Member member = new Member(3L,"Member3");
em.persist(member);
System.out.println("==========");
em.flush();
System.out.println("==========");
						
tx.commit();
```
- em.flusth()로 DB에 쿼리를 날릴수 있다
- 1차캐쉬는 유지된다

##### Persistence3
```
Member member = em.find(Member.class, 3L);
member.setName("defach");
						
//em.detach(member);
em.clear();

// 트랙잭션을 커밋하는 이시점에 영속성 컨텍스트에 있는 값이 DB에 저장이된다
tx.commit();
```
- em.defatch()로 준영속성 으로 만든다
- 영속성 컨택스트에서 관리를 하지않기 때문에 값을 변경해도 변경되지 않는다