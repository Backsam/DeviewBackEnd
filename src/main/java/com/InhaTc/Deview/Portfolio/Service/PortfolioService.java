package com.InhaTc.Deview.Portfolio.Service;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Repository.PortfolioRepository;
import com.InhaTc.Deview.Tag.Service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TagService tagService;



    public Page<PortfolioEntity> getList(Pageable pageable){
        return portfolioRepository.findAll(pageable);
    }

    public PortfolioEntity create(final PortfolioEntity entity){
        validate(entity);
        portfolioRepository.save(entity);
        log.info("Enttity id : {} is saved ", entity.getPfId());
        tagService.createTagList(entity);
        return portfolioRepository.findByPfId(entity.getPfId());
    }

    public PortfolioEntity get(final Long num){ return portfolioRepository.findByPfId(num);}

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
            portfolioRepository.save(portfolio);
        }
        return portfolioRepository.findByPfId(entity.getPfId());
    }

    public void delete(final PortfolioEntity entity) {
        validate(entity);

        try {
            portfolioRepository.delete(entity);
        } catch(Exception e) {
            log.error("error deleting entity ", entity.getPfId(), e);
            throw new RuntimeException("error deleting entity " + entity.getPfId());
        }
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
