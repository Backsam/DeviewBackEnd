package com.InhaTc.Deview.File.Service;

import com.InhaTc.Deview.File.Entity.FileEntity;
import com.InhaTc.Deview.File.Entity.FilePortfolioMapper;
import com.InhaTc.Deview.File.Entity.FileWantedJobMapper;
import com.InhaTc.Deview.File.Repository.FilePortfolioMapperRepository;
import com.InhaTc.Deview.File.Repository.FileRepository;
import com.InhaTc.Deview.File.Repository.FileWantedJobMapperRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@AllArgsConstructor
public class FileWantedJobService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileWantedJobMapperRepository fileWantedJobMapperRepository;

    public Resource getImage(String path, String fileName) throws IOException{
        Path filePath = Paths.get(path);

        try {
            Path file = filePath.resolve(fileName).normalize();
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

    public FileEntity uploadPdf(MultipartFile[] files, String filepath, long wjId){
        String FileNames = "";

        String originFileName = files[0].getOriginalFilename();
        long fileSize = files[0].getSize();
        String safeFile = System.currentTimeMillis() + originFileName;

        File f1 = new File(filepath + safeFile);
        try {
            files[0].transferTo(f1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final FileEntity file = FileEntity.builder()
                .filename(safeFile)
                .build();

        Long fid = fileRepository.save(file).getFid();
        log.info("create FileEntity is " + fid);

        final FileWantedJobMapper fileWantedJobMMapper = FileWantedJobMapper.builder()
                .fId(fid)
                .wjId(wjId)
                .build();
        Long fpmId = fileWantedJobMapperRepository.save(fileWantedJobMMapper).getId();
        log.info("create FilePortfolio is " + fpmId + ", fid : " + fid + " wjId : " + wjId);

        return file;
    }

    public Resource getPdf(String path, long wjId) throws IOException {
        long fId = fileWantedJobMapperRepository.findByWjId(wjId).get().getFId();
        String fileName = fileRepository.findByFid(fId).get().getFilename();
        Path filePath = Paths.get(path);
        log.info(filePath.toString());

        try {
            Path file = filePath.resolve(fileName).normalize();
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

}
