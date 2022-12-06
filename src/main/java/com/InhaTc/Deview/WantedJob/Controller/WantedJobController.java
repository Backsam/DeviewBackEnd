package com.InhaTc.Deview.WantedJob.Controller;

import com.InhaTc.Deview.Security.CustomUserDetails;
import com.InhaTc.Deview.WantedJob.DTO.WantedJobDTO;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import com.InhaTc.Deview.WantedJob.Sevice.WantedJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/wanted")
public class WantedJobController {

    @Autowired
    private WantedJobService wantedJobService;

    @GetMapping("/list")
    public Page<WantedJobEntity> getWantedJobList(@PageableDefault(size = 9) Pageable pageable){
        return wantedJobService.getList(pageable);
    }

    @GetMapping("/job/{wjId}")
    public ResponseEntity<?> readPortfolio( @AuthenticationPrincipal CustomUserDetails user,
                                            @PathVariable Long wjId) {
        WantedJobEntity entity = wantedJobService.get(wjId);
        boolean isWritten = false;
        if (user != null && entity.getUserId().equals(user.getUsername())){
            isWritten = true;
        }

        WantedJobDTO responseDTO = new WantedJobDTO(entity);
        responseDTO.setWritten(isWritten);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/write")
    public ResponseEntity<?> createWantedJob(@AuthenticationPrincipal CustomUserDetails user,
                                             @RequestBody WantedJobDTO dto){
        try {
            WantedJobEntity entity = WantedJobDTO.toEntity(dto);
            entity.setUserId(user.getUsername());
            wantedJobService.create(entity);
            return ResponseEntity.ok().body(entity);
        }catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }
}
