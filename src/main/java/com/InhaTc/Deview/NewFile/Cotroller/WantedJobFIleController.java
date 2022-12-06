package com.InhaTc.Deview.NewFile.Cotroller;

import com.InhaTc.Deview.NewFile.Entity.PortfolioFile;
import com.InhaTc.Deview.NewFile.Entity.WantedJobFile;
import com.InhaTc.Deview.NewFile.Service.WantedJobFileService;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import com.InhaTc.Deview.WantedJob.Sevice.WantedJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wantedjobfile")
@Slf4j
public class WantedJobFIleController {

    private final WantedJobFileService wantedJobFileService;
    private final WantedJobService wantedJobService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////// thumbnail
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("thumbnail")
    public ResponseEntity<?> uploadThumbnail(@RequestParam(value="file", required = false) MultipartFile files,
                                         @RequestParam(value="viewId", required = false) Long viewId,
                                         @RequestParam(value="view",required = false) String view) {
        Map<String, Object> result =new HashMap<>();
        WantedJobEntity wantedJob = wantedJobService.get(viewId);
        WantedJobFile wantedJobFile = WantedJobFile.builder()
                .originFileName(files.getOriginalFilename())
                .wantedJob(wantedJob)
                .type("thumbnail")
                .build();
        
        try {
            wantedJobFileService.saveFile(wantedJobFile, files);
            return  ResponseEntity.ok().body("Saved File");
        } catch (Exception e) {
            log.info(e.getMessage());
            result.put("data", "파일 업로드에 실패 하였습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("thumbnail")
    public ResponseEntity<?> updateThumbnail(@RequestParam(value="file", required = false) MultipartFile files,
                                             @RequestParam(value="viewId", required = false) Long viewId,
                                             @RequestParam(value="view",required = false) String view) {
        Map<String, Object> result =new HashMap<>();
        WantedJobEntity wantedJob = wantedJobService.get(viewId);
        WantedJobFile wantedJobThumbnailEntity = wantedJobFileService.getWantedJobThumbnailEntity(wantedJob);
        try {
                if(wantedJobThumbnailEntity != null){
                    wantedJobFileService.updateFile(wantedJobThumbnailEntity, files);
                }else{
                    WantedJobFile wantedJobFile = WantedJobFile.builder()
                            .originFileName(files.getOriginalFilename())
                            .wantedJob(wantedJob)
                            .type("thumbnail")
                            .build();
                    wantedJobFileService.saveFile(wantedJobFile, files);
                }
                return  ResponseEntity.ok().body("Updated File");
        } catch (Exception e) {
            log.info(e.getMessage());
            result.put("data", "파일 업로드에 실패 하였습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("thumbnail/{wjId}")
    public ResponseEntity<?> getThumbnail(@PathVariable long wjId,
                                          HttpServletRequest request) throws IOException {
        Resource resource;
        WantedJobEntity wantedJobEntity = wantedJobService.get(wjId);
        String originalFileName = wantedJobFileService.getWantedJobThumbnailEntity(wantedJobEntity).getOriginFileName();

        if(wantedJobEntity != null){
            resource = wantedJobFileService.getWantedThumbnail(wantedJobEntity);
        }else{
            log.info("undefined Portfolio");
            return ResponseEntity.badRequest().body("알수 없는 포트폴리오입니다.");
        }

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
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment;filename=\"%1$s\"" , URLEncoder.encode(originalFileName, StandardCharsets.UTF_8)))
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.getFile().length()))
                .body(resource);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////// PDF
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("pdf")
    public ResponseEntity<?> uploadPdf(@RequestParam(value="file", required = false) MultipartFile files,
                                             @RequestParam(value="viewId", required = false) Long viewId,
                                             @RequestParam(value="view",required = false) String view) {
        Map<String, Object> result =new HashMap<>();
        WantedJobEntity wantedJob = wantedJobService.get(viewId);
        WantedJobFile wantedJobFile = WantedJobFile.builder()
                .originFileName(files.getOriginalFilename())
                .wantedJob(wantedJob)
                .type("pdf")
                .build();

        log.info(">>Upload>>>>>>>>>>>>>>  "  + files.getOriginalFilename());
        try {
            wantedJobFileService.saveFile(wantedJobFile, files);
            return  ResponseEntity.ok().body("Saved Pdf");
        } catch (Exception e) {
            log.info(e.getMessage());
            result.put("data", "파일 업로드에 실패 하였습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("pdf")
    public ResponseEntity<?> updatePdf(@RequestParam(value="file", required = false) MultipartFile files,
                                             @RequestParam(value="viewId", required = false) Long viewId,
                                             @RequestParam(value="view",required = false) String view) {
        Map<String, Object> result =new HashMap<>();
        WantedJobEntity wantedJob = wantedJobService.get(viewId);
        WantedJobFile wantedJobPdfEntity = wantedJobFileService.getWantedPdfEntity(wantedJob);
        log.info(">>Update>>>>>>>>>>>>>>>>>>>>>>>>"  + files.getOriginalFilename());
        try {
            if(wantedJobPdfEntity != null){
                wantedJobFileService.updateFile(wantedJobPdfEntity, files);
            }else{
                WantedJobFile wantedJobFile = WantedJobFile.builder()
                        .originFileName(files.getOriginalFilename())
                        .wantedJob(wantedJob)
                        .type("pdf")
                        .build();
                wantedJobFileService.saveFile(wantedJobFile, files);
            }
            return  ResponseEntity.ok().body("Updated File");
        } catch (Exception e) {
            log.info(e.getMessage());
            result.put("data", "파일 업로드에 실패 하였습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("pdf/{wjId}")
    public ResponseEntity<?> getPdf(@PathVariable Long wjId,
                                    HttpServletRequest request) throws IOException {

        Resource resource;
        WantedJobEntity wantedJobEntity = wantedJobService.get(wjId);
        String originalFileName = wantedJobFileService.getWantedPdfEntity(wantedJobEntity).getOriginFileName();
        if(wantedJobEntity != null){
            resource = wantedJobFileService.getWantedJobPdf(wantedJobEntity);
        }else{
            log.info("undefined Portfolio");
            return ResponseEntity.badRequest().body("알수 없는 포트폴리오입니다.");
        }

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
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + originalFileName + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.getFile().length()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
                .body(resource);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////// images
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("documentimage")
    public ResponseEntity<?> uploadDocumentImg(@RequestParam(value="file", required = false) MultipartFile[] files,
                                             @RequestParam(value="viewId", required = false) Long viewId,
                                             @RequestParam(value="view",required = false) String view) {
        Map<String, Object> result =new HashMap<>();
        WantedJobEntity wantedJob = wantedJobService.get(viewId);
        try {

        for (MultipartFile file : files) {
            WantedJobFile wantedJobFile = WantedJobFile.builder()
                    .originFileName(file.getOriginalFilename())
                    .wantedJob(wantedJob)
                    .type("image")
                    .build();

            wantedJobFileService.saveFile(wantedJobFile, file);
        }
            return  ResponseEntity.ok().body("Saved images");
        } catch (Exception e) {
            log.info(e.getMessage());
            result.put("data", "파일 업로드에 실패 하였습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }
}
