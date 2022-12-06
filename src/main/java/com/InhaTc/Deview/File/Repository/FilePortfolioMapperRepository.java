package com.InhaTc.Deview.File.Repository;

import com.InhaTc.Deview.File.Entity.FilePortfolioMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilePortfolioMapperRepository extends JpaRepository<FilePortfolioMapper, Long> {

    Optional<FilePortfolioMapper> findByPfId(Long pfId);
}
