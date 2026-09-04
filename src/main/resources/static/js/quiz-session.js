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

        this.quizUrl =
            null;

        this.attemptId =
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

        this.quizUrl =
            quizUrl;

        this.quizData =
            await response.json();

        this.attemptId =
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

        if (!question) {
            return;
        }

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

        if (questionIndex === -1) {
            return;
        }

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

    answer(question, selectedAnswer) {

        if (
            this.isFinished ||
            this.isCancelled
        ) {
            return;
        }

        const timeSpentMillis =
            this.getTimeSpent();

        const correct =
            this.isCorrect(
                question,
                selectedAnswer
            );

        const existingAttempt =
            this.questionAttempts.get(
                question.id
            );

        const skipCount =
            existingAttempt?.skipCount ?? 0;

        this.questionAttempts.set(
            question.id,
            {
                questionId: question.id,
                selectedAnswer: selectedAnswer,
                correct: correct,
                timeSpentMillis:
                    (existingAttempt?.timeSpentMillis ?? 0)
                    + timeSpentMillis,
                hintsUsed:
                    existingAttempt?.hintsUsed ?? 0,
                skipCount: skipCount
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
            question,
            selectedAnswer,
            this.elements
        );

        this.elements.nextButton.textContent =
            this.hasQuestionsRemaining()
                ? "Next"
                : "Finish";
    }

    skip() {

        if (
            this.isFinished ||
            this.isCancelled
        ) {
            return;
        }

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
            (existingAttempt?.skipCount ?? 0)
            + 1;

        this.questionAttempts.set(
            question.id,
            {
                questionId: question.id,
                selectedAnswer:
                    existingAttempt?.selectedAnswer ?? null,
                correct:
                    existingAttempt?.correct ?? false,
                timeSpentMillis:
                    (existingAttempt?.timeSpentMillis ?? 0)
                    + timeSpentMillis,
                hintsUsed:
                    existingAttempt?.hintsUsed ?? 0,
                skipCount: skipCount
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
            this.isFinished ||
            this.isCancelled
        ) {
            return;
        }

        if (this.isCurrentQuestionAnswered()) {
            this.moveToNextQuestion();
            return;
        }

        if (this.isCurrentQuestionSkipped()) {
            this.moveToNextQuestion();
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

    canSkip() {
        return (
            this.getRemainingQuestionCount() > 1
        );
    }

    getRemainingQuestionCount() {

        let remaining = 0;

        for (
            const question
            of this.quizData.questions
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

        return (
            Date.now() -
            this.questionStartTime
        );
    }

    isCorrect(
        question,
        answer
    ) {

        switch (question.type) {

            case "MULTIPLE_CHOICE":
                return (
                    answer ===
                    question.correctAnswer
                );

            case "FILL_IN_THE_BLANK":
                return (
                    this.normalizeAnswer(answer) ===
                    this.normalizeAnswer(
                        question.correctAnswer
                    )
                );

            case "TRUE_FALSE":
                return (
                    Boolean(answer) ===
                    question.correctAnswer
                );

            default:
                return false;
        }
    }

    normalizeAnswer(answer) {

        if (
            answer === null ||
            answer === undefined
        ) {
            return "";
        }

        return String(answer)
            .trim()
            .toLowerCase();
    }

    async finish() {

        if (
            this.isFinished ||
            this.isCancelled
        ) {
            return;
        }

        this.isFinished = true;

        renderCompletion(
            this.quizData,
            Array.from(
                this.questionAttempts.values()
            ),
            this.elements
        );

        try {

            await this.startAttempt();

            await this.createQuestionAttempts();

            await this.finalizeAttempt(
                "completed"
            );

        } catch (error) {

            console.error(
                "Could not save completed quiz:",
                error
            );
        }
    }

    async cancel() {

        if (
            !this.quizData ||
            this.isFinished ||
            this.isCancelled
        ) {
            return;
        }

        if (
            this.quizData.questions.length === 0
        ) {
            return;
        }

        this.recordCurrentQuestionTime();

        this.isCancelled = true;

        try {

            await this.startAttempt();

            await this.createQuestionAttempts();

            await this.finalizeAttempt(
                "cancelled"
            );

        } catch (error) {

            console.error(
                "Could not save cancelled quiz:",
                error
            );
        }
    }

    async startAttempt() {

        const attempt =
            await this.createQuizAttempt(
                "in_progress"
            );

        this.attemptId =
            attempt.id;

        return attempt;
    }

    async finalizeAttempt(status) {

        if (!this.attemptId) {
            throw new Error(
                "Missing quiz attempt ID."
            );
        }

        const response =
            await fetch(
                `/api/quiz-attempts/${this.attemptId}/finalize`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type":
                            "application/json"
                    },
                    body:
                        JSON.stringify({
                            status: status
                        })
                }
            );

        if (!response.ok) {
            throw new Error(
                `Failed to finalize quiz attempt (${response.status})`
            );
        }

        return response.json();
    }

    recordCurrentQuestionTime() {

        const question =
            this.quizData?.questions?.[
                this.currentQuestionIndex
                ];

        if (
            !question ||
            this.questionStartTime === null
        ) {
            return;
        }

        const timeSpentMillis =
            this.getTimeSpent();

        if (timeSpentMillis <= 0) {
            return;
        }

        const existingAttempt =
            this.questionAttempts.get(
                question.id
            );

        this.questionAttempts.set(
            question.id,
            {
                questionId: question.id,
                selectedAnswer:
                    existingAttempt?.selectedAnswer ?? null,
                correct:
                    existingAttempt?.correct ?? false,
                timeSpentMillis:
                    (existingAttempt?.timeSpentMillis ?? 0)
                    + timeSpentMillis,
                hintsUsed:
                    existingAttempt?.hintsUsed ?? 0,
                skipCount:
                    existingAttempt?.skipCount ?? 0
            }
        );

        this.questionStartTime =
            null;
    }

    async submitAttempt(status) {

        if (!this.quizData) {
            return;
        }

        try {

            const attempt =
                await this.createQuizAttempt(
                    status
                );

            this.attemptId =
                attempt.id;

            await this.createQuestionAttempts();

        } catch (error) {

            console.error(
                "Could not save quiz attempt:",
                error
            );
        }
    }

    async createQuizAttempt(status) {

        const request = {
            quizId:
            this.quizData.quiz.id,

            status:
            status
        };

        const response =
            await fetch(
                this.getAttemptUrl(),
                {
                    method: "POST",
                    headers: {
                        "Content-Type":
                            "application/json"
                    },
                    body:
                        JSON.stringify(request)
                }
            );

        if (!response.ok) {
            throw new Error(
                `Failed to save quiz attempt (${response.status})`
            );
        }

        return response.json();
    }

    getAttemptUrl() {

        if (!this.quizUrl) {
            throw new Error(
                "Missing quiz URL."
            );
        }

        return this.quizUrl.replace(
            "/api/quizzes/",
            "/api/quiz-attempts/"
        );
    }

    async createQuestionAttempts() {

        if (!this.attemptId) {
            throw new Error(
                "Missing quiz attempt ID."
            );
        }

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

                        selectedAnswer:
                            attempt?.selectedAnswer ?? null,

                        timeSpentMillis:
                            attempt?.timeSpentMillis ?? 0,

                        hintsUsed:
                            attempt?.hintsUsed ?? 0,

                        skipCount:
                            attempt?.skipCount ?? 0
                    };
                }
            );

        await Promise.all(
            questionAttempts.map(
                questionAttempt =>
                    this.createQuestionAttempt(
                        questionAttempt
                    )
            )
        );
    }

    async createQuestionAttempt(
        questionAttempt
    ) {

        const response =
            await fetch(
                `/api/quiz-attempts/${this.attemptId}/question-attempts`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type":
                            "application/json"
                    },
                    body:
                        JSON.stringify(
                            questionAttempt
                        )
                }
            );

        if (!response.ok) {
            throw new Error(
                `Failed to save question attempt (${response.status})`
            );
        }
    }
}
