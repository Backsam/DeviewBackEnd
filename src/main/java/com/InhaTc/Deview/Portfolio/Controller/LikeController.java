package com.InhaTc.Deview.Portfolio.Controller;

import com.InhaTc.Deview.Common.ErrorCode;
import com.InhaTc.Deview.Portfolio.DTO.LikesDTO;
import com.InhaTc.Deview.Portfolio.Service.LikeService;
import com.InhaTc.Deview.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<?> like(@AuthenticationPrincipal CustomUserDetails user,
                                        @RequestBody @Validated LikesDTO likesDTO) {
        if(user != null){
            likeService.likes(likesDTO, user.getUsername());
            return new ResponseEntity<>(likesDTO, HttpStatus.CREATED);
        }else{
            return ResponseEntity.badRequest().body(ErrorCode.INVALID_JWT_TOKEN);
        }
    }

    @DeleteMapping
    public ResponseEntity<LikesDTO> unHeart(@AuthenticationPrincipal CustomUserDetails user,
                                            @RequestBody @Validated LikesDTO likesDTO) {
        likeService.unLikes(likesDTO,user.getUsername());
        return new ResponseEntity<>(likesDTO, HttpStatus.OK);
    }
}
