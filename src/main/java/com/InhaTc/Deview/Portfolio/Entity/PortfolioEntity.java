package com.InhaTc.Deview.Portfolio.Entity;

import com.InhaTc.Deview.Comment.Entity.Comment;
import com.InhaTc.Deview.Common.BaseTimeEntity;
import com.InhaTc.Deview.NewFile.Entity.PortfolioFile;
import com.InhaTc.Deview.Portfolio.Constant.Type;
import com.InhaTc.Deview.Tag.Entity.TagPortfolioMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "Portfolio")
public class PortfolioEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pfId;

    private String title;

    private String userId;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String tags;

    @JsonIgnore
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TagPortfolioMapper> tag = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Likes> likes = new ArrayList<>();

    @ColumnDefault("0")
    private Integer likeCount;

    @JsonIgnore
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PortfolioFile> files;

    private Long view;
}
