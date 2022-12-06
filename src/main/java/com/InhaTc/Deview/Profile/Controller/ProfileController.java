package com.InhaTc.Deview.Profile.Controller;

import com.InhaTc.Deview.Profile.DTO.ProfileDTO;
import com.InhaTc.Deview.Profile.Entity.Profile;
import com.InhaTc.Deview.Profile.Service.ProfileService;
import com.InhaTc.Deview.Security.CustomUserDetails;
import com.InhaTc.Deview.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails user,
                                        @PathVariable String userId){
        Map<String, Object> result =new HashMap<>();
        try {
            Profile profile = profileService.getProfile(userId);
            ProfileDTO dto = ProfileDTO.builder()
                    .introduce(profile.getIntroduce())
                    .field(profile.getField())
                    .techStack(profile.getTechStack())
                    .website(profile.getWebsite())
                    .build();

            if(profile.getUser().getUserId().equals(user.getUsername())){
                dto.setOwner(true);
            }else{
                dto.setOwner(false);
            }

            return ResponseEntity.ok().body(dto);
        }catch (Exception e){
            log.info(e.getMessage());
            result.put("error", e.getMessage());
            result.put("data", "프로필 조회에 실패했습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal CustomUserDetails user,
                                           @RequestBody ProfileDTO dto){
        Map<String, Object> result =new HashMap<>();
        try {
            profileService.updateProfile(dto, user.getUsername());

            return ResponseEntity.ok().body("프로필이 수정되었습니다.");
        }catch (Exception e){
            log.info(e.getMessage());
            result.put("error", e.getMessage());
            result.put("data", "프로필 수정에 실패했습니다.");
            return ResponseEntity.badRequest().body(result);
        }
    }
}
