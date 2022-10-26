package com.InhaTc.Deview.Tag.Service;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Tag.Entity.TagEntity;
import com.InhaTc.Deview.Tag.Entity.TagPortfolioMapper;
import com.InhaTc.Deview.Tag.Repository.TagPortfolioMapperRepository;
import com.InhaTc.Deview.Tag.Repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagPortfolioMapperRepository tagPortfolioMapperRepository;

    public void createTagList(PortfolioEntity portfolio){
        String origin = portfolio.getTags();
        String[] tags = origin.split(",");
        List<String> tagList = new ArrayList<>();

        for (String tag : tags) {
            tagList.add(tag);
        }

        System.out.println("Create HashTags Success! -----> " + tagList);
        saveTag(tagList, portfolio.getPfId());
    }

    public Boolean saveTag(List<String> tagList, Long pfId){
        Integer result = 1;

        for(String tag : tagList){
            TagEntity findResult = tagRepository.findTagEntityByContent(tag);

            if(findResult == null){
                tagRepository.save(TagEntity.builder().content(tag).build());
            }

            TagEntity findTag = tagRepository.findTagEntityByContent(tag);
            tagPortfolioMapperRepository.save(TagPortfolioMapper.builder().tid(findTag.getTid()).pfId(pfId).build());
        }
        return result == 1;
    }

}
