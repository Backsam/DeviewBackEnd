package com.InhaTc.Deview.Apply.Entity;


import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyPortfolioMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "ap_id")
    private long id;

    @ManyToOne
    @JoinColumn(name ="apply_id")
    private ApplyEntity apply;

    @ManyToOne
    @JoinColumn(name ="pf_id")
    private PortfolioEntity portfolio;
}
