package com.InhaTc.Deview.Portfolio.Repository;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, String> {

    PortfolioEntity findByPfId(Long pfId);
}
