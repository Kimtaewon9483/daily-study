# section2

### 엔티티 클래스 개발
- Getter는 모두 열어두는게 편리하지만 Setter는 필요한 곳에서만 열어두는게 좋다
    - Setter를 모두 열어둔다면 나중에 코드를 수정할때 값을 변경하는 곳을 찾아야하는데 Setter를 모두 열어두면 찾기 힘들다
    - 에제는 그냥 편하게 모두 열어둠
- 필드에 id에 Column에 name을 주는 이유는 FK키와 맞추기 편하며 id만으로 쉽게 구분이 가능하다
- 실무에서는 ManyToMany를 사용하지 않는게 좋다
    - 중간테이블에 컬럼을 추가할수 없고 세밀하게 쿼리를 실행하기 어렵기 떄문
    - 사용을 해야한다면 중간에 엔티티를 하나 만들고 @ManyToOne, @OneToMany를 사용해 매핑을 이용해 사용한다
    - 다대다 관계에 중간 테이블을 하나 만들어 그 테이블과 매핑시킨다
- 값타입은 변경 불가능하게 설계해야한다
    - @Setter를 제거하고, 생성자에서 값을 모두 초기화해서 변경이 불가능한 클래스를 만든다
    - JPA는 엔티티나 임베디드 타입(@Embeddable)은 자바 기본생성자를 public또는 protected로 설정해야한다
        - protected가 더 안전하다

### 엔티티 설계시 주의점
- 엔티티에는 가급적 Setter를 사용하지 않는다
    - 유지보수가 어려움
- 모든 연관관계는 지연로딩으로 설정
    - 즉시로딩은 예측이 어렵고 어떤 SQL이 실행되는지 확인하기 어렵다
    - 실무에서는 모든 연관관게를 지연로딩(LAZY)로 설정해야한다
    - 연관된 엔티티를 함께 DB에서 조회해야한다면 fetch join 또는 엔티티 그래프 기능을 사용한다
    - @XToOne관계는 기본이 즉시로딩이므로 직접 지연로딩(LAZY)으로 설정해야한다
- 컬렉션은 필드에서 초기화 한다
    - null문제에서 안전한다
    - 하이버네이트가 엔틴티를 영속화 할 때, 컬렉션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다
- 테이블, 컬럼명 생성 전략
    - 하이버네이트 기본구현: 엔티티의 필드명을 그대로 테이블 명으로 사용
    - 스프링부트 신규 설정
        - 카멜케이스 -> 언더스코어(memberPoint->member_point)
        - . -> _
        - 대문자 -> 소문자


### 엔티티 생성

##### Member.java
```
@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id") 
    private Long id;

    private String username;
    
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
```
- @Embedded은 Address클래스가 해당클래스에 포함되어있음을 알려준다
- id의 Column의 이름을 member_id로 설정한다
- @OneToMany(mappedBy = "member")은 order테이블에 있는 member필드와 매핑되어있다

##### Order.java
```
@Entity
@Table(name="orders")
@Getter @Setter
public class Order {
    
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "member_id") // FK키
    private Member member;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") // FK키
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //== 연관관계 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItems orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
```
- OrderStatus의 enum을 사용하기위해 @Enumerated(EnumType.STRING)을 사용한다
- @JoinColumn으로 FK를 지정한다

##### OrderStatus.java
```
public enum OrderStatus {
    ORDER, CANCEL
}
```

##### Delivery.java
```
@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
```
- @Embedded를 사용해 Address클래스를 사용한다
- DeliveryStatus의 값을 사용한다
    - @Enumerated(EnumType.STRING)을 사용해야 값이 제대로 들어간다
    - EnumType.ORDINAL <- 1,2,3,4 숫자로 나타남
        - 중간에 값이 추가되면 기존 값들이 이상해지기 대문에 STRING을 사용해야한다

##### DeliveryStatus.java
```
public enum DeliveryStatus {
    READY, COMP
}
```
##### Address.java
```
@Embeddable 
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address(){}

    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
```
- @Embeddable로 Address클래스는 다른 클래스에 포함될수 있음을 알려준다
- 해당 클래스의 값은 변경되면 안되기 때문에 Setter를 사용하지 않는다
- Address메소드로 필드값을 초기화한다

##### OrderItems.java
```
@Entity
@Getter @Setter
public class OrderItems {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량
}
```

##### Item.java
```
@Entity
// 상속관계 매핑
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 추상클래스
        
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    // 공통 속성
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
```
- @Inheritance(strategy = InheritanceType.SINGLE_TABLE)은 상속받는 필드들을 한테이블에 모두 때려박는것
- @DiscriminatorColumn(name = "dtype")으로 상속 클래스를 구분한다
##### Album.java, Book.java, Movie.java
```
@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item{

    private String artist; // 아티스트
    private String etc; // 기타정보
}

@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item{

    private String author; // 저자
    private String isbn; // 고유번호
}

@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Movie extends Item{

    private String director; //디렉터
    private String actor; // 배우
}
```
- @DiscriminatorValue로 클래스를 구분한다

##### Category.java
```
@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
```
- 다대다 관계는 사용하지 않는것이 좋다
- name = "category_item"은 사용할 JoinTable 이름이다
- joinColumns = @JoinColumn(name = "category_id")은 현재 Entity에서 참조할 FK
- inverseJoinColumns = @JoinColumn(name = "item_id")은 상대방 Entity에서 참조할 FK

