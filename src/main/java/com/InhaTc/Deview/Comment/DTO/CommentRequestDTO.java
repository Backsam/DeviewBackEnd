package com.InhaTc.Deview.Comment.DTO;

import com.InhaTc.Deview.Comment.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequestDTO {

    private String userId;

    private String content;


    public Comment toEntity(){
        return Comment.builder()
                .content(this.getContent())
                .build();
    }
}

