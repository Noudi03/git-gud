package com.app.gitgud.service;

import com.app.gitgud.mapper.FirestoreSectionMapper;
import com.app.gitgud.model.Section;
import com.app.gitgud.repository.FirestoreTutorialRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    private final FirestoreTutorialRepository firestoreTutorialRepository;
    private final FirestoreSectionMapper firestoreSectionMapper;


    public SectionService(
            FirestoreTutorialRepository firestoreTutorialRepository,
            FirestoreSectionMapper firestoreSectionMapper
    ) {

        this.firestoreTutorialRepository =
                firestoreTutorialRepository;

        this.firestoreSectionMapper =
                firestoreSectionMapper;
    }


    public List<Section> getSections(
            String chapterId
    ) throws Exception {

        QuerySnapshot snapshot =
                firestoreTutorialRepository
                        .getSectionsCollection(chapterId)
                        .orderBy("order")
                        .get()
                        .get();

        List<Section> sections =
                new ArrayList<>();

        for (
                DocumentSnapshot document :
                snapshot.getDocuments()
        ) {

            Section section =
                    firestoreSectionMapper.toSection(
                            document,
                            chapterId
                    );

            if (section != null) {
                sections.add(section);
            }
        }

        return sections;
    }


    public Section getSection(
            String chapterId,
            String sectionId
    ) throws Exception {

        DocumentSnapshot document =
                firestoreTutorialRepository
                        .getSection(
                                chapterId,
                                sectionId
                        )
                        .get()
                        .get();

        return firestoreSectionMapper.toSection(
                document,
                chapterId
        );
    }
}
