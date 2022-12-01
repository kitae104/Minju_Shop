package kr.inhatc.spring.item.repository;

//Qitem을 import static으로 설정해서 사용
import static kr.inhatc.spring.item.entity.QItem.item;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.inhatc.spring.item.constant.ItemSellStatus;
import kr.inhatc.spring.item.entity.Item;
import kr.inhatc.spring.item.entity.QItem;

@SpringBootTest
class ItemRepositoryTest {

//	영속성 컨텍스트를 만들어줘야한다. 초창기에서는 @PersistenceContexts를 사용했다. 
//	객체를 만들어서 메모리에 로딩시켜준다.
	@Autowired
	/*엔티티를 관리하는 역할을 한다.
	엔티티 매니저 내부에는 영속성 컨텍스트가 있으며, 이를 통해 엔티티 관리.
	여러 엔티티 매니저가 하나의 영속성 컨텍스트를 공유 가능*/
	EntityManager em;
	
//  이 어노테이션을 이용하여 Bean 주입. 객체를 만든 것처럼 메모리에 올려놓는다. 
	@Autowired
	ItemRepository itemRepository;


	@Test
	void test() {
//		객체 생성
		Item item = new Item();

		System.out.println(item);
	}

	@Test
//  Junit5에 추가된 어노테이션. 테스트 코드 실행 시 여기에서 지정한 테스트명이 노출된다.
	@DisplayName("상품저장테스트")
	public void createItemTest() {
		Item item = new Item();
		item.setItemNm("테스트 상품");
		item.setPrice(10000);
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		item.setRegTime(LocalDateTime.now());
//    작성한 내용 저장.
		Item savedItem = itemRepository.save(item);
		System.out.println(savedItem.toString());
	}

//임의의 상품 데이터 10개 추가
	public void createItemList() {
		for (int i = 1; i <= 10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 제품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
//  작성한 내용 저장.
			Item savedItem = itemRepository.save(item);
		}
	}

	@Test
	@DisplayName("상품명 조회 테스트")
	public void findByItemNmTest() {
//   상품 리스트 먼저 만들고
		this.createItemTest();
//    그 다음 조회
		List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	@Test
	@DisplayName("상품명, 상품상세설명 or 테스트")
	public void findByItemNmOrItemDetailTest() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 제품 상세 설명5");// 결과가 두 개가 나올 것
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	@Test
	@DisplayName("JPQL쿼리")
	public void findByItemDetailTest() {
		this.createItemList();

		List<Item> itemList = itemRepository.findByItemDetail("테스트");
		for (Item item : itemList) {
			System.out.println(item);
		}
	}

	@Test
	@DisplayName("Native쿼리")
	public void findByItemDetailNativeTest() {
		this.createItemList();

		List<Item> itemList = itemRepository.findByItemDetailNative("테스트");
		for (Item item : itemList) {
			System.out.println(item);
		}
	}
	
	@Test
	@DisplayName("querydsl 테스트")
	public void querydslTest() {
		createItemList();
		
//		query를 만들어준다. 생성자가 없는 건 없고, Entitymanager을 만들어줘야 한다. 
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//		QItem을 import static을 사용하여 사용할 수 있다. static으로 선언 했고 아래 코드처럼 item. 으로 접근해서 사용 가능. 
		QItem qItem = new QItem("i");

		
//		현재 판매 중인 상품인지를 확인해서 가져오는 query
		 List<Item> list = queryFactory
		.select(item)
		.from(item)
		.where(item.itemSellStatus.eq(ItemSellStatus.SELL)) //현재 상태가 SELL인 조건만 가져옴
		.where(item.itemDetail.like("%" + "1" + "%")) //like문 작성
		.orderBy(item.price.desc())//가격 역순으로 결과 출력
		.fetch(); 
		
//		책 방법
//		List<Item> list = query.fetch(); 
		
		for(Item item : list) {
			System.out.println(item);
		}
		
		
	}
	
	 public void createItemList2(){
	        for (int i=1; i<=5; i++){
	            Item item = new Item();
	            item.setItemNm("테스트 상품" + i);
	            item.setPrice(10000 + i);
	            item.setItemDetail("테스트 상품 상세 설명" + i);
	            item.setItemSellStatus(ItemSellStatus.SELL);
	            item.setStockNumber(100);
	            item.setRegTime(LocalDateTime.now());
	            item.setUpdateTime(LocalDateTime.now());
//	      작성한 내용 저장.
	            Item savedItem = itemRepository.save(item);
	        }
	        for (int i=6; i<=10; i++){
	            Item item = new Item();
	            item.setItemNm("테스트 상품" + i);
	            item.setPrice(10000 + i);
	            item.setItemDetail("테스트 상품 상세 설명" + i);
	            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
	            item.setStockNumber(0);
	            item.setRegTime(LocalDateTime.now());
	            item.setUpdateTime(LocalDateTime.now());
//	      작성한 내용 저장.
	            Item savedItem = itemRepository.save(item);
	        }
	    }
	 
	 @Test
		@DisplayName("querydsl 테스트")
		public void querydslTest2() {
		 createItemList2();
		 
		 String itemDetail = "테스트";
		 int price = 10003;
		 String itemSellStat = "SELL"; //열거형 아니고 글자 똑같은지 확인해보려고 넣음
		 
		 
		 QItem item = QItem.item;
		 BooleanBuilder builder = new BooleanBuilder();
		 
		 builder.and(item.itemDetail.like("%" + itemDetail + "%"));
//		 내가 가지고 있는 가격보다 디비에 더 큰 가격을 가진 아이템을 디비에서 뽑아온다.
		 builder.and(item.price.gt(price));
		 
//		 내가 비교하고 싶은 문자열과 실제 디비에 들어있는 문자열을 비교한다. 
		 if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {	
//			 builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
		 }
		 
//		 페이징처리
		 Pageable pageable = PageRequest.of(1, 5);
		 
//		 이미 itemRepository에 queryfactory가 선언되어있기때문에 여기서 선언 안 돼도 돌아가는 것
		 Page<Item> findAll = itemRepository.findAll(builder, pageable);
		 System.out.println("전체 갯수 : " + findAll.getTotalElements());
		 List<Item> content = findAll.getContent();
		 for(Item item2 : content) {
			 System.out.println(item2);
		 }
	 }

}
