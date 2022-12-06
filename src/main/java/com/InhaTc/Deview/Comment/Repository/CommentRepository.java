package com.InhaTc.Deview.Comment.Repository;

import com.InhaTc.Deview.Comment.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from Comment c where c.pf_id = :pfId",
            nativeQuery = true)
    Page<Comment> getCommentOfPortfolio(Pageable pageable, @Param("pfId")long pfId);
}
