package com.app.gitgud.seeder;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.QuestionTrial;
import com.app.gitgud.model.Section;

import java.util.List;
import java.util.Map;

public interface ChapterData {
    Chapter getChapter();
    List<Section> getSections();
    Map<String, List<QuestionTrial>> getQuestions();
}