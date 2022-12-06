package com.InhaTc.Deview.Search.Service;


import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Portfolio.Repository.PortfolioRepository;
import com.InhaTc.Deview.Portfolio.Service.PortfolioService;
import com.InhaTc.Deview.Search.DTO.SearchDTO;
import com.InhaTc.Deview.Search.DTO.SearchResultDTO;
import com.InhaTc.Deview.User.Repository.UserRepository;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import com.InhaTc.Deview.WantedJob.Repository.WantedJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SearchService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WantedJobRepository wantedJobRepository;

    public Page<PortfolioEntity> SearchPortfolio(String keyword, List<String> tags, Pageable pageable){
        log.info("=====================================> "  +keyword);
        log.info("=====================================> "  +tags.toString() + " " + tags.size() + " " + tags.isEmpty());

        Page<PortfolioEntity> entities;

        if(tags.size() != 0){
            entities = portfolioRepository.getTitleAndTag(pageable, keyword, tags);
        }else{
            entities = portfolioRepository.findByTitleContaining(pageable, keyword);
        }

        return entities;
    }

    public Page<WantedJobEntity> SearchWantedJob(String keyword, List<String> tags, Pageable pageable){
        log.info("=====================================> "  + keyword);
        log.info("=====================================> "  + tags.toString());

        Page<WantedJobEntity> wantedJobEntities;
        if(tags.size() != 0){
            wantedJobEntities = wantedJobRepository.findByTitleContaining(pageable, keyword);
        }else{
            wantedJobEntities = wantedJobRepository.findByTitleContainingOrTagsIn(pageable, keyword, tags);
        }
        return wantedJobEntities;
    }
}


