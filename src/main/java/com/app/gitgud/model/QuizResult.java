package com.app.gitgud.model;

public class QuizResult {

    private final int score;
    private final QuizGrade grade;
    private final int correctCount;

    public QuizResult(
            int score,
            QuizGrade grade,
            int correctCount
    ) {
        this.score = score;
        this.grade = grade;
        this.correctCount = correctCount;
    }

    public int getScore() {
        return score;
    }

    public QuizGrade getGrade() {
        return grade;
    }

    public int getCorrectCount() {
        return correctCount;
    }
}
