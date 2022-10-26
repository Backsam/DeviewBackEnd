package com.InhaTc.Deview.Portfolio.DTO;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PortfolioDTO {
    private Long pfId;
    private String title;
    private String userId;
    private String summary;
    private String content;

    private String tags;
    private long view;

    public PortfolioDTO(final PortfolioEntity entity){
        this.pfId = entity.getPfId();
        this.title = entity.getTitle();
        this.userId = entity.getUserId();
        this.summary = entity.getSummary();
        this.tags = entity.getTags();
        this.content = entity.getContent();
        this.view = entity.getView();
    }



    public static PortfolioEntity toEntity(final PortfolioDTO dto){

        if(dto.getPfId() == null){
            return PortfolioEntity.builder()
                    .title(dto.getTitle())
                    .userId(dto.getUserId())
                    .summary(dto.getSummary())
                    .content(dto.getContent())
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
                    .tags(dto.getTags())
                    .view(dto.getView())
                    .build();
        }
    }
}
