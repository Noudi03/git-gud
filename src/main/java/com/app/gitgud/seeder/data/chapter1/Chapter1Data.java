
package com.app.gitgud.seeder.data.chapter1;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.MultipleChoiceTrial;
import com.app.gitgud.model.Section;
import com.app.gitgud.seeder.ChapterData;

import java.util.List;
import java.util.Map;

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
    public Map<String, List<MultipleChoiceTrial>> getQuestions() {
        return Chapter1Questions.getQuestions();
    }
}
