import {
    renderQuestion,
    renderAnswer,
    renderSkipped,
    renderEmptyQuiz,
    renderCompletion
} from "./quiz-renderer.js";


export class QuizSession {

    constructor(elements) {

        this.elements =
            elements;

        this.quizData =
            null;

        this.currentQuestionIndex =
            0;

        this.skippedQuestions =
            [];

        this.answeredQuestions =
            new Set();

        this.questionAttempts =
            new Map();

        this.questionStartTime =
            null;

        this.isFinished =
            false;

        this.isCancelled =
            false;
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


        this.currentQuestionIndex =
            0;

        this.skippedQuestions =
            [];

        this.answeredQuestions =
            new Set();

        this.questionAttempts =
            new Map();

        this.questionStartTime =
            null;

        this.isFinished =
            false;

        this.isCancelled =
            false;


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


        this.questionStartTime =
            Date.now();


        renderQuestion(
            question,
            this.currentQuestionIndex,
            this.quizData.questions.length,
            this.elements,
            answer =>
                this.answer(
                    question,
                    answer
                ),
            this.canSkip()
        );
    }


    showQuestion(question) {

        const questionIndex =
            this.quizData.questions.findIndex(
                originalQuestion =>
                    originalQuestion.id ===
                    question.id
            );


        this.currentQuestionIndex =
            questionIndex;


        this.questionStartTime =
            Date.now();


        renderQuestion(
            question,
            questionIndex,
            this.quizData.questions.length,
            this.elements,
            answer =>
                this.answer(
                    question,
                    answer
                ),
            this.canSkip()
        );
    }


    answer(
        question,
        selectedAnswer
    ) {

        const timeSpentMillis =
            this.getTimeSpent();


        const correct =
            selectedAnswer ===
            question.correctAnswer;


        const existingAttempt =
            this.questionAttempts.get(
                question.id
            );


        const skipCount =
            existingAttempt?.skipCount ?? 0;


        this.questionAttempts.set(
            question.id,
            {
                questionId:
                question.id,

                correct:
                correct,

                timeSpentMillis:
                timeSpentMillis,

                hintsUsed:
                    existingAttempt?.hintsUsed ?? 0,

                skipCount:
                skipCount
            }
        );


        this.answeredQuestions.add(
            question.id
        );


        this.skippedQuestions =
            this.skippedQuestions.filter(
                skippedQuestion =>
                    skippedQuestion.id !==
                    question.id
            );


        renderAnswer(
            selectedAnswer,
            this.elements
        );


        this.elements.nextButton.textContent =
            this.hasQuestionsRemaining()
                ? "Next"
                : "Finish";
    }


    skip() {

        const question =
            this.quizData.questions[
                this.currentQuestionIndex
                ];


        if (!question) {
            return;
        }


        const timeSpentMillis =
            this.getTimeSpent();


        const existingAttempt =
            this.questionAttempts.get(
                question.id
            );


        const skipCount =
            (existingAttempt?.skipCount ?? 0) + 1;


        this.questionAttempts.set(
            question.id,
            {
                questionId:
                question.id,

                correct:
                    existingAttempt?.correct ?? false,

                timeSpentMillis:
                    (existingAttempt?.timeSpentMillis ?? 0)
                    + timeSpentMillis,

                hintsUsed:
                    existingAttempt?.hintsUsed ?? 0,

                skipCount:
                skipCount
            }
        );


        const alreadySkipped =
            this.skippedQuestions.some(
                skippedQuestion =>
                    skippedQuestion.id ===
                    question.id
            );


        if (!alreadySkipped) {

            this.skippedQuestions.push(
                question
            );
        }


        renderSkipped(
            this.elements
        );


        this.moveToNextQuestion();
    }


    next() {

        if (
            this.isCurrentQuestionAnswered()
        ) {

            this.moveToNextQuestion();

            return;
        }


        if (
            this.isCurrentQuestionSkipped()
        ) {

            this.moveToNextQuestion();

            return;
        }
    }


    moveToNextQuestion() {

        const nextQuestionIndex =
            this.findNextUnansweredQuestionIndex();


        if (nextQuestionIndex !== -1) {

            this.currentQuestionIndex =
                nextQuestionIndex;

            this.showCurrentQuestion();

            return;
        }


        if (
            this.skippedQuestions.length > 0
        ) {

            const skippedQuestion =
                this.skippedQuestions.shift();


            this.showQuestion(
                skippedQuestion
            );

            return;
        }


        this.finish();
    }


    canSkip() {

        const remaining =
            this.getRemainingQuestionCount();


        return remaining > 1;
    }


    getRemainingQuestionCount() {

        let remaining = 0;


        for (
            const question of
            this.quizData.questions
            ) {

            if (
                !this.answeredQuestions.has(
                    question.id
                )
            ) {
                remaining++;
            }
        }


        return remaining;
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
                        skippedQuestion.id ===
                        question.id
                );


            if (!answered && !skipped) {
                return i;
            }
        }


        return -1;
    }


    isCurrentQuestionAnswered() {

        const question =
            this.quizData.questions[
                this.currentQuestionIndex
                ];


        return (
            question &&
            this.answeredQuestions.has(
                question.id
            )
        );
    }


    isCurrentQuestionSkipped() {

        const question =
            this.quizData.questions[
                this.currentQuestionIndex
                ];


        return (
            question &&
            this.skippedQuestions.some(
                skippedQuestion =>
                    skippedQuestion.id ===
                    question.id
            )
        );
    }


    hasQuestionsRemaining() {

        return (
            this.findNextUnansweredQuestionIndex()
            !== -1 ||
            this.skippedQuestions.length > 0
        );
    }


    getTimeSpent() {

        if (
            this.questionStartTime === null
        ) {
            return 0;
        }


        return Date.now() -
            this.questionStartTime;
    }


    async finish() {

        if (this.isFinished) {
            return;
        }


        this.isFinished =
            true;


        renderCompletion(
            this.quizData,
            Array.from(
                this.questionAttempts.values()
            ),
            this.elements
        );


        await this.submitAttempt(
            "completed"
        );
    }


    async cancel() {

        if (
            !this.quizData ||
            this.isFinished ||
            this.isCancelled
        ) {
            return;
        }


        this.isCancelled =
            true;


        await this.submitAttempt(
            "cancelled"
        );
    }


    async submitAttempt(status) {

        const questionAttempts =
            this.quizData.questions.map(
                question => {

                    const attempt =
                        this.questionAttempts.get(
                            question.id
                        );


                    return {
                        questionId:
                        question.id,

                        correct:
                            attempt?.correct ?? false,

                        timeSpentMillis:
                            attempt?.timeSpentMillis ?? 0,

                        hintsUsed:
                            attempt?.hintsUsed ?? 0,

                        skipCount:
                            attempt?.skipCount ?? 0
                    };
                }
            );


        const request = {

            quizId:
            this.quizData.quiz.id,

            status:
            status,

            questionAttempts:
            questionAttempts
        };


        try {

            const response =
                await fetch(
                    "/api/quiz-attempts",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(
                                request
                            )
                    }
                );


            if (!response.ok) {

                throw new Error(
                    `Failed to save quiz attempt (${response.status})`
                );
            }


        } catch (error) {

            console.error(
                "Could not save quiz attempt:",
                error
            );
        }
    }
}
