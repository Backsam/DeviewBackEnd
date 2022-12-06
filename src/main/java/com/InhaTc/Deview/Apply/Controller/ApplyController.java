package com.InhaTc.Deview.Apply.Controller;

import com.InhaTc.Deview.Apply.DTO.ApplyDTO;
import com.InhaTc.Deview.Apply.Entity.ApplyEntity;
import com.InhaTc.Deview.Apply.Entity.ApplyPortfolioMapper;
import com.InhaTc.Deview.Apply.Repository.ApplyPortfolioMapperRepository;
import com.InhaTc.Deview.Apply.Service.ApplyService;
import com.InhaTc.Deview.Portfolio.Service.PortfolioService;
import com.InhaTc.Deview.Security.CustomUserDetails;
import com.InhaTc.Deview.WantedJob.Sevice.WantedJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apply")
@Slf4j
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private WantedJobService wantedJobService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private ApplyPortfolioMapperRepository applyPortfolioMapperRepository;

    @PostMapping("/write")
    public ResponseEntity<?> createApply(@AuthenticationPrincipal CustomUserDetails user,
                                         @RequestBody ApplyDTO dto) {
        try {
            log.info(dto.toString());
            ApplyEntity entity = ApplyDTO.toEntity(dto);
            entity.setUserId(user.getUsername());
            entity.setWjId(wantedJobService.get(dto.getWjId()));
            ApplyEntity savedEntity = applyService.create(entity);

            if (dto.getPfIds().length > 0) {
                for (String pfId : dto.getPfIds()) {
                    ApplyPortfolioMapper apMapper = ApplyPortfolioMapper.builder()
                            .apply(savedEntity)
                            .portfolio(portfolioService.get(Long.parseLong(pfId)))
                            .build();

                    applyPortfolioMapperRepository.save(apMapper);
                }
            }

            return ResponseEntity.ok().body(entity);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/list/{wjId}")
    public ResponseEntity<?> getApplyList(@PathVariable Long wjId) {
        try {
            List<ApplyEntity> applyList = applyService.getList(wjId);
            List<ApplyDTO> responseDTO = new ArrayList<>();
            for (ApplyEntity entity : applyList) {
                ApplyDTO dto = ApplyDTO.builder()
                        .id(entity.getId())
                        .userId(entity.getUserId())
                        .part(entity.getPart())
                        .career(entity.getCareer())
                        .education(entity.getEducation())
                        .modifiedDate(entity.getModifiedDate())
                        .build();

                responseDTO.add(dto);
            }
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/view/{apId}")
    public ResponseEntity<?> getApply(@PathVariable Long apId){
        ApplyEntity entity = applyService.get(apId);

        List<ApplyPortfolioMapper> list = applyPortfolioMapperRepository.findByApply(entity);

        String[] pfTitles = new String[list.size()];
        String[] pfIds = new String[list.size()];

        if(list.size() > 0){
            int i = 0;
            for (ApplyPortfolioMapper apMapper : list) {
                pfIds[i] = String.valueOf(apMapper.getPortfolio().getPfId());
                pfTitles[i] = apMapper.getPortfolio().getTitle();
                i++;
            }
        }

        ApplyDTO responseDTO = ApplyDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .part(entity.getPart())
                .career(entity.getCareer())
                .education(entity.getEducation())
                .question0(entity.getWjId().getQuestion0())
                .question1(entity.getWjId().getQuestion1())
                .question2(entity.getWjId().getQuestion2())
                .answer0(entity.getAnswer0())
                .answer1(entity.getAnswer1())
                .answer2(entity.getAnswer2())
                .modifiedDate(entity.getModifiedDate())
                .pfIds(pfIds)
                .pfTitles(pfTitles)
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }
}
