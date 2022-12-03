package com.InhaTc.Deview.Portfolio.Service;

import com.InhaTc.Deview.NewFile.Service.NewFileService;
import com.InhaTc.Deview.NewFile.Service.PortfolioFileService;
import com.InhaTc.Deview.Portfolio.DTO.PortfolioDTO;
import com.InhaTc.Deview.Portfolio.Entity.Likes;
import com.InhaTc.Deview.Portfolio.Repository.PortfolioRepository;
import com.InhaTc.Deview.File.Service.FileService;
import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Tag.Service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private FileService fileService;

    @Autowired
    private PortfolioFileService portfolioFileService;

    private LikeService likeService;



    public Page<PortfolioEntity> getList(Pageable pageable){
        return portfolioRepository.findAll(pageable);
    }

    public Page<PortfolioEntity> getList(Pageable pageable, String userId){
        return portfolioRepository.findByUserId(pageable, userId);
    }

    public List<PortfolioEntity> getTopLikeList(){
        LocalDateTime today = LocalDateTime.now();    //끝
        LocalDateTime startDate = today.minusDays(7); //시작

        return portfolioRepository.findTop10ByCreateDateBetweenOrderByLikeCountDesc(startDate, today);
    }

    public PortfolioEntity create(final PortfolioEntity entity){
        validate(entity);
        portfolioRepository.save(entity);
        log.info("Entity id : {} is saved ", entity.getPfId());
        tagService.createTagList(entity);
        return portfolioRepository.findByPfId(entity.getPfId());
    }

    public PortfolioEntity get(final Long num){
        PortfolioEntity portfolio = portfolioRepository.findByPfId(num);
        this.updateView(portfolio.getPfId());
        return portfolio;
    }

    public PortfolioEntity retrieve(final String userId){
        return portfolioRepository.findByUserId(userId);
    }

    public PortfolioEntity update(final PortfolioEntity entity){
        validate(entity);

        final PortfolioEntity origin = portfolioRepository.findByPfId(entity.getPfId());

        if(origin != null){
            final PortfolioEntity portfolio = origin;
            portfolio.setTitle(entity.getTitle());
            portfolio.setSummary(entity.getSummary());
            portfolio.setContent(entity.getContent());
            portfolio.setType(entity.getType());
            portfolio.setTags(entity.getTags());
            portfolioRepository.save(portfolio);
            tagService.updatePortfolioByTag(portfolio);
        }
        return portfolioRepository.findByPfId(entity.getPfId());
    }

    public void delete(final PortfolioEntity entity) {
        validate(entity);

        final PortfolioEntity deleteEntity = portfolioRepository.findByPfId(entity.getPfId());

        try {
            deleteEntity.getTag().clear();
            portfolioRepository.delete(deleteEntity);
        } catch(Exception e) {
            log.error("error deleting entity ", entity.getPfId(), e);
            throw new RuntimeException("error deleting entity " + entity.getPfId());
        }
    }
    @Transactional
    public int updateView(Long pfId){
        return this.portfolioRepository.updateView(pfId);
    }

    @Transactional
    public int updateLikeCount(Long pfId, int value){
        return this.portfolioRepository.updateLikeCount(pfId, value);
    }

    private void validate(final PortfolioEntity entity){
        if(entity == null){
            log.warn("Entity can't be null");
            throw new RuntimeException("Entity can't be null");
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
