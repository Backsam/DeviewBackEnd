package com.InhaTc.Deview.Tag.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TagPortfolioMapping {
    private Long tid;
    private Long pfId;
    private String content;


    public TagPortfolioMapping(Long tid, Long pfid){
        this.tid = tid;
        this.pfId =pfid;
    }
}
