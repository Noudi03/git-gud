package com.app.gitgud.mapper;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.dto.ChapterDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChapterMapper {

    public ChapterDto toDto(
            Chapter chapter
    ) {

        if (chapter == null) {
            return null;
        }

        return new ChapterDto(
                chapter.getId(),
                chapter.getTitle(),
                chapter.getOrder()
        );
    }


    public List<ChapterDto> toDtoList(
            List<Chapter> chapters
    ) {

        return chapters.stream()
                .map(this::toDto)
                .toList();
    }
}
