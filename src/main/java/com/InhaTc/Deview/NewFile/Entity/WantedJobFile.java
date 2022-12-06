package com.InhaTc.Deview.NewFile.Entity;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import lombok.*;

import javax.persistence.*;

@Data
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="wantedJob_file") // 테이블 명을 작성
public class WantedJobFile {
    @Id // primary key임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false, unique = true, length = 1000)
    private String filename;

    @Column(nullable = false, length = 1000)
    private String originFileName;

    @Column(nullable = false, length = 1000)
    private String filePath;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String created_dt;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "wj_id")
    private WantedJobEntity wantedJob;

    public void setWantedJob(WantedJobEntity wantedJob) {
        this.wantedJob = wantedJob;
    }

    private String type;

    public void updateFile(String oriImgName, String filename, String filePath){
        this.originFileName = oriImgName;
        this.filename = filename;
        this.filePath = filePath;
    }
}