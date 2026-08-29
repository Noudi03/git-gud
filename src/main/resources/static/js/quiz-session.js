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

        // Questions skipped during this session.
        this.skippedQuestions = [];

        // Questions already answered during this session.
        this.answeredQuestions = new Set();
    }


    async load(quizUrl) {

        if (!quizUrl) {
            throw new Error(
                "Missing quiz URL."
            );
        }

        const response =
            await fetch(quizUrl);

        if (!response.ok) {
            throw new Error(
                `Failed to load quiz (${response.status})`
            );
        }

        this.quizData =
            await response.json();

        this.currentQuestionIndex = 0;

        this.skippedQuestions = [];
        this.answeredQuestions = new Set();

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
            answer =>
                this.answer(
                    question,
                    answer
                )
        );
    }


    answer(question, selectedAnswer) {

        renderAnswer(
            question,
            selectedAnswer,
            this.elements
        );

        // Mark this question as answered.
        this.answeredQuestions.add(
            question.id
        );

        // If this question was previously skipped,
        // remove it from the skipped queue.
        this.skippedQuestions =
            this.skippedQuestions.filter(
                skippedQuestion =>
                    skippedQuestion.id !== question.id
            );

        const {
            nextButton
        } = this.elements;

        nextButton.textContent =
            this.hasQuestionsRemaining()
                ? "Next"
                : "Finish";
    }


    next() {

        const nextQuestionIndex =
            this.findNextUnansweredQuestionIndex();

        if (nextQuestionIndex !== -1) {

            this.currentQuestionIndex =
                nextQuestionIndex;

            this.showCurrentQuestion();

            return;
        }

        // All original questions have been
        // answered or skipped.
        if (this.skippedQuestions.length > 0) {

            const skippedQuestion =
                this.skippedQuestions.shift();

            this.showQuestion(
                skippedQuestion
            );

            return;
        }

        this.finish();
    }


    skip() {

        const question =
            this.quizData.questions[
                this.currentQuestionIndex
            ];

        // Add the question to the skipped queue
        // if it is not already there.
        const alreadySkipped =
            this.skippedQuestions.some(
                skippedQuestion =>
                    skippedQuestion.id === question.id
            );

        if (!alreadySkipped) {

            this.skippedQuestions.push(
                question
            );
        }

        // Find the next unanswered question.
        const nextQuestionIndex =
            this.findNextUnansweredQuestionIndex();

        if (nextQuestionIndex !== -1) {

            this.currentQuestionIndex =
                nextQuestionIndex;

            this.showCurrentQuestion();

            return;
        }

        // No unanswered questions remain.
        // Go back through skipped questions.
        if (this.skippedQuestions.length > 0) {

            const skippedQuestion =
                this.skippedQuestions.shift();

            this.showQuestion(
                skippedQuestion
            );

            return;
        }

        this.finish();
    }


    showQuestion(question) {

        const questionIndex =
            this.quizData.questions.findIndex(
                originalQuestion =>
                    originalQuestion.id === question.id
            );

        renderQuestion(
            question,
            questionIndex,
            this.quizData.questions.length,
            this.elements,
            answer =>
                this.answer(
                    question,
                    answer
                )
        );
    }


    findNextUnansweredQuestionIndex() {

        for (
            let i = 0;
            i < this.quizData.questions.length;
            i++
        ) {

            const question =
                this.quizData.questions[i];

            const answered =
                this.answeredQuestions.has(
                    question.id
                );

            const skipped =
                this.skippedQuestions.some(
                    skippedQuestion =>
                        skippedQuestion.id === question.id
                );

            if (!answered && !skipped) {
                return i;
            }
        }

        return -1;
    }


    hasQuestionsRemaining() {

        return (
            this.findNextUnansweredQuestionIndex() !== -1 ||
            this.skippedQuestions.length > 0
        );
    }


    finish() {

        renderCompletion(
            this.elements
        );
    }
}
