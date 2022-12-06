package com.InhaTc.Deview.Apply.DTO;

import com.InhaTc.Deview.Apply.Entity.ApplyEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class ApplyDTO {
    private long id;

    private String userId;

    private long wjId;

    private String part;

    private String career;

    private String education;

    private String question0;

    private String question1;

    private String question2;

    private String answer0;

    private String answer1;

    private String answer2;

    private String[] pfIds;

    private String[] pfTitles;

    private String createDate;

    private LocalDateTime modifiedDate;

    public static ApplyEntity toEntity(ApplyDTO dto){
        return ApplyEntity.builder()
                .userId(dto.getUserId())
                .part(dto.getPart())
                .career(dto.getCareer())
                .education(dto.getEducation())
                .answer0(dto.getAnswer0())
                .answer1(dto.getAnswer1())
                .answer2(dto.getAnswer2())
                .build();
    }
}
