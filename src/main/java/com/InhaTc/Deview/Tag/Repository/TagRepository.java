package com.InhaTc.Deview.Tag.Repository;

import com.InhaTc.Deview.Tag.Entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    TagEntity findTagEntityByContent(String content);
}
