package com.app.gitgud.seeder.data.chapter2;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.TrueFalseQuestion;
import com.app.gitgud.seeder.ChapterData;

import java.util.List;
import java.util.Map;

public class Chapter2Data implements ChapterData {

    @Override
    public Chapter getChapter() {
        Chapter c = new Chapter();
        c.setId("chapter-2");
        c.setTitle("Intro to git Branches and Merging.");
        c.setOrder(2);
        return c;
    }

    @Override
    public List<Section> getSections() {
        return Chapter2Curriculum.getSections();
    }

    @Override
    public Map<String, List<MultipleChoiceQuestion>> getQuestions() {
        return Chapter2Questions.getQuestions();
    }

    @Override
    public Map<String, List<TrueFalseQuestion>> getTrueFalseQuestions() {
        return Chapter2TrueFalse.getQuestions();
    }

    @Override
    public Map<String, List<FillInTheBlankQuestion>> getFillInTheBlankQuestions() {
        return Chapter2FillBlanks.getQuestions();
    }
}