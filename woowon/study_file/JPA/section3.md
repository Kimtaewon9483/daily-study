# section3

### 엔티티 매핑
- 객체와 테이블 매필 : @Entity, @Table
    - @Entity
        - @Entity가 붙은 클래스는 JPA가 관리
        - JPA를 사용해서 테이블과 매핑할 클래스는 @Entity가 필수
        - 기본생성자 필수
        - final 클래스, enum, interface,inner 클래스 사용X
        - 저장할 필드에 final 사용X
    - @Table
        - Entity와 매핑할 테이블 지정
        - name : 매핑할 테이블 이름(기본값은 엔티티 이름)
- 필드와 컬럼 매핑 : @Column
    - unique로 제약조건을 줄수 있다
    - DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다
    - @Column : 컬럼 매핑
        - name : 필드와 매핑할 테이블의 컬럼 이름(기본값은 객체의 필드 이름)
        - insertable, updatable : 등록,변경 가능 여부(기본값 true)
        - nullable(DDL) : null허용 여부를 설정, false로 설정시 not null 제약조건이 붙는다
        - unique(DDL) : 유니크 제약조건을 걸 때 사용
        - columnDefinition(DDL) : 데이터베이스 컬럼정보를 직접 줄수 있다
            - columnDefinition = "varchar(100) default 'EMPTY'"
            - varchar 100에 기본값 EMPTY
        - length(DDL) : 문자 길이 제약조건, String타입에만 사용이 가능하다(기본값 255)
        - precision, scale(DDL) : 아주 큰 숫자나 소수점을 사용할때 사용
    - @Temporal : 날짜 타입 매핑
        - 최신버전이라면 LocalDate, LocalDateTime을 사용해도 된다
    - @Enumerated : enum 타입 매핑
        - ORDINAL(default), STRING이 있다
        - ORDINAL은 순서를 데이터베이스에 저장
            - str1,str2,str3...
            - 0부터 시작
            - enum에 값 추가시 값이 이상해질수 있다
        - STRING은 이름을 데이터베이스에 저장
            - str1,str2,str3...
            - enum의 값이 그대로 들어가기 때문에 값이 추가되어도 문제가 없다
    - @Lob : BLOB, CLOB 타입 매핑
        - 매핑하는 필드 타입이 문자면 CLOB, 나머지는 BLOB
        - CLOB : String, char[], java.sql.CLOB
        - BLOB : byte[], java.sql.BLOB
    - @Transient : 특정 필드를 컬럼에 매핑하지 않는다
        - 데이터베이스에 저장,조회X
        - 주로 메모리상에서만 임시로 어떠한 값을 보관하고 싶을때 사용한다
- 기본 키 매핑 : @Id
    - 직접할당 : @Id
    - 자동생성 : @GeneratedValue
        - IDENTITY : 데이터베이스에 위임, MYSQL
        - em.persist()시점에 INSERT SQL을 실행하고 DB에서 조회
            - em.persist()를 하면 .getId()로 값을 가져올수있다(persist()를 할때 id값은 세팅되어있지 않음, 자동생성)
        - SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, ORACLE
            - @SequenceGenerator사용
                - 테이블마다 시퀀스를 따로 관리하고 싶을때 사용
                    - name(식별자), sequenceName(매핑할 데이터베이스 시퀀스 이름), initialValue(시작값, 기본1), allocationSize(시퀀스 한번 호출에 증가하는 수, 기본값 50)
            - persist()시 시퀀스의 값을 가져와서 id의 값을 넣어주고 영속성 컨텍스트에 넣어준다
                - 트랜잭션 커밋때 insert한다
        - TABLE : 키 생성용 테이블 사용, 모든DB에서 사용
            - @TableGenerator 필요
            - 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
            - 장점 : 모든 데이터베이스에 적용 가능
            - 단점 : 성능
        - AUTO : 방언에 따라 자동지정, 기본값 
- 연관관계 매핑 : @ManyToOne, @JoinColumn
- 데이터베이스 스키마 자동 생성
    - DDL을 애플리케이션 실행 시점에 자동 생성
    - 테이블중심 -> 객체 중심
    - 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적합한 DDL생성
    - 생성된 DDL은 개발 장비에서만 사용, 운영서버에서는 사용하지 않거나 적절히 다듬은 후 사용
        - 데이터가 날라가버리기 때문에 validate또는 none을 사용
- 권장하는 식별자 전략
    - 기본 키 제약 조건 : null이 아니고 유일해야하며 변하면 안된다
    - 먼 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대체키를 사용
    - 권장 : Long형 + 대체키 + 키 생성전략 사용

### Entity class
##### Member.java
```
@Entity
@Getter @Setter
public class Member {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String name;
	
	private String city;
	
	private String street;
	
	private String zipcode; // 우편번호
	
}
```
- 회원 정보 Entity

##### OrderStatus.java
```
public enum OrderStatus {
	ORDER, CANCEL
}
```
- 주문상태를 가지는 enum

##### Order.java
```
@Entity
@Table(name = "ORDERS")
@Getter @Setter
public class Order {
	
	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;
	
	@Column(name = "MEMBER_ID")
	private Long memberId;
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
}
```
- 주문 Entity

##### Item.java
```
@Entity
@Getter @Setter
public class Item {
	
	@Id @GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;
	
	private String name;
	
	private int price;
	
	private int stockQuantity;
}
```
- 상품 Entity

##### OrderItem.java
```
@Entity
@Getter @Setter
public class OrderItem {
	
	@Id @GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	
	@Column(name = "ORDER_ID")
	private Long orderId;
	
	@Column(name = "ITEM_ID")
	private Long itemId;
	
	private int orderPrice;
	
	private int count;
}
```
- 주문정보 Entity