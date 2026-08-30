package com.app.gitgud.service;

import com.app.gitgud.mapper.SectionFirestoreMapper;
import com.app.gitgud.model.Section;
import com.app.gitgud.repository.TutorialFirestoreRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    private final TutorialFirestoreRepository tutorialFirestoreRepository;
    private final SectionFirestoreMapper sectionFirestoreMapper;


    public SectionService(
            TutorialFirestoreRepository tutorialFirestoreRepository,
            SectionFirestoreMapper sectionFirestoreMapper
    ) {

        this.tutorialFirestoreRepository =
                tutorialFirestoreRepository;

        this.sectionFirestoreMapper =
                sectionFirestoreMapper;
    }


    public List<Section> getSections(
            String chapterId
    ) throws Exception {

        QuerySnapshot snapshot =
                tutorialFirestoreRepository
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
                    sectionFirestoreMapper.toSection(
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
                tutorialFirestoreRepository
                        .getSection(
                                chapterId,
                                sectionId
                        )
                        .get()
                        .get();

        return sectionFirestoreMapper.toSection(
                document,
                chapterId
        );
    }
}
