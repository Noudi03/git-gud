package com.app.gitgud.seeder.data.chapter3;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.TrueFalseQuestion;
import com.app.gitgud.seeder.ChapterData;

import java.util.List;
import java.util.Map;

public class Chapter3Data implements ChapterData {

    @Override
    public Chapter getChapter() {
        Chapter c = new Chapter();
        c.setId("chapter-3");
        c.setTitle("Remote Repositories");
        c.setOrder(2);
        return c;
    }

    @Override
    public List<Section> getSections() {
        return Chapter3Curriculum.getSections();
    }

    @Override
    public Map<String, List<MultipleChoiceQuestion>> getQuestions() {
        return Chapter3MultipleChoice.getQuestions();
    }

    @Override
    public Map<String, List<TrueFalseQuestion>> getTrueFalseQuestions() {
        return Chapter3TrueFalse.getQuestions();
    }

    @Override
    public Map<String, List<FillInTheBlankQuestion>> getFillInTheBlankQuestions() {
        return Chapter3FillBlanks.getQuestions();
    }
}