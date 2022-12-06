package com.InhaTc.Deview.Tag.Repository;

import com.InhaTc.Deview.Tag.Entity.TagPortfolioMapper;
import com.InhaTc.Deview.Tag.Entity.TagWantedJobMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagWantedJobMapperRepository extends JpaRepository<TagWantedJobMapper, Long> {
}
