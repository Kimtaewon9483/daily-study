# section8

### 기본값 타입
- 엔티티 타입
    - @Entity로 정의하는 객체
    - 데이터가 변해도 식별자로 지속해서 추적 가능
        - 엔티티의 값을 변경해도 식별자로 인식 가능
- 값타입
    - int, Integer, String 처럼 단순히 값으로 사용하는 자바 기본타입이나 객체
    - 식별자가 없고 값만 있으므로 변경시 추적 불가
    - 값을 변경하면 다른 값으로 대체
    - 기본값타입
        - 자바 기본 타입(int, double)
        - 래퍼 클래스(Integer, Long)
        - String
        - 생명주기를 엔티티의 의존
        - 값타입은 공유하면 안된다
        - Integer같은 래퍼 클래스나 String 같은 특수한 클래스는 공유 가능한 객체이지만 변경하지 않는다
    - 임베디드 타입(embedded type, 복합 값 타입)
    - 컬렉션 값 타입(collection value type)

### 임베디드 타입
- 새로운 값 타입을 직접 정의할 수 있음
- 주로 기본 값 타입을 모아서 만들어서 복합 값 타입이라고도 함
- int, String과 같은 타입
- 장점
    - 재사용이 가능
    - 높은 응집도
    - 임베디드 타입을 포함한 모든 값 타입은, 값 타입을 소유한 엔티티에 생명주기를 의존함
- @Embeddable : 값 타입을 정의하는 곳에 표시
- @Embedded : 값 타입을 사용하는 곳에 표시
- 기본 생성자는 필수이다
- 임베디트 타입은 엔티티의 값일 뿐이다
- 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다
- 객체와 테이블을 세밀하게 매핑하는 것이 가능하다
- 한 엔티티에서 같은 값 타입을 사용하려면 컬럼 명이 중복된다
    - @AttributeOverrides, @AttributeOverride를 사용해 재정의한다
- 임베디드 타입의 값이 null이면 매핑한 컬럼 값은 모두 null이다
##### USERINFO.java
```
@Entity
@Getter @Setter
public class UserInfo {
    @Id
    @GeneratedValue
    @Column(name = "USERINFO_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "city",column = @Column(name = "WORK_CITY")),
        @AttributeOverride(name = "street",column = @Column(name = "WORK_STREET")),
        @AttributeOverride(name = "zipcode",column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;
}
```
##### Address.java
```
@Embeddable
@Getter @Setter
public class Address {
    // 주소
    private String city;
    private String street;
    private String zipcode;

    //private UserInfo userInfo;

    public Address(){}
    public Address(String city,String street,String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
```

##### Period.java
```
@Embeddable
@Getter @Setter
public class Period {
     // 기간
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period(){}
}
```