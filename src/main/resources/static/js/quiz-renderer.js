export function renderQuestion(
    question,
    currentIndex,
    totalQuestions,
    elements,
    onAnswer
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

    skipButton.hidden = false;
    nextButton.hidden = true;

    Object.entries(question.options)
        .forEach(([key, text]) => {

            const button =
                document.createElement("button");

            button.type = "button";
            button.classList.add("quiz-option");

            button.dataset.answer = key;
            button.textContent = `${key}. ${text}`;

            button.addEventListener(
                "click",
                () => onAnswer(key)
            );

            optionsContainer.appendChild(button);
        });
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

    const optionButtons =
        optionsContainer.querySelectorAll(
            ".quiz-option"
        );

    optionButtons.forEach(button => {

        button.disabled = true;

        if (
            button.dataset.answer ===
            question.correctAnswer
        ) {
            button.classList.add("correct");
        }
    });

    const correct =
        selectedAnswer === question.correctAnswer;

    if (correct) {

        feedback.textContent =
            "Correct!";

        feedback.className =
            "quiz-feedback quiz-feedback-correct";

    } else {

        feedback.textContent =
            "Not quite.";

        feedback.className =
            "quiz-feedback quiz-feedback-incorrect";

        optionButtons.forEach(button => {

            if (
                button.dataset.answer ===
                selectedAnswer
            ) {
                button.classList.add("incorrect");
            }
        });
    }

    feedback.hidden = false;

    skipButton.hidden = true;
    nextButton.hidden = false;

    return correct;
}


export function renderEmptyQuiz(elements) {

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


export function renderCompletion(elements) {

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

    feedback.textContent =
        "Nice work!";

    feedback.className =
        "quiz-feedback quiz-feedback-correct";

    feedback.hidden = false;

    skipButton.hidden = true;
    nextButton.hidden = true;
}
