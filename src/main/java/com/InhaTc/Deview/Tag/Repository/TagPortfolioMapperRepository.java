package com.InhaTc.Deview.Tag.Repository;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Tag.Entity.TagPortfolioMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagPortfolioMapperRepository extends JpaRepository<TagPortfolioMapper, Long> {

    List<TagPortfolioMapper> findByPortfolio(PortfolioEntity portfolio);
}
