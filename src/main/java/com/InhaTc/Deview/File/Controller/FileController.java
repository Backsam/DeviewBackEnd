package com.InhaTc.Deview.File.Controller;

import com.InhaTc.Deview.File.DTO.FileDTO;
import com.InhaTc.Deview.File.Entity.FileEntity;
import com.InhaTc.Deview.File.Repository.FileRepository;
import com.InhaTc.Deview.File.Service.FileService;
import com.InhaTc.Deview.File.Service.FileWantedJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Slf4j
public class FileController {
    private final FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileWantedJobService fileWantedJobService;

    String filepath ="E:/project/Deview/back/src/main/resources/static/Images/";

    @PostMapping("image")
    public ResponseEntity<?> uploadImage(@RequestParam(value="file", required = false) MultipartFile[] files,
                                        @RequestParam(value="viewId", required = false) Long viewId,
                                         @RequestParam(value="view",required = false) String view) {
        String originFileName = files[0].getOriginalFilename();
        long fileSize = files[0].getSize();
        String safeFile="";

        if(viewId == null && view == null){
           safeFile = System.currentTimeMillis() + originFileName;
        }else{
            safeFile = view + viewId + "_thumbnail";
        }

        File f1 = new File(filepath + safeFile);
        try {
            files[0].transferTo(f1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final FileEntity file = FileEntity.builder()
                .filename(safeFile)
                .build();

        FileEntity save = fileRepository.save(file);

        final FileDTO fileDTO = FileDTO.builder()
                .filename(save.getFilename())
                .filepath(filepath)
                .build();

        return ResponseEntity.ok().body(fileDTO);
    }

    @GetMapping("image/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName,
                                    HttpServletRequest request) throws IOException {
        Resource resource = fileService.getImage(filepath, fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.getFile().length()))
                .body(resource);
    }


    @PostMapping("pdf/upload")
    public ResponseEntity<?> uploadPdf(@RequestParam(value="pdf", required = false) MultipartFile[] files,
                                       @RequestParam(value="viewId", required = false) Long pfId) {
        FileEntity file = fileService.uploadPdf(files, filepath, pfId);

        final FileDTO fileDTO = FileDTO.builder()
                .filename(file.getFilename())
                .filepath(filepath)
                .build();

        return ResponseEntity.ok().body(fileDTO);
    }



    @GetMapping("pdf/read/{pfId}")
    public ResponseEntity<?> getPdf(@PathVariable Long pfId,
                                     HttpServletRequest request) throws IOException {

        Resource resource = fileService.getPdf(filepath,pfId);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.getFile().length()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
                .body(resource);
    }

    @GetMapping("pdf/download/{pfId}")
    public ResponseEntity<?> getPdfData(@PathVariable Long pfId,
                                    HttpServletRequest request) throws IOException {

        Resource resource = fileService.getPdf(filepath,pfId);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.getFile().length()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
                .body(resource);
    }


    @PostMapping("wanted/job/pdf/upload")
    public ResponseEntity<?> uploadWantedJobPdf(@RequestParam(value="pdf", required = false) MultipartFile[] files,
                                       @RequestParam(value="viewId", required = false) Long wjId) {
        FileEntity file = fileWantedJobService.uploadPdf(files, filepath, wjId);

        final FileDTO fileDTO = FileDTO.builder()
                .filename(file.getFilename())
                .filepath(filepath)
                .build();

        return ResponseEntity.ok().body(fileDTO);
    }

    @GetMapping("wanted/job/pdf/read/{wjId}")
    public ResponseEntity<?> getWantedJobPdf(@PathVariable Long wjId,
                                    HttpServletRequest request) throws IOException {
        Resource resource = fileWantedJobService.getPdf(filepath,wjId);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.getFile().length()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
                .body(resource);
    }


}