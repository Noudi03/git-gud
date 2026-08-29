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

        // Questions that have already been answered.
        this.answeredQuestions = new Set();
    }


    async load(button) {

        const quizType =
            button.dataset.quizType;

        const quizId =
            button.dataset.quizId;

        const chapterId =
            button.dataset.chapterId;

        const sectionId =
            button.dataset.sectionId;


        if (!quizType || !quizId) {
            throw new Error(
                "Missing quiz information."
            );
        }


        let url;


        switch (quizType) {

            case "section":

                if (!chapterId || !sectionId) {
                    throw new Error(
                        "Section quiz is missing chapter or section information."
                    );
                }

                url =
                    `/api/quizzes/sections/${chapterId}/${sectionId}/${quizId}`;

                break;


            case "chapter":

                if (!chapterId) {
                    throw new Error(
                        "Chapter quiz is missing chapter information."
                    );
                }

                url =
                    `/api/quizzes/chapters/${chapterId}/${quizId}`;

                break;


            case "global":

                url =
                    `/api/quizzes/${quizId}`;

                break;


            default:

                throw new Error(
                    `Unknown quiz type: ${quizType}`
                );
        }


        const response =
            await fetch(url);


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

        // Mark this question as answered.
        this.answeredQuestions.add(question.id);

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

        // No unanswered original questions remain.
        // If there are skipped questions, show them.
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
        // if it isn't already there.
        const alreadySkipped =
            this.skippedQuestions.some(
                skippedQuestion =>
                    skippedQuestion.id === question.id
            );

        if (!alreadySkipped) {
            this.skippedQuestions.push(question);
        }

        // Move to the next unanswered question.
        const nextQuestionIndex =
            this.findNextUnansweredQuestionIndex();

        if (nextQuestionIndex !== -1) {

            this.currentQuestionIndex =
                nextQuestionIndex;

            this.showCurrentQuestion();

            return;
        }

        // No new unanswered questions remain.
        // Start going through the skipped questions.
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

        renderQuestion(
            question,
            this.getQuestionNumber(question),
            this.quizData.questions.length,
            this.elements,
            answer => this.answer(question, answer)
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

            if (
                !this.answeredQuestions.has(
                    question.id
                ) &&
                !this.skippedQuestions.some(
                    skippedQuestion =>
                        skippedQuestion.id === question.id
                )
            ) {
                return i;
            }
        }

        return -1;
    }


    getQuestionNumber(question) {

        const index =
            this.quizData.questions.findIndex(
                originalQuestion =>
                    originalQuestion.id === question.id
            );

        return index !== -1
            ? index
            : 0;
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
