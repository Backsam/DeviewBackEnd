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
    private String user;
    private String summary;
    private String content;
    private int view;

    public PortfolioDTO(final PortfolioEntity entity){
        this.pfId = entity.getPfId();
        this.title = entity.getTitle();
        this.user = entity.getUser();
        this.summary = entity.getSummary();
        this.content = entity.getContent();
        this.view = entity.getView();
    }

    public static PortfolioEntity toEntity(final PortfolioDTO dto){
        return PortfolioEntity.builder()
                .title(dto.getTitle())
                .user(dto.getUser())
                .summary(dto.getSummary())
                .content(dto.getContent())
                .view(dto.getView())
                .build();
    }
}
