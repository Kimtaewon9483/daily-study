# section4

### 회원 도메인 개발
- 회원 등록
- 회원 목록 조회

##### Member.java
```
private String name;
```
- username이었던걸 name으로 변경

##### MemberRepository.java
```
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em; 

    // 저장
    public void save(Member member){ em.persist(member); }
    // 회원조회
    public Member findOne(Long id){ return em.find(Member.class,id); }

    // 전체 회원목록
    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class) // jpquery, 반환타입
            .getResultList();
    }
    // 이름으로 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
```

##### MemberService.java
```
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor 
public class MemberService {

    private final MemberRepository memberRepository; 

    // 회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    // 회원전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}

```
- @Transactional(readOnly = true), 데이터 변경은 트랜잭션이 있어야함
    - readOnly = true면 저장이 되지 않는다
- @RequiredArgsConstructor은 final이 있는 필드만 가지고 생성자를 만들어줌


##### MemberServiceTest.java
```
@SpringBootTest
@Transactional 
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("Lee");
        
        //when
        Long saveId = memberService.join(member);

        //then
        // em.flush();// 쿼리가 나감, 인설트 했다가 롤백을함
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("Lee");
        Member member2 = new Member();
        member2.setName("Lee");

        //when
        memberService.join(member1);
        //memberService.join(member2); // 예외 발생
        
        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //fail("예외가 발생해야 한다");
    }
}

```
- 회원가입은 회원 정보를 저장하는 테스트 메서드이다
- 중복_회원_예외는 이름이 중복일때를 테스트하는 메서드인다
    - 중복이라면 IllegalStateException을 던지도록 MemberService에서 만들었다