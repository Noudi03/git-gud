export function renderMultipleChoiceQuestion(
    question,
    currentIndex,
    totalQuestions,
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
        `Question ${currentIndex + 1} / ${totalQuestions}`;

    questionText.textContent =
        question.text;

    optionsContainer.innerHTML = "";

    feedback.textContent = "";
    feedback.hidden = true;
    feedback.className = "quiz-feedback";

    skipButton.hidden =
        !canSkip;

    nextButton.hidden = true;

    Object.entries(question.options)
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

export function renderFillInTheBlankQuestion(
    question,
    currentIndex,
    totalQuestions,
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
        `Question ${currentIndex + 1} / ${totalQuestions}`;

    questionText.textContent =
        question.text;

    optionsContainer.innerHTML = "";

    feedback.textContent = "";
    feedback.hidden = true;
    feedback.className = "quiz-feedback";

    skipButton.hidden =
        !canSkip;

    nextButton.hidden = true;

    const input =
        document.createElement("input");

    input.type = "text";

    input.classList.add(
        "quiz-fill-in-the-blank"
    );

    input.autocomplete = "off";

    input.setAttribute(
        "aria-label",
        "Your answer"
    );

    input.addEventListener(
        "keydown",
        event => {

            if (event.key !== "Enter") {
                return;
            }

            event.preventDefault();

            if (input.value.trim() === "") {
                return;
            }

            onAnswer(input.value);
        }
    );

    optionsContainer.appendChild(
        input
    );

    input.focus();
}

export function renderTrueFalseQuestion(
    question,
    currentIndex,
    totalQuestions,
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
        `Question ${currentIndex + 1} / ${totalQuestions}`;

    questionText.textContent =
        question.text;

    optionsContainer.innerHTML = "";

    feedback.textContent = "";
    feedback.hidden = true;
    feedback.className = "quiz-feedback";

    skipButton.hidden =
        !canSkip;

    nextButton.hidden = true;

    [
        {
            value: true,
            label: "True"
        },
        {
            value: false,
            label: "False"
        }
    ].forEach(option => {

        const button =
            document.createElement("button");

        button.type = "button";

        button.classList.add(
            "quiz-option"
        );

        button.dataset.answer =
            String(option.value);

        button.setAttribute(
            "aria-pressed",
            "false"
        );

        button.textContent =
            option.label;

        button.addEventListener(
            "click",
            () => onAnswer(option.value)
        );

        optionsContainer.appendChild(
            button
        );
    });
}

export function renderQuestion(
    question,
    currentIndex,
    totalQuestions,
    elements,
    onAnswer,
    canSkip
) {
    switch (question.type) {

        case "MULTIPLE_CHOICE":

            renderMultipleChoiceQuestion(
                question,
                currentIndex,
                totalQuestions,
                elements,
                onAnswer,
                canSkip
            );

            break;

        case "FILL_IN_THE_BLANK":

            renderFillInTheBlankQuestion(
                question,
                currentIndex,
                totalQuestions,
                elements,
                onAnswer,
                canSkip
            );

            break;

        case "TRUE_FALSE":

            renderTrueFalseQuestion(
                question,
                currentIndex,
                totalQuestions,
                elements,
                onAnswer,
                canSkip
            );

            break;

        default:

            throw new Error(
                `Unsupported question type: ${question.type}`
            );
    }
}

export function renderAnswer(
    question,
    selectedAnswer,
    elements
) {
    const {
        optionsContainer,
        feedback,
        skipButton,
        nextButton
    } = elements;

    if (
        question.type === "MULTIPLE_CHOICE" ||
        question.type === "TRUE_FALSE"
    ) {

        const optionButtons =
            optionsContainer.querySelectorAll(
                ".quiz-option"
            );

        optionButtons.forEach(button => {

            button.disabled = true;

            const selected =
                button.dataset.answer ===
                String(selectedAnswer);

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

    } else if (
        question.type === "FILL_IN_THE_BLANK"
    ) {

        const input =
            optionsContainer.querySelector(
                ".quiz-fill-in-the-blank"
            );

        if (input) {
            input.disabled = true;
        }
    }

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

    const fillInTheBlankInput =
        optionsContainer.querySelector(
            ".quiz-fill-in-the-blank"
        );

    if (fillInTheBlankInput) {
        fillInTheBlankInput.disabled = true;
    }

    feedback.textContent =
        "Question skipped. You can return to it later.";

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
        "This quiz has no questions.";

    optionsContainer.innerHTML = "";

    feedback.textContent = "";
    feedback.hidden = true;

    skipButton.hidden = true;
    nextButton.hidden = true;
}

export function renderCompletion(
    quizData,
    questionResults,
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

    questionResults.forEach(result => {

        const question =
            quizData.questions.find(
                item =>
                    item.id ===
                    result.questionId
            );

        if (!question) {
            return;
        }

        const container =
            document.createElement("div");

        container.classList.add(
            "quiz-result"
        );

        const questionTextElement =
            document.createElement("p");

        questionTextElement.classList.add(
            "quiz-result-question"
        );

        questionTextElement.textContent =
            getQuestionDisplayText(
                question
            );

        container.appendChild(
            questionTextElement
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
                getQuestionAnswerText(
                    question,
                    selectedAnswer
                );

            selectedAnswerElement.textContent =
                `Your answer: ${selectedText}`;
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
            getQuestionCorrectAnswerText(
                question
            );

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
            + `${skippedCount} question`
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

function getQuestionDisplayText(
    question
) {
    return question.text;
}

function getQuestionAnswerText(
    question,
    answer
) {
    switch (question.type) {

        case "MULTIPLE_CHOICE":
            return (
                `${answer}. ` +
                (question.options?.[answer] ?? answer)
            );

        case "FILL_IN_THE_BLANK":
            return answer;

        case "TRUE_FALSE":
            return String(answer) === "true"
                ? "True"
                : "False";

        default:
            return answer;
    }
}

function getQuestionCorrectAnswerText(
    question
) {
    switch (question.type) {

        case "MULTIPLE_CHOICE": {

            const answer =
                question.correctAnswer;

            const text =
                question.options?.[answer] ?? answer;

            return `${answer}. ${text}`;
        }

        case "FILL_IN_THE_BLANK":
            return question.correctAnswer;

        case "TRUE_FALSE":
            return question.correctAnswer
                ? "True"
                : "False";

        default:
            return null;
    }
}
