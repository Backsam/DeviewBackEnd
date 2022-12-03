package com.InhaTc.Deview.Portfolio.Repository;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, String> {

    PortfolioEntity findByUserId(String userId);

    Page<PortfolioEntity> findByUserId(Pageable pageable, String userId);
    Boolean existsByPfId(Long pfId);

    PortfolioEntity findByPfId(Long pfId);

    @Transactional
    @Modifying
    @Query("update PortfolioEntity p set p.view = p.view + 1 where p.pfId = :pfId")
    int updateView(@Param("pfId") Long pfId);

    @Transactional
    @Modifying
    @Query("update PortfolioEntity p set p.likeCount = p.likeCount + :value where p.pfId = :pfId")
    int updateLikeCount(@Param("pfId") Long pfId, @Param("value") int value);

    List<PortfolioEntity> findTop10ByCreateDateBetweenOrderByLikeCountDesc(LocalDateTime start, LocalDateTime end);

    Page<PortfolioEntity> findByTitleContainingOrTagsIn(Pageable pageable, String title, List<String> tag);

    Page<PortfolioEntity> findByTitleContaining(Pageable pageable, @Param("title")String title);

    @Query(value = "select DISTINCT p.* " +
            "from portfolio p, tag_portfolio_mapper tp, tag_entity t " +
            "where p.pf_id = tp.pf_id and tp.tag_tid = t.tid and (replace(p.title,' ','') like %:title% and t.content in :tag)", nativeQuery = true)
    Page<PortfolioEntity> getTitleAndTag(Pageable pageable, @Param("title")String title, @Param("tag")List<String> tag );

//    @Query("SELECT TagEntity FROM TagEntity tagEntity ORDER BY tagEntity.name")
//    @Transactional(readOnly = true)
//    List<TagEntity> findTagEntity();
//
//    @Query("SELECT TagEntity FROM TagEntity tagEntity WHERE tagEntity.name = ?1")
//    Optional<TagEntity> findTagEntityByName(String name);
}
