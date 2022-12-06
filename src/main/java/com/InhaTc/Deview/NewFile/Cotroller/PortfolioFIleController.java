package com.InhaTc.Deview.NewFile.Cotroller;

import com.InhaTc.Deview.NewFile.Entity.PortfolioFile;
import com.InhaTc.Deview.NewFile.Service.PortfolioFileService;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Service.PortfolioService;
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
@RequestMapping("/newfile")
@Slf4j
public class PortfolioFIleController {

    private final PortfolioFileService portfolioFileService;
    private final PortfolioService portfolioService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////// thumbnail
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("thumbnail")
    public ResponseEntity<?> uploadThumbnail(@RequestParam(value="file", required = false) MultipartFile files,
                                         @RequestParam(value="viewId", required = false) Long viewId,
                                         @RequestParam(value="view",required = false) String view) {
        Map<String, Object> result =new HashMap<>();
        PortfolioEntity portfolio = portfolioService.get(viewId);
        PortfolioFile portfolioFile = PortfolioFile.builder()
                .originFileName(files.getOriginalFilename())
                .portfolio(portfolio)
                .type("thumbnail")
                .build();
        
        try {
            portfolioFileService.saveFile(portfolioFile, files);
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
        PortfolioEntity portfolio = portfolioService.get(viewId);
        PortfolioFile portfolioThumbnailEntity = portfolioFileService.getPortfolioThumbnailEntity(portfolio);
        try {
                if(portfolioThumbnailEntity != null){
                    portfolioFileService.updateFile(portfolioThumbnailEntity, files);
                }else{
                    PortfolioFile portfolioFile = PortfolioFile.builder()
                            .originFileName(files.getOriginalFilename())
                            .portfolio(portfolio)
                            .type("thumbnail")
                            .build();
                    portfolioFileService.saveFile(portfolioFile, files);
                }
                return  ResponseEntity.ok().body("Updated File");
        } catch (Exception e) {
            log.info(e.getMessage());
            result.put("data", "파일 업로드에 실패 하였습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("thumbnail/{pfId}")
    public ResponseEntity<?> getThumbnail(@PathVariable long pfId,
                                          HttpServletRequest request) throws IOException {
        Resource resource;
        PortfolioEntity portfolioEntity = portfolioService.get(pfId);
        String originalFileName = portfolioFileService.getPortfolioThumbnailEntity(portfolioEntity).getOriginFileName();

        if(portfolioEntity != null){
            resource = portfolioFileService.getPortfolioThumbnail(portfolioEntity);
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
        PortfolioEntity portfolio = portfolioService.get(viewId);
        PortfolioFile portfolioFile = PortfolioFile.builder()
                .originFileName(files.getOriginalFilename())
                .portfolio(portfolio)
                .type("pdf")
                .build();

        log.info(">>Upload>>>>>>>>>>>>>>  "  + files.getOriginalFilename());
        try {
            portfolioFileService.saveFile(portfolioFile, files);
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
        PortfolioEntity portfolio = portfolioService.get(viewId);
        PortfolioFile portfolioPdfEntity = portfolioFileService.getPortfolioPdfEntity(portfolio);
        log.info(">>Update>>>>>>>>>>>>>>>>>>>>>>>>"  + files.getOriginalFilename());
        try {
            if(portfolioPdfEntity != null){
                portfolioFileService.updateFile(portfolioPdfEntity, files);
            }else{
                PortfolioFile portfolioFile = PortfolioFile.builder()
                        .originFileName(files.getOriginalFilename())
                        .portfolio(portfolio)
                        .type("pdf")
                        .build();
                portfolioFileService.saveFile(portfolioFile, files);
            }
            return  ResponseEntity.ok().body("Updated File");
        } catch (Exception e) {
            log.info(e.getMessage());
            result.put("data", "파일 업로드에 실패 하였습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("pdf/{pfId}")
    public ResponseEntity<?> getPdf(@PathVariable Long pfId,
                                    HttpServletRequest request) throws IOException {

        Resource resource;
        PortfolioEntity portfolioEntity = portfolioService.get(pfId);
        String originalFileName = portfolioFileService.getPortfolioPdfEntity(portfolioEntity).getOriginFileName();
        if(portfolioEntity != null){
            resource = portfolioFileService.getPortfolioPdf(portfolioEntity);
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
        PortfolioEntity portfolio = portfolioService.get(viewId);
        try {

        for (MultipartFile file : files) {
            PortfolioFile portfolioFile = PortfolioFile.builder()
                    .originFileName(file.getOriginalFilename())
                    .portfolio(portfolio)
                    .type("image")
                    .build();

            portfolioFileService.saveFile(portfolioFile, file);
        }
            return  ResponseEntity.ok().body("Saved images");
        } catch (Exception e) {
            log.info(e.getMessage());
            result.put("data", "파일 업로드에 실패 하였습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }
}
