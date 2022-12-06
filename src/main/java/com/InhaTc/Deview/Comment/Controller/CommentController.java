package com.InhaTc.Deview.Comment.Controller;

import com.InhaTc.Deview.Comment.DTO.CommentRequestDTO;
import com.InhaTc.Deview.Comment.DTO.CommentResponseDTO;
import com.InhaTc.Deview.Comment.Entity.Comment;
import com.InhaTc.Deview.Comment.Service.CommentService;
import com.InhaTc.Deview.DTO.ResponseDTO;
import com.InhaTc.Deview.Security.AuthUser;
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
@RequestMapping("/comment")
public class CommentController{

    @Autowired
    private CommentService commentService;

    @PostMapping("/write/{pfId}")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal CustomUserDetails user,
                                           @PathVariable Long pfId,
                                           @RequestBody CommentRequestDTO requestDTO){
        if(user != null){
            Comment createComment = commentService.createComment(requestDTO, user.getUsername(), pfId);
            CommentResponseDTO responseDTO = Comment.toResponseDTO(createComment);
            return ResponseEntity.ok().body(responseDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Comment Write error")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/list/{pfId}")
    public ResponseEntity<?> getCommentList(@AuthUser UserEntity user,
                                            @PathVariable Long pfId,
                                            Pageable pageable){

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>> : " + user.getUserId());
        Page<Comment> commentPage = commentService.getCommentList(pageable, pfId);
        Page<CommentResponseDTO> responseDTOPage = CommentResponseDTO.toResponseDtoPage(commentPage, user.getUserId());
        return ResponseEntity.ok().body(responseDTOPage);
    }

    //DELETE
    @DeleteMapping("/delete/{pfId}/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal CustomUserDetails user,
                                        @PathVariable Long pfId,
                                        @PathVariable Long commentId,
                                        Pageable pageable) {
        if(user != null){
            commentService.deleteComments(commentId,user.getUsername());
            return ResponseEntity.ok().body(commentService.getCommentList(pageable, pfId));
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Comment Delete error")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    //UPDATE
    @PutMapping("/update/{pfId}/{commentsId}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal CustomUserDetails user,
                                           @PathVariable Long pfId,
                                           @PathVariable Long commentsId,
                                           @RequestBody CommentRequestDTO requestDTO,
                                           Pageable pageable) {

        if (user != null) {
            commentService.updateComment(requestDTO, user.getUsername(), commentsId);
            return ResponseEntity.ok().body(commentService.getCommentList(pageable, pfId));
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Comment Write error")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);

        }
    }
}
