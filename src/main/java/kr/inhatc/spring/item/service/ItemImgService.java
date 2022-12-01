package kr.inhatc.spring.item.service;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import kr.inhatc.spring.item.entity.ItemImg;
import kr.inhatc.spring.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional //그림을 세 개를 올리다가 네 개까지 갔는데 하나만 오류가 날 경우 다 취소하고 다시 해야함. 이게 transaction
public class ItemImgService {
    
//    application properties에 설정해놓은 itemImgLocation를 가져와서 service에서 uploadfile 할 때 사용해준다
    @Value(value = "${itemImgLocation}")
    private String itemImgLocation;
    
    private final ItemImgRepository itemImgRepository;
    
    private final FileService fileService;
    
//    이미지를 저장합니다
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
//        파일을 받아온 시점에 보면 multiopart 안에 오리지날 이름 등 파일 정보가 다 들어있는데 일단 저건 원래 이름을 가지고 오는 것
//        왜냐면 DB에 넣을 때는 원래 이름을 알아야 하기 때문이다
        String oriImgName = itemImgFile.getOriginalFilename();
//        fileService에서 만든 imgName임
        String imgName = "";
//       이미지 경로
        String imgUrl = "";
        
//        원래 경로가 값이 비어있는지 타임리프 유틸을 이용해서 확인한다
        if(!StringUtils.isEmpty(oriImgName)) {
//            진짜 이미지 이름 받아옴
//            파일의 정보는 itemImgFile에 다 있으니까 이걸 byte배열로 가져온다
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }
        
//        실제 상품 이미지 저장
        itemImg.updateImg(oriImgName, imgName, imgUrl);
//        이미지만 저장 그러니까 아이템도 저장해줘야함
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long ItemImgId, MultipartFile itemImgFile) throws IOException {
        if(!itemImgFile.isEmpty()) {
            ItemImg itemImg = itemImgRepository.findById(ItemImgId).orElseThrow(EntityNotFoundException::new);
            
            if(!StringUtils.isEmpty(itemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + itemImg.getImgName());
            }
            
            String oriName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            
            itemImg.updateImg(oriName, imgName, imgUrl);
        }
    }
    
    
}
