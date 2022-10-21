package com.InhaTc.Deview.Portfolio.Controller;

import com.InhaTc.Deview.Portfolio.DTO.PortfolioDTO;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
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
    public ResponseEntity<?> createPortfolio(@RequestBody PortfolioDTO dto){
        try {
            String userId = "testAccount";
            PortfolioEntity entity = PortfolioDTO.toEntity(dto);
            entity.setUser(userId);
            portfolioService.create(entity);
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }
}
