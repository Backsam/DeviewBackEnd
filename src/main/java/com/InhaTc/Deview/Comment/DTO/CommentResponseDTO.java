package com.InhaTc.Deview.Comment.DTO;

import com.InhaTc.Deview.Comment.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentResponseDTO {

    private Long id;

    private Long pfId;

    private String userId;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    private boolean isWritten = false;

    public static Page<CommentResponseDTO> toResponseDtoPage(Page<Comment> commentPage, String userId){
        Page<CommentResponseDTO>  responseDTOPage = commentPage.map(
                comment -> {
                     String getUserId = comment.getUser().getUserId();

                     return CommentResponseDTO.builder()
                            .id(comment.getId())
                            .pfId(comment.getPortfolio().getPfId())
                            .userId(getUserId)
                            .content(comment.getContent())
                            .createDate(comment.getCreateDate())
                            .modifiedDate(comment.getModifiedDate())
                            .isWritten(getUserId.equals(userId))
                            .build();
                });

        return responseDTOPage;
    }
}
