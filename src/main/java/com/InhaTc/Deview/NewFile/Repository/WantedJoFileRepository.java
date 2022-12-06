package com.InhaTc.Deview.NewFile.Repository;

import com.InhaTc.Deview.NewFile.Entity.PortfolioFile;
import com.InhaTc.Deview.NewFile.Entity.WantedJobFile;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WantedJoFileRepository extends JpaRepository<WantedJobFile, Long> {
    List<WantedJobFile> findByWantedJob(WantedJobEntity wantedJob);

    WantedJobFile findByWantedJobAndType(WantedJobEntity wantedJob, String type);
}
