package com.app.gitgud.seeder.data.chapter3;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.MultipleChoiceTrial;
import com.app.gitgud.model.Section;
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
    public Map<String, List<MultipleChoiceTrial>> getQuestions() {
        return Chapter3Questions.getQuestions();
    }
}