package kr.inhatc.spring.item.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import kr.inhatc.spring.item.constant.ItemSellStatus;
import kr.inhatc.spring.item.entity.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
//ItemDto라고 해도 된다
public class ItemFormDto {

    private Long id;
    
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;          //상품 이름
    
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;          //상품 가격
    
    @NotNull(message = "재고는 필수 입력 값입니다.")
    private int stockNumber;        //재고 수량
    
    @NotBlank(message = "상품 상세 설명은 필수 입력 값입니다")
    private String itemDetail;   //상품 상세 설명
    
    private ItemSellStatus itemSellStatus; //상품 판매 상태
    
//    아이디 정보를 받아오려고 함
    private List<Long> itemImgIds = new ArrayList<>();
    
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    
    private static ModelMapper modelMapper = new ModelMapper();
    
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }
    
    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
    
}
