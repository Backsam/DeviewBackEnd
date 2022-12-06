package com.InhaTc.Deview.File.Repository;

import com.InhaTc.Deview.File.Entity.FilePortfolioMapper;
import com.InhaTc.Deview.File.Entity.FileWantedJobMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileWantedJobMapperRepository extends JpaRepository<FileWantedJobMapper, Long> {

    Optional<FileWantedJobMapper> findByWjId(Long pfId);
}
