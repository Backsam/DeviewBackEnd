package com.InhaTc.Deview.Profile.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDTO {
    private String introduce;

    private String field;

    private String techStack;

    private String website;

    private boolean owner;
}
