package com.InhaTc.Deview.Portfolio.Service;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Repository.PortfolioRepository;
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

    public Page<PortfolioEntity> getList(Pageable pageable){
        return portfolioRepository.findAll(pageable);
    }

    public PortfolioEntity create(final PortfolioEntity entity){
        validate(entity);
        portfolioRepository.save(entity);
        log.info("Enttity id : {} is saved ", entity.getPfId());
        return portfolioRepository.findByPfId(entity.getPfId());
    }


    private void validate(final PortfolioEntity entity){
        if(entity == null){
            log.warn("Entity can't be null");
            throw new RuntimeException("Entity can't be null");
        }

        if(entity.getUser() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
