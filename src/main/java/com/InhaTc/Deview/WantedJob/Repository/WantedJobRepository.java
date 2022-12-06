package com.InhaTc.Deview.WantedJob.Repository;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WantedJobRepository extends JpaRepository<WantedJobEntity, Long> {
    WantedJobEntity findByWjId(Long wjId);

    boolean existsByWjId(Long wjId);

    Page<WantedJobEntity> findByTitleContainingOrTagsIn(Pageable pageable, String title, List<String> tag);

    Page<WantedJobEntity> findByTitleContaining(Pageable pageable, @Param("title")String title);

    @Query(value = "select DISTINCT w.* " +
            "from wanted_job w, tag_wanted_job_mapper tw, tag_entity t " +
            "where w.wj_id = tw.pf_id and tw.tag_tid = t.tid and (replace(w.title,' ','') like %:title% and t.content in :tag)", nativeQuery = true)
    Page<WantedJobEntity> getTitleAndTag(Pageable pageable, @Param("title")String title, @Param("tag")List<String> tag );
}
