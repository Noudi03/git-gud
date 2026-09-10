package com.app.gitgud.cache;

import com.app.gitgud.mapper.FirestoreSectionMapper;
import com.app.gitgud.model.Section;
import com.app.gitgud.repository.FirestoreSectionRepository;
import com.google.cloud.firestore.Firestore;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class CachedSectionRepository
        extends FirestoreSectionRepository {

    public CachedSectionRepository(
            Firestore db,
            FirestoreSectionMapper mapper
    ) {
        super(db, mapper);
    }

    @Override
    @Cacheable(
            value = "sections",
            key = "#chapterId"
    )
    public List<Section> findSections(
            String chapterId
    ) throws Exception {

        return super.findSections(chapterId);
    }

    @Override
    @Cacheable(
            value = "section",
            key = "#chapterId + ':' + #sectionId"
    )
    public Section findSection(
            String chapterId,
            String sectionId
    ) throws Exception {

        return super.findSection(
                chapterId,
                sectionId
        );
    }
}