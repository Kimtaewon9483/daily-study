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

### 값타입과 불변 객체
- 값 타입 공유 참조
    - 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험함
    - 부작용 발생
    - city의 값을 new city로 변경하면 다른 테이블의 값도 변경됨
- 값 타입의 복사
    - 값 타입의 실제 인스턴스인 값을 공유하는 것은 위험
    - 대신 값을 복사해서 적용
- 객체 타입의 한계
    - 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다
    - 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본타입이 아니라 객체 타입이다
    - 자바 기본 타입에 값을 대입하면 값을 복사한다
    - 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다
    - 객체의 공유 참조는 피할 수 없다
- 불변 객체
    - 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
    - 값 타입은 불볍 객체로 설게해야한다
    - 불변 객체 : 생성 시점 이후 절대 값을 변경할 수 없는 객체
    - 생성자로만 값을 설정하고 수정자를 만들지 않으며 됨
    - Integer, String은 자바가 제공하는 대표적인 불변객체

### 값 타입 비교
- 값타입 : 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야한다
- 동일성 비교 : 인스턴스의 참조 값을 비교, == 사용
- 동등성 비교 : 인스턴스의 값을 비겨, equals()사용
    - 값타입은 equals()를 사용해서 동등성 비교를 해야한다
    - 값타입의 equals()메소드를 적절하게 재정의

### 값 타입 컬렉션
- 값 타입을 컬렉션에 담아 사용하는것
- 값 타입을 하나이상 저장할 떄 사용
- @ElementCollection, @CollectionTable을 사용
- 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다
    - 컬렉션을 저장하기 위한 별도의 테이블이 필요함
- 제약사항
    - 값 타입은 엔티티와 다르게 식별자 개념이 없다
    - 값 변경시 추적이 어렵다
    - 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다
    - 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야한다
        - null,중복X 
- 대안
    - 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
    - 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
    - 영속성전이+고아객체 제거를 사용해서 값 타입 컬렉션 처럼 사용
