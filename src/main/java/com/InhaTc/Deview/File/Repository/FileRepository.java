package com.InhaTc.Deview.File.Repository;

import com.InhaTc.Deview.File.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByFid(Long fId);

    Optional<FileEntity> findByFilename(String fileName);
}
