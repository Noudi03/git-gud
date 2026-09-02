package com.app.gitgud.service;

import com.app.gitgud.model.Section;
import com.app.gitgud.repository.FirestoreSectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final FirestoreSectionRepository sectionRepository;


    public SectionService(
            FirestoreSectionRepository sectionRepository
    ) {

        this.sectionRepository =
                sectionRepository;
    }


    public List<Section> getSections(
            String chapterId
    ) throws Exception {

        return sectionRepository.findSections(
                chapterId
        );
    }


    public Section getSection(
            String chapterId,
            String sectionId
    ) throws Exception {

        return sectionRepository.findSection(
                chapterId,
                sectionId
        );
    }
}
