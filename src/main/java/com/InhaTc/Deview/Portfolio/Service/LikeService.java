package com.InhaTc.Deview.Portfolio.Service;

import com.InhaTc.Deview.Common.CustomException;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Repository.PortfolioRepository;
import com.InhaTc.Deview.Portfolio.DTO.LikesDTO;
import com.InhaTc.Deview.Portfolio.Entity.Likes;
import com.InhaTc.Deview.Portfolio.Repository.LikesRepository;
import com.InhaTc.Deview.User.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.InhaTc.Deview.Common.ErrorCode.*;

@Slf4j
@Service
public class LikeService {

    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioService portfolioService;


    public void likes(LikesDTO likesDTO, String userId){

        if(findLikeByUserEntityAndPfId(likesDTO,userId).isPresent()){
            throw new CustomException(ALREADY_HEARTED);
        }

        Likes likes = Likes.builder()
                .portfolio(portfolioRepository.findByPfId(likesDTO.getPfId()))
                .user(userRepository.findByUserId(likesDTO.getUserId()))
                .build();

        likesRepository.save(likes);
        portfolioService.updateLikeCount(likesDTO.getPfId(), +1);

    }

    public void unLikes(LikesDTO likesDTO, String userId) {

        Optional<Likes> likesOpt = findLikeByUserEntityAndPfId(likesDTO, userId);

        if (likesOpt.isEmpty()) {
            throw new CustomException(HEART_NOT_FOUND);
        }
        likesRepository.delete(likesOpt.get());
        portfolioService.updateLikeCount(likesDTO.getPfId(), -1);
    }

    public Optional<Likes> findLikeByUserEntityAndPfId(LikesDTO likesDTO, String userId){
        return likesRepository.findLikesByUserAndPortfolio(
                userRepository.findByUserId(userId),
                portfolioRepository.findByPfId(likesDTO.getPfId()));
    }
}
