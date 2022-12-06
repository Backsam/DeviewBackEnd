package com.InhaTc.Deview.Search.DTO;

import com.InhaTc.Deview.Portfolio.Constant.Type;
import com.InhaTc.Deview.Portfolio.DTO.PortfolioDTO;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.WantedJob.DTO.WantedJobDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchResultDTO {
    private Long id;
    private String title;
    private String userId;
    private String summary;
    private String tags;
    private long likes;
    private long view;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
}
