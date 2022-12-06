package com.InhaTc.Deview.Search.Controller;

import com.InhaTc.Deview.Search.DTO.SearchDTO;
import com.InhaTc.Deview.Search.Service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/")
    public ResponseEntity<?> Search(@RequestParam(name = "type") String type,
                                    @RequestParam(name = "keyword", required = false) String keyword,
                                    @RequestParam(name = "tags", required = false) List<String> tag,
                                    Pageable pageable){
        if(type == null) {
            return ResponseEntity.ok().body("검색결과가 없습니다");
        }

        if(tag == null ){
            tag = new ArrayList<>();
        }

        log.info("Searching :" + type);
        log.info("Searching :" + keyword);
        log.info("Searching :" + tag);


        if(type.equals("0")) {
            return ResponseEntity.ok().body(searchService.SearchPortfolio(keyword, tag, pageable));
        }else if(type.equals("1")) {
            return ResponseEntity.ok().body(searchService.SearchWantedJob(keyword, tag, pageable));
        }else {
            return ResponseEntity.ok().body("검색결과가 없습니다");
        }
    }
}
