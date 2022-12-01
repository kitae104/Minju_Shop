package kr.inhatc.spring.item.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
//파일을 만드는 일을 한다.
//파일을 지우는 일을 한다. 
@Slf4j
public class FileService {
    
//    특정 폴더를 잡고 올리고 싶지만, 파일 명칭이 충돌이 날 수 있기 때문에 파일 명칭이 충돌이 나지 않게 하는 메소드. 
    public String uploadFile(String uploadPath, String oriFilename, byte[] fileData) throws IOException {
        
//        임의의 UUID를 자동으로 만들어준다
        UUID uuid = UUID.randomUUID();
        
//        file의 확장자를 떼기 위함
//        요즘 파일은 중간중간에도 .을 찍기 때문에 맨 마지막에 있는 확장자만 지운다.
        String extension = oriFilename.substring(oriFilename.lastIndexOf("."));
//        uuid는 문자열이 아니니까 toString 꼭 해줘야 함 거기에 확장자만 붙여주면 됩니다
        String savedFileName = uuid.toString() + extension;
        
//        실제 파일 경로 -> D://어쩌고~ / uuid로 만든 파일 이름.extension으로 저장됨
        String fileUploadUrl = uploadPath + "/" + savedFileName;
        
        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
//       위에서 받아온 fileData를 써줍니다
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }
    
//    파일을 지우는 동작
    public void deleteFile(String filePath) {
//         파일을 지우기 위해서는 file 객체 필요
        File deleteFile = new File(filePath);
        
//        만약에 지우려고 하는 파일이 존재한다면?
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제했습니다");
        }else {
            log.info("파일이 존재하지 않습니다");
        }
    }

}
