package com.InhaTc.Deview.Apply.Service;

import com.InhaTc.Deview.Apply.Entity.ApplyEntity;
import com.InhaTc.Deview.Apply.Repository.ApplyRepository;
import com.InhaTc.Deview.WantedJob.Sevice.WantedJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApplyService {

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private WantedJobService wantedJobService;

    public ApplyEntity create(final ApplyEntity entity){
        return applyRepository.save(entity);
    }

    public List<ApplyEntity> getList(final Long wjId){
       return applyRepository.findByWjId(wantedJobService.get(wjId));
    }

    public ApplyEntity get(final Long apId){
        return applyRepository.findById(apId).get();
    }
}
