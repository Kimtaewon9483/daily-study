# section6

### 주문 도메인 개발
- 상품 주문
- 주문 내역 조회
- 주문 취소

##### Order.java
```
    //== 비즈니스 로직 ==//
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItems orderItems : orderItems) {
            orderItems.cancel();
        }
    }

    //== 조회 로직 ==//
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItems orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
```
- 주문 취소와 전체 주문 가격에대한 메서드
- 주문 취소에서 이미 배송이 완료되었다면 취소가 불가능하다


##### OrderItems.java
```
    //== 비즈니스 로직 ==//
    public void cancel() {
        getItem().addStock(count); // 재고수량 원복
    }

    //== 조회 로직 ==//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
```
- 주문 취소시 재고는 다시 올라가야하며 주문시 상품의 가격을 계산하는 메소드

##### OrderRepository.java
```
    public List<Order> findAll(OrderSearch orderSearch){
        String jpql = "select 0 from Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문상태 검색
        if(orderSearch.getOrderStatus() != null){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        // 회원이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);// 최대 1000개까지 조회
        if(orderSearch.getOrderStatus() != null){
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if(StringUtils.hasText(orderSearch.getMemberName())){
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }
```

##### OrderSearch.java
```
@Getter @Setter
public class OrderSearch {
    private String memberName; // 회원 이름
    private OrderStatus orderStatus; // 주문상태 [ORDER, CANCEL]
}
```
- 검색시 이름, 주문상태에대한 클래스

##### OrderService.java
```
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    
    public final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);    
        Item item = itemRepository.findOne(itemId);
        
        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItems orderItem = OrderItems.createOrderItems(item, item.getPrice(), count);
        
        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        
        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // 검색 
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
     
}
```
- 주문, 주문취소, 검색에 대한 메서드

### 테스트 코드
##### OrderServiceTest.java
```
@SpringBootTest
@Transactional
public class OrderServiceTest {
    
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        Member member = createMember();
        Book book = createBook("JPA",10000,10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus()); // 주문 수량
        assertEquals(1, getOrder.getOrderItems().size()); // 상품 종류 수
        assertEquals(10000 * orderCount, getOrder.getTotalPrice()); // 주문가격은 가격 * 수량
        assertEquals(8,book.getStockQuantity()); // 주문 수량만큼 재고수 감소
    }
    
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        Member member = createMember();
        Item item = createBook("JPA",10000,10);
        
        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
        //fail("재고 수량 부족 예외가 발생해야 한다");
    }

    @Test
    public void 주문취소() throws Exception{
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus()); // 주문취소시 상태는 CANCEL
        assertEquals(10, item.getStockQuantity());// 주문 취소시 상품은 그만큼 재고가 증가해야함
    }


    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","경기","123-123"));
        em.persist(member);
        return member;
    }
    
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}

```