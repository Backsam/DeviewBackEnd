package com.InhaTc.Deview.Search.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchDTO {

    private String keyword;

    private String type;

    private String[] tags;
}
