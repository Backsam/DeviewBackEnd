package com.InhaTc.Deview.Portfolio.Repository;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, String> {

    PortfolioEntity findByUserId(String userId);
    Boolean existsByPfId(Long pfId);

    PortfolioEntity findByPfId(Long pfId);

//    @Query("SELECT TagEntity FROM TagEntity tagEntity ORDER BY tagEntity.name")
//    @Transactional(readOnly = true)
//    List<TagEntity> findTagEntity();
//
//    @Query("SELECT TagEntity FROM TagEntity tagEntity WHERE tagEntity.name = ?1")
//    Optional<TagEntity> findTagEntityByName(String name);
}
