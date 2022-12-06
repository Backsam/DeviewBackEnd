package com.InhaTc.Deview.WantedJob.DTO;

import com.InhaTc.Deview.Portfolio.Constant.Type;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WantedJobDTO {
    private Long wjId;

    private String title;

    private String userId;

    private Type type;

    private String summary;

    private String content;

    private String tags;

    private String personnel;

    private String part;

    private String education;

    private String career;

    private String pay;

    private String workingTime;

    private String employmentType;

    private String area;

    private String question0;

    private String question1;

    private String question2;

    private long view;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;
    private boolean Written;

    public WantedJobDTO (final WantedJobEntity entity){
        this.wjId = entity.getWjId();
        this.title = entity.getTitle();
        this.userId = entity.getUserId();
        this.type = entity.getType();
        this.summary = entity.getSummary();
        this.content = entity.getContent();
        this.tags = entity.getTags();
        this.personnel  = entity.getPersonnel();
        this.part = entity.getPart();
        this.education = entity.getEducation();
        this.career = entity.getCareer();
        this.pay = entity.getPay();
        this.workingTime = entity.getWorkingTime();
        this.employmentType = entity.getEmploymentType();
        this.area = entity.getArea();
        this.question0 =entity.getQuestion0();
        this.question1 = entity.getQuestion1();
        this.question2 = entity.getQuestion2();
        this.createDate = entity.getCreateDate();
        this.modifiedDate = entity.getModifiedDate();
        this.view = entity.getView();
        this.Written = false;
    }

    public static WantedJobEntity toEntity(final WantedJobDTO  dto){

        if(dto.getWjId() == null) {
            return WantedJobEntity.builder()
                    .title(dto.getTitle())
                    .userId(dto.getUserId())
                    .summary(dto.getSummary())
                    .content(dto.getContent())
                    .type(dto.getType())
                    .tags(dto.getTags())
                    .personnel(dto.getPersonnel())
                    .part(dto.getPart())
                    .education(dto.getEducation())
                    .career(dto.getCareer())
                    .pay(dto.getPay())
                    .workingTime(dto.getWorkingTime())
                    .employmentType(dto.getEmploymentType())
                    .area(dto.getArea())
                    .question0(dto.question0)
                    .question1(dto.question1)
                    .question2(dto.question2)
                    .view(dto.getView())
                    .build();
        }else{
            return WantedJobEntity.builder()
                    .wjId(dto.getWjId())
                    .title(dto.getTitle())
                    .userId(dto.getUserId())
                    .summary(dto.getSummary())
                    .content(dto.getContent())
                    .type(dto.getType())
                    .tags(dto.getTags())
                    .personnel(dto.getPersonnel())
                    .part(dto.getPart())
                    .education(dto.getEducation())
                    .career(dto.getCareer())
                    .pay(dto.getPay())
                    .workingTime(dto.getWorkingTime())
                    .employmentType(dto.getEmploymentType())
                    .area(dto.getArea())
                    .question0(dto.question0)
                    .question1(dto.question1)
                    .question2(dto.question2)
                    .view(dto.getView())
                    .build();
        }
    }
}
