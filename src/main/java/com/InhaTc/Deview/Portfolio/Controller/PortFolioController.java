package com.InhaTc.Deview.Portfolio.Controller;

import com.InhaTc.Deview.Portfolio.DTO.PortfolioDTO;
import com.InhaTc.Deview.Portfolio.DTO.PortfolioListDTO;
import com.InhaTc.Deview.Portfolio.Entity.Likes;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Service.PortfolioService;
import com.InhaTc.Deview.Security.CustomUserDetails;
import com.InhaTc.Deview.User.Entitiy.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<PortfolioListDTO> getPortfolioList(Pageable pageable){
        Page<PortfolioEntity> entityPage = portfolioService.getList(pageable);
        Page<PortfolioListDTO> dtoPage = PortfolioListDTO.toDtoPage(entityPage);
        return dtoPage;
    }

    @GetMapping("/list/apply")
    public Page<PortfolioListDTO> getApplyPortfolioList( Pageable pageable,
                                                        @AuthenticationPrincipal CustomUserDetails user){
        if(user != null){
            Page<PortfolioEntity> entityPage = portfolioService.getList(pageable, user.getUsername());
            Page<PortfolioListDTO> dtoPage = PortfolioListDTO.toDtoPage(entityPage);
            return dtoPage;
        }else{
            return null;
        }
    }

    @GetMapping("/list/{userId}")
    public Page<PortfolioListDTO> getUserPortfolioList( Pageable pageable,
                                                        @PathVariable String userId) {
        if(userId != null){
            Page<PortfolioEntity> entityPage = portfolioService.getList(pageable, userId);
            Page<PortfolioListDTO> dtoPage = PortfolioListDTO.toDtoPage(entityPage);
            return dtoPage;
        }else{
            throw new RuntimeException("유저 아이디가 없습니다.");
        }
    }

    @GetMapping("/list/top")
    public List<PortfolioListDTO> getLikeTopPortfolio(){
        List<PortfolioEntity> entityList = portfolioService.getTopLikeList();
        List<PortfolioListDTO> dtoList = PortfolioListDTO.toDtoList(entityList);
        return dtoList;
    }

    @PostMapping("/write")
    public ResponseEntity<?> createPortfolio(@AuthenticationPrincipal CustomUserDetails user,
                                             @RequestBody PortfolioDTO dto){
        try {
            PortfolioEntity entity = PortfolioDTO.toEntity(dto);
            entity.setUserId(user.getUsername());
            portfolioService.create(entity);
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }
    @GetMapping("/{num}")
    public ResponseEntity<?> readPortfolio(@AuthenticationPrincipal CustomUserDetails user,
                                           @PathVariable Long num){

        PortfolioEntity entity = portfolioService.get(num);
        PortfolioDTO dto = new PortfolioDTO(entity);

        if(user != null){
            if(entity.getUserId().equals(user.getUsername())){
                dto.setWritten(true);
            }
            for (Likes like: entity.getLikes()) {
                if(like.getUser().getUserId().equals(user.getUsername())){
                    dto.setAlreadyLike(true);
                }
            }
        }

        return ResponseEntity.ok().body(dto);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updatePortfolio(@AuthenticationPrincipal CustomUserDetails user,
                                             @RequestBody PortfolioDTO dto){
        try {
        PortfolioEntity entity = PortfolioDTO.toEntity(dto);
        entity.setUserId(user.getUsername());
        portfolioService.update(entity);
        return ResponseEntity.ok().body(entity);
            }catch (Exception e){
             String error = e.getMessage();
             return ResponseEntity.badRequest().body(error);
        }
    }
    @DeleteMapping
    ResponseEntity<?> deletePortfolio(@AuthenticationPrincipal CustomUserDetails user,
                                      @RequestBody PortfolioDTO dto){

        try{
            PortfolioEntity entity = PortfolioDTO.toEntity(dto);
            entity.setUserId(user.getUsername());
            portfolioService.delete(entity);
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }
}
