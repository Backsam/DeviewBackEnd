package com.InhaTc.Deview.NewFile.Service;

import com.InhaTc.Deview.NewFile.Entity.PortfolioFile;
import com.InhaTc.Deview.NewFile.Repository.PortfolioFileRepository;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import lombok.RequiredArgsConstructor;
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
public class PortfolioFileService {

    private String filepath ="E:/project/Deview/back/src/main/resources/static/PortfolioFile";

    private final NewFileService newFileService;

    private final PortfolioFileRepository portfolioFileRepository;

    public void saveFile(PortfolioFile portfolioFile, MultipartFile file) throws Exception{
        String oriFileName = portfolioFile.getOriginFileName();
        String fileName = "";
        String fileUrl = "";

        //파일 업로드
        if(!oriFileName.isEmpty()){
            fileName = newFileService.uploadFile(filepath, oriFileName,
                    file.getBytes());
            fileUrl = "/portfolio/" + fileName;
        }

        //상품 이미지 정보 저장
        portfolioFile.updateFile(oriFileName, fileName, fileUrl);
        portfolioFileRepository.save(portfolioFile);
    }

    public void updateFile(PortfolioFile portfolioFile, MultipartFile file) throws Exception{
        if(!file.isEmpty()){
            //기존 이미지 파일 삭제
            if(!portfolioFile.getFilename().isEmpty()) {
                newFileService.deleteFile(filepath +
                        portfolioFile.getFilename());
            }

            String oriFileName = file.getOriginalFilename();
            String fileName = newFileService.uploadFile(filepath, oriFileName, file.getBytes());
            String fileUrl = "/portfolio/" + fileName;

            portfolioFile.updateFile(oriFileName, fileName, fileUrl);

            portfolioFileRepository.save(portfolioFile);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Resource getPortfolioThumbnail(PortfolioEntity portfolio) throws IOException {
        PortfolioFile thumbnail = portfolioFileRepository.findByPortfolioAndType(portfolio, "thumbnail");
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

    public PortfolioFile getPortfolioThumbnailEntity(PortfolioEntity portfolio){
        return portfolioFileRepository.findByPortfolioAndType(portfolio, "thumbnail");
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Resource getPortfolioPdf(PortfolioEntity portfolio) throws IOException {
        PortfolioFile pdf = portfolioFileRepository.findByPortfolioAndType(portfolio, "pdf");
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

    public PortfolioFile getPortfolioPdfEntity(PortfolioEntity portfolio){
        return portfolioFileRepository.findByPortfolioAndType(portfolio, "pdf");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public List<PortfolioFile> getPortfolioFileList(PortfolioEntity portfolio){
        List<PortfolioFile> portfolioFiles = portfolioFileRepository.findByPortfolio(portfolio);
        if(portfolioFiles != null) {
            return portfolioFiles;
        }else{
            throw new EntityNotFoundException();
        }
    }
}
