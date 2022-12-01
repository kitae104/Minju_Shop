package kr.inhatc.spring.item.dto;

import org.modelmapper.ModelMapper;

import groovy.transform.ToString;
import kr.inhatc.spring.item.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ItemImgDto {

    private Long id; // 상품 코드

    private String imgName; // 이미지 파일명

    private String oriImgName; // 원본 파일명

    private String imgUrl; // 이미지 경로

    private String repImgYn; // 대표 이미지
    
    private static ModelMapper modelMapper = new ModelMapper();
    
    public static ItemImgDto of(ItemImg itmImg) {
//        아이템 이미지 디티오로 받아서 매핑한다는 뜻
        return  modelMapper.map(itmImg, ItemImgDto.class);
    }

}
