export function renderQuestionTrial(
    trial,
    currentIndex,
    totalTrials,
    elements,
    onAnswer,
    canSkip
) {
    const {
        progress,
        questionText,
        optionsContainer,
        feedback,
        skipButton,
        nextButton
    } = elements;

    progress.textContent =
        `Trial ${currentIndex + 1} / ${totalTrials}`;

    questionText.textContent =
        trial.text;

    optionsContainer.innerHTML = "";

    feedback.textContent = "";
    feedback.hidden = true;
    feedback.className = "quiz-feedback";

    skipButton.hidden =
        !canSkip;

    nextButton.hidden = true;

    Object.entries(trial.options)
        .forEach(([key, text]) => {

            const button =
                document.createElement("button");

            button.type = "button";

            button.classList.add(
                "quiz-option"
            );

            button.dataset.answer =
                key;

            button.setAttribute(
                "aria-pressed",
                "false"
            );

            button.textContent =
                `${key}. ${text}`;

            button.addEventListener(
                "click",
                () => onAnswer(key)
            );

            optionsContainer.appendChild(
                button
            );
        });
}


export function renderTrial(
    trial,
    currentIndex,
    totalTrials,
    elements,
    onAnswer,
    canSkip
) {
    switch (trial.type) {

        case "QUESTION":
            renderQuestionTrial(
                trial,
                currentIndex,
                totalTrials,
                elements,
                onAnswer,
                canSkip
            );
            break;

        default:
            throw new Error(
                `Unsupported trial type: ${trial.type}`
            );
    }
}


export function renderAnswer(
    selectedAnswer,
    elements
) {
    const {
        optionsContainer,
        feedback,
        skipButton,
        nextButton
    } = elements;

    const optionButtons =
        optionsContainer.querySelectorAll(
            ".quiz-option"
        );

    optionButtons.forEach(button => {

        button.disabled = true;

        const selected =
            button.dataset.answer ===
            selectedAnswer;

        button.setAttribute(
            "aria-pressed",
            selected ? "true" : "false"
        );

        if (selected) {
            button.classList.add(
                "selected"
            );
        }
    });

    feedback.textContent =
        "Answer selected. Your result will be shown when the quiz is complete.";

    feedback.className =
        "quiz-feedback";

    feedback.hidden = false;

    skipButton.hidden = true;
    nextButton.hidden = false;
}


export function renderSkipped(
    elements
) {
    const {
        optionsContainer,
        feedback,
        skipButton,
        nextButton
    } = elements;

    const optionButtons =
        optionsContainer.querySelectorAll(
            ".quiz-option"
        );

    optionButtons.forEach(button => {
        button.disabled = true;
    });

    feedback.textContent =
        "Trial skipped. You can return to it later.";

    feedback.className =
        "quiz-feedback";

    feedback.hidden = false;

    skipButton.hidden = true;
    nextButton.hidden = false;
}


export function renderEmptyQuiz(
    elements
) {
    const {
        progress,
        questionText,
        optionsContainer,
        feedback,
        skipButton,
        nextButton
    } = elements;

    progress.textContent = "";

    questionText.textContent =
        "This quiz has no trials.";

    optionsContainer.innerHTML = "";

    feedback.textContent = "";
    feedback.hidden = true;

    skipButton.hidden = true;
    nextButton.hidden = true;
}


export function renderCompletion(
    quizData,
    trialResults,
    elements
) {
    const {
        progress,
        questionText,
        optionsContainer,
        feedback,
        skipButton,
        nextButton
    } = elements;

    progress.textContent = "";

    questionText.textContent =
        "Quiz complete!";

    optionsContainer.innerHTML = "";

    let correctCount = 0;
    let answeredCount = 0;
    let skippedCount = 0;

    trialResults.forEach(result => {

        const trial =
            quizData.trials.find(
                item =>
                    item.id ===
                    result.trialId
            );

        if (!trial) {
            return;
        }

        const container =
            document.createElement("div");

        container.classList.add(
            "quiz-result"
        );

        const trialTextElement =
            document.createElement("p");

        trialTextElement.classList.add(
            "quiz-result-question"
        );

        trialTextElement.textContent =
            getTrialDisplayText(trial);

        container.appendChild(
            trialTextElement
        );

        const selectedAnswer =
            result.selectedAnswer;

        const skipped =
            selectedAnswer === null ||
            selectedAnswer === undefined;

        if (skipped) {
            skippedCount++;
        } else {
            answeredCount++;

            if (result.correct) {
                correctCount++;
            }
        }

        const resultStatus =
            document.createElement("p");

        resultStatus.classList.add(
            "quiz-result-status"
        );

        if (skipped) {

            resultStatus.textContent =
                "Skipped";

            resultStatus.classList.add(
                "quiz-result-skipped"
            );

        } else if (result.correct) {

            resultStatus.textContent =
                "Correct";

            resultStatus.classList.add(
                "quiz-result-correct"
            );

        } else {

            resultStatus.textContent =
                "Incorrect";

            resultStatus.classList.add(
                "quiz-result-incorrect"
            );
        }

        container.appendChild(
            resultStatus
        );

        const selectedAnswerElement =
            document.createElement("p");

        selectedAnswerElement.classList.add(
            "quiz-result-answer"
        );

        if (skipped) {

            selectedAnswerElement.innerHTML =
                "<strong>Your answer:</strong> Skipped";

        } else {

            const selectedText =
                getTrialAnswerText(
                    trial,
                    selectedAnswer
                );

            selectedAnswerElement.textContent =
                `Your answer: ${selectedAnswer}. ${selectedText}`;
        }

        container.appendChild(
            selectedAnswerElement
        );

        const correctAnswerElement =
            document.createElement("p");

        correctAnswerElement.classList.add(
            "quiz-result-answer",
            "quiz-result-correct-answer"
        );

        const correctText =
            getTrialCorrectAnswerText(trial);

        if (correctText) {

            correctAnswerElement.textContent =
                `Correct answer: ${correctText}`;

            container.appendChild(
                correctAnswerElement
            );
        }

        optionsContainer.appendChild(
            container
        );
    });

    let summary;

    if (skippedCount > 0) {

        summary =
            `You got ${correctCount} / ${answeredCount} answered correctly. `
            + `${skippedCount} trial`
            + `${skippedCount === 1 ? "" : "s"} skipped.`;

    } else {

        summary =
            `You got ${correctCount} / ${answeredCount} correct.`;
    }

    feedback.textContent =
        summary;

    feedback.className =
        "quiz-feedback quiz-feedback-correct";

    feedback.hidden = false;

    skipButton.hidden = true;
    nextButton.hidden = true;
}


function getTrialDisplayText(trial) {

    switch (trial.type) {

        case "QUESTION":
            return trial.text;

        default:
            return "Unsupported trial";
    }
}


function getTrialAnswerText(
    trial,
    answer
) {

    switch (trial.type) {

        case "QUESTION":
            return trial.options?.[answer] ?? answer;

        default:
            return answer;
    }
}


function getTrialCorrectAnswerText(trial) {

    switch (trial.type) {

        case "QUESTION": {

            const answer =
                trial.correctAnswer;

            const text =
                trial.options?.[answer] ?? answer;

            return `${answer}. ${text}`;
        }

        default:
            return null;
    }
}
