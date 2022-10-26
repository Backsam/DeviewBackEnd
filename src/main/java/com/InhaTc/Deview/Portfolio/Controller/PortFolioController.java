package com.InhaTc.Deview.Portfolio.Controller;

import com.InhaTc.Deview.Portfolio.DTO.PortfolioDTO;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Service.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/portfolio")
public class PortFolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    public String test(){
        return "Hello";
    }

    @GetMapping("/list")
    public Page<PortfolioEntity> getPortfolioList(@PageableDefault(size = 9) Pageable pageable){
        return portfolioService.getList(pageable);
    }

    @PostMapping("/write")
    public ResponseEntity<?> createPortfolio(   @AuthenticationPrincipal String userId,
                                                @RequestBody PortfolioDTO dto){
        try {
            PortfolioEntity entity = PortfolioDTO.toEntity(dto);
            entity.setUserId(userId);
            portfolioService.create(entity);
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{num}")
    public ResponseEntity<?> readPortfolio(@PathVariable Long num){
        PortfolioEntity entity = portfolioService.get(num);
        return ResponseEntity.ok().body(entity);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updatePortfolio(@AuthenticationPrincipal String userId,
                                             @RequestBody PortfolioDTO dto){
        try {
        PortfolioEntity entity = PortfolioDTO.toEntity(dto);
        entity.setUserId(userId);
        portfolioService.update(entity);
        return ResponseEntity.ok().body(entity);
            }catch (Exception e){
             String error = e.getMessage();
             return ResponseEntity.badRequest().body(error);
    }
    }

    @DeleteMapping
    ResponseEntity<?> deletePortfolio(@AuthenticationPrincipal String userId,
                                      @RequestBody PortfolioDTO dto){
        try{
            PortfolioEntity entity = PortfolioDTO.toEntity(dto);
            entity.setUserId(userId);
            portfolioService.delete(entity);
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }
}
