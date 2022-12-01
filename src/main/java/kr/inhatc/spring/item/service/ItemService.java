package kr.inhatc.spring.item.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.inhatc.spring.item.dto.ItemFormDto;
import kr.inhatc.spring.item.dto.ItemImgDto;
import kr.inhatc.spring.item.entity.Item;
import kr.inhatc.spring.item.entity.ItemImg;
import kr.inhatc.spring.item.repository.ItemImgRepository;
import kr.inhatc.spring.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional //여기도 마찬가지로 서비스 등록하다가 깨지면 처음부터 다시 해야하니까 transaction 해줘야 한다.
public class ItemService {
    
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;
    
//   아이템을 등록했으니까 아이템 아이디가 넘어왔을 것이다
//    itemDto 값을 넘겨 받고, multipart 형식으로 되어있는 리스트를 받아온다.
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {
        
//        DTO를 entity로 바꾼다. createItem에서 mapper로 바꿨으니까
        Item item = itemFormDto.createItem();
        itemRepository.save(item);
        
        
//        그림 저장하기
//        내가 저장한 이미지의 갯수만큼 돌린다
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
//            기존에 있던 아이템의 id값을 세팅해야한다. 영속성 영역에 떠있기 때문에 지금 값이 채워져있는 상태가 돼서 그냥 값을 가져오기만 하면 된다
            itemImg.setItem(item);
//            첫 번째 이미지면 대표 이미지로 쓴다
            if(i == 0) {
                itemImg.setRepImgYn("Y");
            }else {
                itemImg.setRepImgYn("N");
            }
            
//            실제로 DB에 집어넣어야한다 파일 리스트에 있는 애들 중 i번째에 있는 애들을 꺼내서 등록을 해준다.
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        
//        아이디를 반환한다
        return item.getId();
    }

    public ItemFormDto getItemDetail(Long itemId) {
        
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        
        for(ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
        
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        
        return itemFormDto;
    }
    
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {
        
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new); 
        
        item.updateItem(itemFormDto);
        
        List<Long> itemImgIds = itemFormDto.getItemImgIds();
        
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        
        return item.getId();
    }
}
