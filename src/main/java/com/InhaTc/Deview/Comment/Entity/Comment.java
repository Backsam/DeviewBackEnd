package com.InhaTc.Deview.Comment.Entity;

import com.InhaTc.Deview.Comment.DTO.CommentRequestDTO;
import com.InhaTc.Deview.Comment.DTO.CommentResponseDTO;
import com.InhaTc.Deview.Common.BaseTimeEntity;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.User.Entitiy.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @JsonIgnore
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "pf_id")
    private PortfolioEntity portfolio;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private UserEntity user;

    @Column(columnDefinition = "TEXT")
    private String content;

    public static CommentResponseDTO toResponseDTO(Comment entity){
        return CommentResponseDTO.builder()
                .id(entity.getId())
                .pfId(entity.getPortfolio().getPfId())
                .userId(entity.getUser().getUserId())
                .content(entity.getContent())
                .createDate(entity.getCreateDate())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }
}

