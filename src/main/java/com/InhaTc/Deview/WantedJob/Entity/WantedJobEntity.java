package com.InhaTc.Deview.WantedJob.Entity;

import com.InhaTc.Deview.Common.BaseTimeEntity;
import com.InhaTc.Deview.NewFile.Entity.PortfolioFile;
import com.InhaTc.Deview.NewFile.Entity.WantedJobFile;
import com.InhaTc.Deview.Portfolio.Constant.Type;
import com.InhaTc.Deview.Tag.Entity.TagWantedJobMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WantedJob")
public class WantedJobEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wjId;

    private String title;

    private String userId;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
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

    @JsonIgnore
    @OneToMany(mappedBy = "wantedJob", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TagWantedJobMapper> tag;

    @JsonIgnore
    @OneToMany(mappedBy = "wantedJob", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WantedJobFile> files;
}
