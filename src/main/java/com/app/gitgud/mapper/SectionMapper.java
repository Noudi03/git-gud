package com.app.gitgud.mapper;

import com.app.gitgud.model.Section;
import com.app.gitgud.model.dto.SectionDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SectionMapper {

    public SectionDto toDto(
            Section section
    ) {

        if (section == null) {
            return null;
        }

        return new SectionDto(
                section.getId(),
                section.getChapterId(),
                section.getTitle(),
                section.getContent(),
                section.getOrder(),
                section.getPrerequisiteId(),
                section.isLocked(),
                section.getAdvancedContent()
        );
    }


    public List<SectionDto> toDtoList(
            List<Section> sections
    ) {

        return sections.stream()
                .map(this::toDto)
                .toList();
    }
}
