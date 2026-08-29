import {
    renderQuestion,
    renderAnswer,
    renderEmptyQuiz,
    renderCompletion
} from "./quiz-renderer.js";


export class QuizSession {

    constructor(elements) {
        this.elements = elements;

        this.quizData = null;
        this.currentQuestionIndex = 0;
    }


    async load(button) {

        const quizId =
            button.dataset.quizId;

        const chapterId =
            button.dataset.chapterId;

        const sectionId =
            button.dataset.sectionId;

        if (!quizId || !chapterId || !sectionId) {
            throw new Error(
                "Missing quiz information."
            );
        }

        const response = await fetch(
            `/api/quizzes/sections/${chapterId}/${sectionId}/${quizId}`
        );

        if (!response.ok) {
            throw new Error(
                `Failed to load quiz (${response.status})`
            );
        }

        this.quizData =
            await response.json();

        this.currentQuestionIndex = 0;

        return this.quizData;
    }


    start() {

        if (!this.quizData) {
            throw new Error(
                "Quiz data has not been loaded."
            );
        }

        const {
            modalTitle
        } = this.elements;

        modalTitle.textContent =
            this.quizData.quiz.title;

        if (
            !this.quizData.questions ||
            this.quizData.questions.length === 0
        ) {

            renderEmptyQuiz(
                this.elements
            );

            return;
        }

        this.showCurrentQuestion();
    }


    showCurrentQuestion() {

        const question =
            this.quizData.questions[
                this.currentQuestionIndex
            ];

        renderQuestion(
            question,
            this.currentQuestionIndex,
            this.quizData.questions.length,
            this.elements,
            answer => this.answer(question, answer)
        );
    }


    answer(question, selectedAnswer) {

        renderAnswer(
            question,
            selectedAnswer,
            this.elements
        );

        const {
            nextButton
        } = this.elements;

        if (
            this.currentQuestionIndex ===
            this.quizData.questions.length - 1
        ) {
            nextButton.textContent = "Finish";
        } else {
            nextButton.textContent = "Next";
        }
    }


    next() {

        if (
            this.currentQuestionIndex <
            this.quizData.questions.length - 1
        ) {

            this.currentQuestionIndex++;

            this.showCurrentQuestion();

        } else {

            this.finish();
        }
    }


    skip() {

        if (
            this.currentQuestionIndex <
            this.quizData.questions.length - 1
        ) {

            this.currentQuestionIndex++;

            this.showCurrentQuestion();

        } else {

            this.finish();
        }
    }


    finish() {

        renderCompletion(
            this.elements
        );
    }
}
