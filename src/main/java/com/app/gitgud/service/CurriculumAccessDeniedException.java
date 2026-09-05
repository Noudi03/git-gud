package com.app.gitgud.service;

public class CurriculumAccessDeniedException
        extends RuntimeException {

    public CurriculumAccessDeniedException(
            String message
    ) {
        super(message);
    }
}
