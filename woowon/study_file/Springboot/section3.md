# section3

### 구현 요구사항
- 회원
    - 등록, 조회
- 상품기능
    - 등록, 수정, 조회
- 주문기능
    - 상품주문, 주문 내역 조회, 주문 취소
- 로그인 권한 관리x, 파라미터 검증과 에외 처리 단순화, 상품은 도서만 사용, 카테고리는 사용X, 배송정보는 사용X

### 애플리케이션 아키텍쳐
- 계층형 구조 사용
    - controller, web : 웹 계층
    - service : 비즈니스 로직, 트랙잭션 처리
    - repository : JPA를 직접 사용하는 계층, 엔티티 매니저 사용
    - domain : 엔티티가 모여 있는 계층, 모든 계층에서 사용
- 패키지 구조
    - jpabook.jpashop
        - domain
        - exception
        - repository
        - service
        - web