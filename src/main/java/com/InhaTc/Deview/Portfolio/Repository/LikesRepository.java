package com.InhaTc.Deview.Portfolio.Repository;

import com.InhaTc.Deview.Portfolio.Entity.Likes;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.User.Entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findLikesByUserAndPortfolio(UserEntity user, PortfolioEntity portfolio);

}
