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
public class PortfolioListDTO {
    private Long pfId;

    private String title;

    private String userId;

    private String summary;

    private Type type;

    private String tags;

    private long likes;

    private long view;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    public static Page<PortfolioListDTO> toDtoPage(Page<PortfolioEntity> portfolioEntityPage) {
        Page<PortfolioListDTO> portfolioListDTOPage = portfolioEntityPage.map(entity -> PortfolioListDTO.builder()
                .pfId(entity.getPfId())
                .title(entity.getTitle())
                .userId(entity.getUserId())
                .summary(entity.getSummary())
                .type(entity.getType())
                .tags(entity.getTags())
                .likes(entity.getLikeCount())
                .view(entity.getView())
                .modifiedDate(entity.getModifiedDate())
                .createDate(entity.getCreateDate())
                .build());
        return portfolioListDTOPage;
    }

    public static List<PortfolioListDTO> toDtoList(List<PortfolioEntity> portfolioEntityList) {
        List<PortfolioListDTO> portfolioDTOList = new ArrayList<>();

        for (PortfolioEntity entity : portfolioEntityList) {
            portfolioDTOList.add(PortfolioListDTO.builder()
                    .pfId(entity.getPfId())
                    .title(entity.getTitle())
                    .userId(entity.getUserId())
                    .summary(entity.getSummary())
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
}
