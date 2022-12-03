package com.InhaTc.Deview.Portfolio.DTO;

import com.InhaTc.Deview.Comment.Entity.Comment;
import com.InhaTc.Deview.Portfolio.Constant.Type;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PortfolioDTO {
    private Long pfId;
    private String title;
    private String userId;
    private String summary;
    private String content;
    private Type type;
    private String tags;
    private long likes;

    private boolean alreadyLike = false;

    private boolean isWritten = false;
    private long view;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public PortfolioDTO(final PortfolioEntity entity){
        this.pfId = entity.getPfId();
        this.title = entity.getTitle();
        this.userId = entity.getUserId();
        this.summary = entity.getSummary();
        this.type = entity.getType();
        this.tags = entity.getTags();
        this.content = entity.getContent();
        this.likes = entity.getLikeCount();
        this.view = entity.getView();
        this.createDate = entity.getCreateDate();
        this.modifiedDate = entity.getModifiedDate();
    }

    public static Page<PortfolioDTO> toDtoPage(Page<PortfolioEntity> portfolioEntityPage) {
        Page<PortfolioDTO> portfolioDTOPage = portfolioEntityPage.map(entity -> PortfolioDTO.builder()
                .pfId(entity.getPfId())
                .title(entity.getTitle())
                .userId(entity.getUserId())
                .summary(entity.getSummary())
                .content(entity.getContent())
                .type(entity.getType())
                .tags(entity.getTags())
                .likes(entity.getLikeCount())
                .view(entity.getView())
                .modifiedDate(entity.getModifiedDate())
                .createDate(entity.getCreateDate())
                .build());
        return portfolioDTOPage;
    }

    public static List<PortfolioDTO> toDtoList(List<PortfolioEntity> portfolioEntityList) {
        List<PortfolioDTO> portfolioDTOList = new ArrayList<>();

        for (PortfolioEntity entity : portfolioEntityList) {
            portfolioDTOList.add(PortfolioDTO.builder()
                    .pfId(entity.getPfId())
                    .title(entity.getTitle())
                    .userId(entity.getUserId())
                    .summary(entity.getSummary())
                    .content(entity.getContent())
                    .type(entity.getType())
                    .tags(entity.getTags())
                    .likes(entity.getLikeCount())
                    .view(entity.getView())
                    .modifiedDate(entity.getModifiedDate())
                    .createDate(entity.getCreateDate())
                    .build());
        }

        return portfolioDTOList;
    }



    public static PortfolioEntity toEntity(final PortfolioDTO dto){

        if(dto.getPfId() == null){
            return PortfolioEntity.builder()
                    .title(dto.getTitle())
                    .userId(dto.getUserId())
                    .summary(dto.getSummary())
                    .content(dto.getContent())
                    .type(dto.getType())
                    .tags(dto.getTags())
                    .view(dto.getView())
                    .build();
        }else{
            return PortfolioEntity.builder()
                    .pfId(dto.getPfId())
                    .title(dto.getTitle())
                    .userId(dto.getUserId())
                    .summary(dto.getSummary())
                    .content(dto.getContent())
                    .type(dto.getType())
                    .tags(dto.getTags())
                    .view(dto.getView())
                    .build();
        }
    }

    public void updateView(Long updateView){
        this.view = updateView;
    }
}
