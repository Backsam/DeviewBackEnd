package com.InhaTc.Deview.NewFile.Service;

import com.InhaTc.Deview.NewFile.Entity.PortfolioFile;
import com.InhaTc.Deview.NewFile.Entity.WantedJobFile;
import com.InhaTc.Deview.NewFile.Repository.PortfolioFileRepository;
import com.InhaTc.Deview.NewFile.Repository.WantedJoFileRepository;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WantedJobFileService {

    private String filepath ="E:/project/Deview/back/src/main/resources/static/WantedJobFile/";

    private final NewFileService newFileService;

    private final WantedJoFileRepository wantedJoFileRepository;

    public void saveFile(WantedJobFile wantedJobFile, MultipartFile file) throws Exception{
        String oriFileName = wantedJobFile.getOriginFileName();
        String fileName = "";
        String fileUrl = "";

        //파일 업로드
        if(!oriFileName.isEmpty()){
            fileName = newFileService.uploadFile(filepath, oriFileName,
                    file.getBytes());
            fileUrl = "/portfolio/" + fileName;
        }

        //상품 이미지 정보 저장
        wantedJobFile.updateFile(oriFileName, fileName, fileUrl);
        wantedJoFileRepository.save(wantedJobFile);
    }

    public void updateFile(WantedJobFile wantedJobFile, MultipartFile file) throws Exception{
        if(!file.isEmpty()){
            //기존 이미지 파일 삭제
            if(!wantedJobFile.getFilename().isEmpty()) {
                log.info(wantedJobFile.getFilename());
                newFileService.deleteFile(filepath +
                        wantedJobFile.getFilename());
            }

            String oriFileName = file.getOriginalFilename();
            String fileName = newFileService.uploadFile(filepath, oriFileName, file.getBytes());
            String fileUrl = "/portfolio/" + fileName;

            wantedJobFile.updateFile(oriFileName, fileName, fileUrl);

            wantedJoFileRepository.save(wantedJobFile);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Resource getWantedThumbnail(WantedJobEntity wantedJob) throws IOException {
        WantedJobFile thumbnail = wantedJoFileRepository.findByWantedJobAndType(wantedJob, "thumbnail");
        Path filePath = Paths.get(filepath);
        Resource resource;
        try {
            Path file;
            if(thumbnail != null){
                file = filePath.resolve(thumbnail.getFilename()).normalize();

            }else{
                file = filePath.resolve("non-image.png").normalize();
            }
            resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;

            }else {
                throw new FileNotFoundException("Could not find file");
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }

    public WantedJobFile getWantedJobThumbnailEntity(WantedJobEntity wantedJob){
        return wantedJoFileRepository.findByWantedJobAndType(wantedJob, "thumbnail");
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Resource getWantedJobPdf(WantedJobEntity wantedJob) throws IOException {
        WantedJobFile pdf = wantedJoFileRepository.findByWantedJobAndType(wantedJob, "pdf");
        Path filePath = Paths.get(filepath);

        try {
            Path file = filePath.resolve(pdf.getFilename()).normalize();
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new FileNotFoundException("Could not find file");
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }

    public WantedJobFile getWantedPdfEntity(WantedJobEntity wantedJob){
        return wantedJoFileRepository.findByWantedJobAndType(wantedJob, "pdf");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
