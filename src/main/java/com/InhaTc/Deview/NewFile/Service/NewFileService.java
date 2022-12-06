package com.InhaTc.Deview.NewFile.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Service
@Slf4j
public class NewFileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = System.currentTimeMillis() + originalFileName;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
