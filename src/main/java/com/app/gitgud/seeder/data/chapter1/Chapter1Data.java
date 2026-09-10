package com.app.gitgud.seeder.data.chapter1;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.TrueFalseQuestion;
import com.app.gitgud.seeder.ChapterData;

import java.util.List;
import java.util.Map

public class Chapter1Data implements ChapterData {

    @Override
    public Chapter getChapter() {
        Chapter c = new Chapter();
        c.setId("chapter-1");
        c.setTitle("What is git? Why use it?");
        c.setOrder(1);
        return c;
    }

    @Override
    public List<Section> getSections() {
        return Chapter1Curriculum.getSections();
    }

    @Override
    public Map<String, List<MultipleChoiceQuestion>> getQuestions() {
        return Chapter1MultipleChoice.getQuestions();
    }

    @Override
    public Map<String, List<TrueFalseQuestion>> getTrueFalseQuestions() {
        return Chapter1TrueFalse.getQuestions();
    }

    @Override
    public Map<String, List<FillInTheBlankQuestion>> getFillInTheBlankQuestions() {
        return Chapter1FillBlanks.getQuestions();
    }
}
