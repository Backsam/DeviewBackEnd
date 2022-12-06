package com.InhaTc.Deview.WantedJob.Sevice;

import com.InhaTc.Deview.File.Service.FileService;
import com.InhaTc.Deview.Tag.Service.TagService;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import com.InhaTc.Deview.WantedJob.Repository.WantedJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WantedJobService {

    @Autowired
    private WantedJobRepository wantedJobRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private FileService fileService;


    public Page<WantedJobEntity> getList(Pageable pageable) {
        return wantedJobRepository.findAll(pageable);
    }

    public WantedJobEntity get(final Long num){ return wantedJobRepository.findByWjId(num);}

    public WantedJobEntity create(final WantedJobEntity entity){
        validate(entity);
        wantedJobRepository.save(entity);
        log.info("Enttity id : {} is saved ", entity.getWjId());
        tagService.createTagList(entity);
        return wantedJobRepository.findByWjId(entity.getWjId());
    }

    private void validate(final WantedJobEntity entity){
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
