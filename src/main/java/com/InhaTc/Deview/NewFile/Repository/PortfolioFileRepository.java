package com.InhaTc.Deview.NewFile.Repository;

import com.InhaTc.Deview.NewFile.Entity.PortfolioFile;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioFileRepository extends JpaRepository<PortfolioFile, Long> {
    List<PortfolioFile> findByPortfolio(PortfolioEntity portfolio);

    PortfolioFile findByPortfolioAndType(PortfolioEntity portfolio, String type);
}
