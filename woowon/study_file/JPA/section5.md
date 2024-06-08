# section5

### 연관관계 매핑
- 다중성, 단방향, 양방향, 연관관계 주인을 고려해야한다
- 단방향, 양방향
    - 테이블
        - 외래 키 하나로 양쪽 조인 가능
    - 객체
        - 참조용 필드가 있는 쪽으로만 참조 가능
        - 한쪽만 참조하면 단방향
        - 양쪽이 서로 참조하면 양방향
- N:1
    - @ManyToOne
    - 반대는 1:N
    - N쪽에 외래키가 있어야함
    - 반대는 1:N
- 1:N
    - @OneToMany
    - 1이 연관관계 주인
    - 테이블 일대다 관계는 항상 N쪽에 외래키가 있음
    - 객체와 테이블의 차이 때문에 반대편 테이블의 외래키를 관리하는 특이한 구조
    - @JoinColumn을 꼭 사용해야한다
    - 엔티티가 관리하는 외래 키가 다른 테이블에 있음
    - 연관관계 관리를 위해 추가로 UPDATE SQL 실행
    - 일대다 단반향 매핑보다는 다대일 양방향 매핑을 사용
- 1:1
    - @OneToOne
    - 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
        - 외래키가 있는 곳이 연관관계 주인
        - 반대편은 mappedBy적용
    - 외래 키에 데이터베이스 유니크 제약조건 추가
    - N:1 단반향 매핑과 유사하다
    - 단방향은 지원X
- N:M
    - @ManyToMany
    - 실무에서 사용하지 않아야 한다
    - 관계형 데이터베이스느 정규화된 테이블 2개로 다대다 관계를 표현할 수 없다
    - 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야 한다
    - 객체는 컬렉션을 사용해서 객체 2개로 다대다 관계가 가능하다
    - @ManyToMany -> @OneToMany, @ManyToOne
        - 중간을 이어줄 Entity를 생성해 @ManyToMany대신에 @OneToMany, @ManyToOne를 사용하도록 만든다

##### Order.java
```
@Entity
@Table(name = "ORDERS")
@Getter @Setter
public class Order {
	
	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;
	
	@ManyToOne // N:1
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
}
```

##### Delivery.java
```
@Entity
@Getter @Setter
public class Delivery {

	@Id @GeneratedValue @Column(name = "DELIVERY_ID")
	private Long id;
	
	private String city;
	private String street;
	private String zipcode;
	private DeliveryStatus status;
	
	@OneToOne(mappedBy = "delivery")
	private Order order;
}

```

##### DeliveryStatus.java
```
public enum DeliveryStatus {
	
}
```

##### Category.java
```
@Entity
@Getter @Setter
public class Category {
	
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Category> child = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "CATEGORY_ITEM",
			joinColumns = @JoinColumn(name = "CATEGORY_ID"),
			inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
			)
	private List<Item> items = new ArrayList<>();
	
}
```

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
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();
}
```
