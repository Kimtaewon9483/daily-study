# section5

### 상품 도메인 개발
- 상품등록
- 상품 목록 조회
- 상품 수정

##### Item.java
```
public void addStock(int quantity){
    this.stockQuantity += quantity;
}

public void removeStock(int quantity){
    int restStock = this.stockQuantity -= quantity;
    if(restStock < 0){
        throw new NotEnoughStockException("need more stock");
    }
    this.stockQuantity = restStock;
}
```
- 재고 증가와 감소 처리를 위한 메서드를 추가한다

##### ItemRepository.java
```
@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> finaAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
```
- 상품이 이미 있는지 없는지를 확인후 없다면 추가한다
- findAll()은 여러개를 찾아야 하기 때문에 쿼리를 작성한다

##### ItemService.java
```
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    // 저장
    @Transactional
    public void saveitem(Item item){
        itemRepository.save(item);
    }

    // 전체조회
    public List<Item> findItems(){
        return itemRepository.finaAll();
    }
    // 단건조회
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
```

##### NotEnoughStockException.java
```
public class NotEnoughStockException extends RuntimeException{
    
    public NotEnoughStockException(){ super(); }

    public NotEnoughStockException(String message){ super(message); }

    public NotEnoughStockException(String message, Throwable cause){ super(message,cause); }

    public NotEnoughStockException(Throwable cause){ super(cause); }
}
```
- 재고의 수량이 0개 이하일때 Exception을 처리하기위한 파일