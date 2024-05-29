# section4

### 연관관계 매핑 기초
- 객체의 참조와 테이블의 외래 키를 매핑
- 단방향
- 양방향
    - 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개다
    - 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다
        - 객체 연관관계 = 2개
        - 회원 -> 팁(단방향)
        - 팀 -> 회원(단방향)
    - 테이블은 외래 키 하나로 두개의 연관관계를 관리
        - 테이블 연관관계 = 1개
        - 회원 <-> 팀(양방향)
    - 객체의 두 관계중 하나를 연관관계의 주인으로 지정
    - 연관관계의 주인만이 외래 키를 관리(등록,수정)
    - 주인이 아닌쪽은 읽기만 가능
    - 주인은 mappedBy 속성 사용X, 주인이 아니면 mappedBy 속성으로 주인 지정
        - 외래키가 있는 곳을 주인으로 지정
    - 주의점
        - 연관관계의 주인에 값을 입력하지 않았을때
        - 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정한다
        - 연관관계 편의 메소드를 생성한다
        - 양방향 매핑시에 무한 루프를 조심한다
            - toString(), lombok, JSON 생성 라이브러리
    - 단방향 매핑만으로도 이미 연관관계 매핑은 완료
        - 양방향 매핑은 반대 방향으로 조회 기능이 추가되는것뿐
        - JPQL에서 역방향으로 탐색할 일이 많다
        - 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 된다
- 다중성
    - N:1
    - 1:N
    - 1:1
    - N:M
- 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다
- 객체는 참조를 사용해서 연관된 객체를 찾는다


##### Member.java
```
	@Id @GeneratedValue @Column(name = "MEMER_ID")
	private Long id;
	
	private String name;
	
	private String city;
	
	private String street;
	
	private String zipcode; // 우편번호
	
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
```

##### Order.java
```
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
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
```

##### OrderItem.java
```
	@Id @GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	private int orderPrice;
	
	private int count;
```