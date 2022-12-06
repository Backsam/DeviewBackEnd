package com.InhaTc.Deview.Apply.Repository;

import com.InhaTc.Deview.Apply.Entity.ApplyEntity;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<ApplyEntity, Long> {
    List<ApplyEntity> findByWjId (WantedJobEntity wjId);
}
