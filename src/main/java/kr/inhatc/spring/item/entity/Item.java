package kr.inhatc.spring.item.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import kr.inhatc.spring.item.constant.ItemSellStatus;
import kr.inhatc.spring.item.dto.ItemFormDto;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
//name 속성 = 테이블 명칭을 임의로 설정할 수 있다.

@Table(name = "item" )
@Getter
@Setter

//생성되는 필드에 대해서 문자열을 자동으로 생성한다. 
@ToString

//빈 생성자를 만든다.
@NoArgsConstructor

//모든 entity에 대해서 생성자를 만든다. 
@AllArgsConstructor
public class Item extends BaseEntity
{
	@Id //기본키 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) //mysql의 경우 identity를 사용. 
	@Column(name ="item_id" )//name 속성은 column의 이름을 변경할 수 있다. 
	private Long id;                //상품 코드
	
//	null을 허용하지 않음.
	@Column(nullable = false, length = 50)
	private String itemNm;          //상품 이름
	
	@Column(nullable = false)
	private int price; 			//상품 가격
	
	@Column(nullable = false, name = "number")
	private int stockNumber;		//재고 수량
	

//	열거형은 기본적으로 숫자로 다루는 것이 원칙. 해당 어노테이션은 열거형을 나타내는 어노테이션
//	ORDINAL = 숫자로 다룬다. STRING = 문자로 다룬다. 
	@Enumerated(EnumType.STRING)
	private ItemSellStatus itemSellStatus;
	
	@Lob
	@Column(nullable = false)
	private String itemDetail;		//상품 상세 설명
	
	public void updateItem(ItemFormDto itemFormDto) {
	    this.itemNm = itemFormDto.getItemNm();
	    this.price = itemFormDto.getPrice();
	    this.stockNumber = itemFormDto.getStockNumber();
	    this.itemDetail = itemFormDto.getItemDetail();
	    this.itemSellStatus = itemFormDto.getItemSellStatus();
	}
	
}
