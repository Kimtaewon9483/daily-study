# section6

### 상속관계 매핑
- 관계형 데이터베이스는 상속관계가 없다
- 객체의 상속과 구조와 DB의 슈퍼타입 서브타입 관계를 매핑
    - item -> album, movie, book
    - @Inheritance(strategy=InheritanceType.XXX)
    - 조인 전략 : 각각 테이블로 변환(JOINED)
        - item테이블을 만들고 album, movie, book 테이블을 만든후 조인으로 구성
        - item의 pk와 조인
        - 구분하는 컬럼은 만들어 구분한다
        - 장점
            - 테이블 정규화, 외래키 참조 무결성 제약조건 활용가능, 저장공간 효율화
        - 단점
            - 조회시 조인을 많이 사용, 성능저하, 조회 쿼리가 복잡하다, 데이터 저장시 INSERT 2번 호출
    - 단일 테이블 전략 : 통합 테이블로 변환(SINGLE_TABLE)
        - item테이블에 하나로 합치는것
        - 장점
            - 조인이 필요없어서 조회성능이 빠름
            - 조회쿼리가 단순
        - 단점
            - 자식 엔티티가 매핑한 컬럼을 null을 허용해줘야함
            - 단일 테이블에 모든 것을 저장하기 때문에 테이블이 커질 수 있어서 상황에 따라 조회 성능이 느려질수있다
    - 구현 클래스마다 테이블 전략 : 서브타입 테이블로 변환(TABLE_PER_CLASS)
        - 사용하지 않는걸 추천
        - album, movie, book 테이블을 만든후 item테이블을 사용하지 않고 item테이블의 정보를 다 들고있는것
        - item으로 찾으면 album, movie, book를 다 찾아보기 때문에 비효율적
        - 장점
            - 서브타입을 명확하게 구분해서 처리할때 효과적
            - not null 사용가능
        - 단점
            - 여러 자식 테이블을 함께 조회할때 성능이 느림
            - 자식 테이블을 통합해서 쿼리하기 어려움
- @MappedSuperclass
    - 공통 매핑 정보가 필요할 때 사용(id,name)
    - 상속관계 매핑은 아님
    - 엔티티X, 테이블과 매핑X
    - 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
    - 조회,검색 불가능
    - 직접 생성해서 사용할 일이 없으므로 추상 클래스를 권장한다
    - 전체 엔티티에서 공통으로 적용하는 정보를 모을때 사용한다

##### Album, Movie, Book.java
```
@Entity
@Getter @Setter
public class Album extends Item{
    private String artist;
    private String etc;
}

@Entity
@Getter @Setter
public class Book extends Item{
    private String author;
    private String isbn;
}

@Entity
@Getter @Setter
public class Movie extends Item{
    private String director;
    private String actor;
}
```

##### Item.java
```
@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item extends BaseEntity{
	
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
- 기존 코드에 테이블 전략 추가
    - 싱글테이블 전략
- @DiscriminatorColumn으로 Item을 상속받는 테이블의 이름이 들어가게됨

##### BaseEntity.java
```
@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
```
- 공통적으로 사용할 정보를 담고있음
- @MappedSuperclass를 사용해야함

##### 공통정보 추가
```
extends BaseEntity
```
- Category, Delivery, Order, OrderItem, Member에 상속 추가