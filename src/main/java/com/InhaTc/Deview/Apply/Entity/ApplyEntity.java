package com.InhaTc.Deview.Apply.Entity;

import com.InhaTc.Deview.Common.BaseTimeEntity;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "apply")
public class ApplyEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "apply_id")
    private long id;

    private String userId;

    @OneToOne
    @JoinColumn(name = "wjId")
    private WantedJobEntity wjId;

    private String part;

    private String career;

    private String education;

    private String answer0;

    private String answer1;

    private String answer2;

    @Override
    public LocalDateTime getModifiedDate() {
        return super.getModifiedDate();
    }
}
