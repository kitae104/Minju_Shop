package kr.inhatc.spring.item.dto;

import java.time.LocalDateTime;

import kr.inhatc.spring.item.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

//getter 빼먹으면 값을 못 가져와잉!
@Getter
@Setter
public class ItemDto {
	
	private Long id;                //상품 코드
	
	private String itemNm;          //상품 이름
	
	private int price; 			//상품 가격
	
	private int stockNumber;		//재고 수량
	
	private ItemSellStatus itemSellStatus;
	
	private String itemDetail;		//상품 상세 설명
	
	private LocalDateTime regTime; //제품 등록 시간
	
	private LocalDateTime updateTime; // 수정 시간	

}
