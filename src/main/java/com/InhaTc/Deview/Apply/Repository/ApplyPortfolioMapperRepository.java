package com.InhaTc.Deview.Apply.Repository;

import com.InhaTc.Deview.Apply.Entity.ApplyEntity;
import com.InhaTc.Deview.Apply.Entity.ApplyPortfolioMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyPortfolioMapperRepository extends JpaRepository<ApplyPortfolioMapper, Long> {

    List<ApplyPortfolioMapper> findByApply(ApplyEntity entity);
}
