import {
    renderTrial,
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

        this.attemptId =
            null;

        this.currentTrialIndex =
            0;

        this.skippedTrials =
            [];

        this.answeredTrials =
            new Set();

        this.trialAttempts =
            new Map();

        this.trialStartTime =
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

        this.attemptId =
            null;

        this.currentTrialIndex =
            0;

        this.skippedTrials =
            [];

        this.answeredTrials =
            new Set();

        this.trialAttempts =
            new Map();

        this.trialStartTime =
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
            this.quizData.trials.length === 0
        ) {

            renderEmptyQuiz(
                this.elements
            );

            return;
        }

        this.showCurrentTrial();
    }


    showCurrentTrial() {

        const trial =
            this.quizData.trials[
                this.currentTrialIndex
                ];

        if (!trial) {
            return;
        }

        this.trialStartTime =
            Date.now();

        renderTrial(
            trial,
            this.currentTrialIndex,
            this.quizData.trials.length,
            this.elements,
            answer =>
                this.answer(
                    trial,
                    answer
                ),
            this.canSkip()
        );
    }


    showTrial(trial) {

        const trialIndex =
            this.quizData.trials.findIndex(
                originalTrial =>
                    originalTrial.id ===
                    trial.id
            );

        if (trialIndex === -1) {
            return;
        }

        this.currentTrialIndex =
            trialIndex;

        this.trialStartTime =
            Date.now();

        renderTrial(
            trial,
            trialIndex,
            this.quizData.trials.length,
            this.elements,
            answer =>
                this.answer(
                    trial,
                    answer
                ),
            this.canSkip()
        );
    }


    answer(
        trial,
        selectedAnswer
    ) {

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
                trial,
                selectedAnswer
            );

        const existingAttempt =
            this.trialAttempts.get(
                trial.id
            );

        const skipCount =
            existingAttempt?.skipCount ?? 0;

        this.trialAttempts.set(
            trial.id,
            {
                trialId:
                trial.id,

                selectedAnswer:
                selectedAnswer,

                correct:
                correct,

                timeSpentMillis:
                    (
                        existingAttempt?.timeSpentMillis ?? 0
                    ) + timeSpentMillis,

                hintsUsed:
                    existingAttempt?.hintsUsed ?? 0,

                skipCount:
                skipCount
            }
        );

        this.answeredTrials.add(
            trial.id
        );

        this.skippedTrials =
            this.skippedTrials.filter(
                skippedTrial =>
                    skippedTrial.id !==
                    trial.id
            );

        renderAnswer(
            trial,
            selectedAnswer,
            this.elements
        );

        this.elements.nextButton.textContent =
            this.hasTrialsRemaining()
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

        const trial =
            this.quizData.trials[
                this.currentTrialIndex
                ];

        if (!trial) {
            return;
        }

        const timeSpentMillis =
            this.getTimeSpent();

        const existingAttempt =
            this.trialAttempts.get(
                trial.id
            );

        const skipCount =
            (
                existingAttempt?.skipCount ?? 0
            ) + 1;

        this.trialAttempts.set(
            trial.id,
            {
                trialId:
                trial.id,

                selectedAnswer:
                    existingAttempt?.selectedAnswer ?? null,

                correct:
                    existingAttempt?.correct ?? false,

                timeSpentMillis:
                    (
                        existingAttempt?.timeSpentMillis ?? 0
                    ) + timeSpentMillis,

                hintsUsed:
                    existingAttempt?.hintsUsed ?? 0,

                skipCount:
                skipCount
            }
        );

        const alreadySkipped =
            this.skippedTrials.some(
                skippedTrial =>
                    skippedTrial.id ===
                    trial.id
            );

        if (!alreadySkipped) {

            this.skippedTrials.push(
                trial
            );
        }

        renderSkipped(
            this.elements
        );

        this.moveToNextTrial();
    }


    next() {

        if (
            this.isFinished ||
            this.isCancelled
        ) {
            return;
        }

        if (
            this.isCurrentTrialAnswered()
        ) {

            this.moveToNextTrial();

            return;
        }

        if (
            this.isCurrentTrialSkipped()
        ) {

            this.moveToNextTrial();
        }
    }


    moveToNextTrial() {

        const nextTrialIndex =
            this.findNextUnansweredTrialIndex();

        if (nextTrialIndex !== -1) {

            this.currentTrialIndex =
                nextTrialIndex;

            this.showCurrentTrial();

            return;
        }

        if (
            this.skippedTrials.length > 0
        ) {

            const skippedTrial =
                this.skippedTrials.shift();

            this.showTrial(
                skippedTrial
            );

            return;
        }

        this.finish();
    }


    canSkip() {

        return (
            this.getRemainingTrialCount() > 1
        );
    }


    getRemainingTrialCount() {

        let remaining = 0;

        for (
            const trial of
            this.quizData.trials
            ) {

            if (
                !this.answeredTrials.has(
                    trial.id
                )
            ) {

                remaining++;
            }
        }

        return remaining;
    }


    findNextUnansweredTrialIndex() {

        for (
            let i = 0;
            i < this.quizData.trials.length;
            i++
        ) {

            const trial =
                this.quizData.trials[i];

            const answered =
                this.answeredTrials.has(
                    trial.id
                );

            const skipped =
                this.skippedTrials.some(
                    skippedTrial =>
                        skippedTrial.id ===
                        trial.id
                );

            if (!answered && !skipped) {
                return i;
            }
        }

        return -1;
    }


    isCurrentTrialAnswered() {

        const trial =
            this.quizData.trials[
                this.currentTrialIndex
                ];

        return (
            trial &&
            this.answeredTrials.has(
                trial.id
            )
        );
    }


    isCurrentTrialSkipped() {

        const trial =
            this.quizData.trials[
                this.currentTrialIndex
                ];

        return (
            trial &&
            this.skippedTrials.some(
                skippedTrial =>
                    skippedTrial.id ===
                    trial.id
            )
        );
    }


    hasTrialsRemaining() {

        return (
            this.findNextUnansweredTrialIndex()
            !== -1 ||
            this.skippedTrials.length > 0
        );
    }


    getTimeSpent() {

        if (
            this.trialStartTime === null
        ) {
            return 0;
        }

        return Date.now() -
            this.trialStartTime;
    }


    isCorrect(
        trial,
        answer
    ) {

        switch (trial.type) {

            case "QUESTION":

                return (
                    answer ===
                    trial.correctAnswer
                );

            case "FILL_IN_THE_BLANK":

                return (
                    this.normalizeAnswer(answer) ===
                    this.normalizeAnswer(
                        trial.correctAnswer
                    )
                );

            default:

                return false;
        }
    }


    normalizeAnswer(
        answer
    ) {

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

        this.isFinished =
            true;

        renderCompletion(
            this.quizData,
            Array.from(
                this.trialAttempts.values()
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

        if (
            this.quizData.trials.length === 0
        ) {
            return;
        }

        this.recordCurrentTrialTime();

        this.isCancelled =
            true;

        await this.submitAttempt(
            "cancelled"
        );
    }


    recordCurrentTrialTime() {

        const trial =
            this.quizData?.trials?.[
                this.currentTrialIndex
                ];

        if (
            !trial ||
            this.trialStartTime === null
        ) {
            return;
        }

        const timeSpentMillis =
            this.getTimeSpent();

        if (timeSpentMillis <= 0) {
            return;
        }

        const existingAttempt =
            this.trialAttempts.get(
                trial.id
            );

        this.trialAttempts.set(
            trial.id,
            {
                trialId:
                trial.id,

                selectedAnswer:
                    existingAttempt?.selectedAnswer ?? null,

                correct:
                    existingAttempt?.correct ?? false,

                timeSpentMillis:
                    (
                        existingAttempt?.timeSpentMillis ?? 0
                    ) + timeSpentMillis,

                hintsUsed:
                    existingAttempt?.hintsUsed ?? 0,

                skipCount:
                    existingAttempt?.skipCount ?? 0
            }
        );

        this.trialStartTime =
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

            await this.createTrialAttempts();

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
                "/api/quiz-attempts",
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


    async createTrialAttempts() {

        if (!this.attemptId) {
            throw new Error(
                "Missing quiz attempt ID."
            );
        }

        const trialAttempts =
            this.quizData.trials.map(
                trial => {

                    const attempt =
                        this.trialAttempts.get(
                            trial.id
                        );

                    return {
                        trialId:
                        trial.id,

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

        await Promise.all(
            trialAttempts.map(
                trialAttempt =>
                    this.createTrialAttempt(
                        trialAttempt
                    )
            )
        );
    }


    async createTrialAttempt(
        trialAttempt
    ) {

        const response =
            await fetch(
                `/api/quiz-attempts/${this.attemptId}/trial-attempts`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(
                            trialAttempt
                        )
                }
            );

        if (!response.ok) {

            throw new Error(
                `Failed to save trial attempt (${response.status})`
            );
        }
    }
}
