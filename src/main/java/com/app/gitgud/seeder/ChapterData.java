package com.app.gitgud.seeder;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.TrueFalseQuestion;

import java.util.List;
import java.util.Map;

public interface ChapterData {
    Chapter getChapter();
    List<Section> getSections();
    Map<String, List<MultipleChoiceQuestion>> getQuestions();
    Map<String, List<TrueFalseQuestion>> getTrueFalseQuestions();
    Map<String, List<FillInTheBlankQuestion>> getFillInTheBlankQuestions();
}