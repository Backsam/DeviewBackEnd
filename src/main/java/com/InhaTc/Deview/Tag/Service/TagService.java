package com.InhaTc.Deview.Tag.Service;

import com.InhaTc.Deview.Portfolio.Entity.PortfolioEntity;
import com.InhaTc.Deview.Tag.Entity.TagEntity;
import com.InhaTc.Deview.Tag.Entity.TagPortfolioMapper;
import com.InhaTc.Deview.Tag.Entity.TagWantedJobMapper;
import com.InhaTc.Deview.Tag.Repository.TagPortfolioMapperRepository;
import com.InhaTc.Deview.Tag.Repository.TagRepository;
import com.InhaTc.Deview.Tag.Repository.TagWantedJobMapperRepository;
import com.InhaTc.Deview.WantedJob.Entity.WantedJobEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagPortfolioMapperRepository tagPortfolioMapperRepository;
    @Autowired
    private TagWantedJobMapperRepository tagWantedJobMapperRepository;


    public void createTagList(PortfolioEntity portfolio){
        String origin = portfolio.getTags();
        if(origin  != null){
            String[] tags = origin.split(",");
            List<String> tagList = new ArrayList<>();

            for (String tag : tags) {
                tagList.add(tag);
            }

            System.out.println("Create HashTags Success! -----> " + tagList);
            savePortfolioByTag(tagList, portfolio);
        }
    }

    public void deleteTag(String content){
        TagEntity tag = tagRepository.findTagEntityByContent(content);
        tagRepository.delete(tag);
    }

    public void createTagList(WantedJobEntity wantedJob){
        String origin = wantedJob.getTags();
        if(origin  != null) {
            String[] tags = origin.split(",");
            List<String> tagList = new ArrayList<>();

            for (String tag : tags) {
                tagList.add(tag);
            }

            System.out.println("Create HashTags Success! -----> " + tagList);
            saveWantedJobByTag(tagList, wantedJob);
        }
    }

    public Boolean savePortfolioByTag(List<String> tagList, PortfolioEntity portfolio){
        Integer result = 1;

        for(String tag : tagList){
            TagEntity findResult = tagRepository.findTagEntityByContent(tag);

            if(findResult == null){
                tagRepository.save(TagEntity.builder().content(tag).build());
            }

            TagEntity findTag = tagRepository.findTagEntityByContent(tag);
            tagPortfolioMapperRepository.save(TagPortfolioMapper.builder().tag(findTag).portfolio(portfolio).content(tag).build());
        }
        return result == 1;
    }

    public void updatePortfolioByTag(PortfolioEntity portfolio) {
        String origin = portfolio.getTags();

        if (origin != null) {
            String[] tags = origin.split(",");
            List<String> tagList = new ArrayList<>();

            for (String tag : tags) {
                tagList.add(tag);
            }

            List<TagPortfolioMapper> portfolioTag = tagPortfolioMapperRepository.findByPortfolio(portfolio);

            if (portfolioTag != null) {
                for (TagPortfolioMapper map : portfolioTag) {
                    String content = map.getContent();
                    int i = tagList.indexOf(content);

                    if (i == -1) {                                   // 태그리스트에 없는 값이면 삭제
                        tagPortfolioMapperRepository.delete(map);
                    } else {                                         // 태그리스트에 있는 값이면 저장하는 값에서 제외
                        tagList.remove(content);
                    }
                }
            }
            savePortfolioByTag(tagList, portfolio);
        }
    }

    public Boolean saveWantedJobByTag(List<String> tagList, WantedJobEntity wantedJob){
        Integer result = 1;

        for(String tag : tagList){
            TagEntity findResult = tagRepository.findTagEntityByContent(tag);

            if(findResult == null){
                tagRepository.save(TagEntity.builder().content(tag).build());
            }

            TagEntity findTag = tagRepository.findTagEntityByContent(tag);
            tagWantedJobMapperRepository.save(TagWantedJobMapper.builder().tag(findTag).wantedJob(wantedJob).build());
        }
        return result == 1;
    }

}
