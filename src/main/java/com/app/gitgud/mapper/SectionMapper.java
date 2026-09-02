package com.app.gitgud.mapper;

import com.app.gitgud.model.Section;
import com.app.gitgud.model.dto.SectionDto;
import com.app.gitgud.service.MarkdownService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SectionMapper {

    private final MarkdownService markdownService;

    public SectionMapper(MarkdownService markdownService) {
        this.markdownService = markdownService;
    }

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
                markdownService.toHtml(section.getContent()),
                section.getOrder(),
                section.getPrerequisiteId(),
                section.isLocked(),
                markdownService.toHtml(section.getAdvancedContent())
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
