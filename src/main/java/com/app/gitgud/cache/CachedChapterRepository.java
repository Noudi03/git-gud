package com.app.gitgud.cache;

import com.app.gitgud.mapper.FirestoreChapterMapper;
import com.app.gitgud.model.Chapter;
import com.app.gitgud.repository.FirestoreChapterRepository;
import com.google.cloud.firestore.Firestore;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

// Transparent caching decorator. Marked @Primary so Spring injects
// this instead of the plain FirestoreChapterRepository wherever the
// base type is requested, with no changes needed anywhere else.
@Primary
@Repository
public class CachedChapterRepository
        extends FirestoreChapterRepository {

    public CachedChapterRepository(
            Firestore db,
            FirestoreChapterMapper mapper
    ) {
        super(db, mapper);
    }

    @Override
    @Cacheable("chapters")
    public List<Chapter> findChapters()
            throws Exception {

        return super.findChapters();
    }

    @Override
    @Cacheable(
            value = "chapter",
            key = "#chapterId"
    )
    public Chapter findChapter(
            String chapterId
    ) throws Exception {

        return super.findChapter(chapterId);
    }
}