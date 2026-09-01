export function renderQuestion(
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

    skipButton.hidden = !canSkip;
    nextButton.hidden = true;

    Object.entries(question.options)
        .forEach(([key, text]) => {

            const button =
                document.createElement("button");

            button.type = "button";
            button.classList.add("quiz-option");

            button.dataset.answer = key;

            button.textContent =
                `${key}. ${text}`;

            button.addEventListener(
                "click",
                () => onAnswer(key)
            );

            optionsContainer.appendChild(button);
        });
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

        if (
            button.dataset.answer ===
            selectedAnswer
        ) {
            button.classList.add(
                "selected"
            );
        }
    });

    feedback.textContent =
        "Answer selected.";

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
        "Question skipped.";

    feedback.className =
        "quiz-feedback";

    feedback.hidden = false;

    skipButton.hidden = true;
    nextButton.hidden = false;
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

    questionResults.forEach(result => {

        if (result.correct) {
            correctCount++;
        }

        const container =
            document.createElement("div");

        container.classList.add(
            "quiz-result"
        );

        const question =
            quizData.questions.find(
                item =>
                    item.id === result.questionId
            );

        if (!question) {
            return;
        }

        const text =
            document.createElement("p");

        text.textContent =
            question.text;

        container.appendChild(text);


        const answer =
            document.createElement("p");

        answer.textContent =
            `Correct answer: ${question.correctAnswer}`;

        answer.classList.add(
            result.correct
                ? "quiz-result-correct"
                : "quiz-result-incorrect"
        );

        container.appendChild(answer);

        optionsContainer.appendChild(
            container
        );
    });


    feedback.textContent =
        `You got ${correctCount} / ${questionResults.length} correct.`;

    feedback.className =
        "quiz-feedback quiz-feedback-correct";

    feedback.hidden = false;

    skipButton.hidden = true;
    nextButton.hidden = true;
}
