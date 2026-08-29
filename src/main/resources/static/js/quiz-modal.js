document.addEventListener("DOMContentLoaded", () => {

    const modal =
        document.getElementById("quiz-modal");

    const closeButton =
        document.getElementById("quiz-close-button");

    const modalContent =
        modal?.querySelector(".quiz-modal-content");

    const modalTitle =
        document.getElementById("quiz-modal-title");

    const modalBody =
        document.getElementById("quiz-modal-body");

    const startButtons =
        document.querySelectorAll(".quiz-start-button");


    if (!modal || !modalTitle || !modalBody) {
        return;
    }


    async function openQuiz(button) {

        const quizId = button.dataset.quizId;
        const chapterId = button.dataset.chapterId;
        const sectionId = button.dataset.sectionId;

        if (!quizId || !chapterId || !sectionId) {
            console.error("Missing quiz information.");
            return;
        }

        try {

            const response = await fetch(
                `/api/quizzes/sections/${chapterId}/${sectionId}/${quizId}`
            );

            if (!response.ok) {
                console.error(
                    "Failed to load quiz:",
                    response.status
                );

                return;
            }

            const quizData = await response.json();

            console.log("Quiz data:", quizData);


            /*
             * Display quiz title
             */

            modalTitle.textContent =
                quizData.quiz.title;


            /*
             * Display first question
             */

            if (
                !quizData.questions ||
                quizData.questions.length === 0
            ) {
                modalBody.textContent =
                    "This quiz has no questions.";

            } else {

                const question =
                    quizData.questions[0];

                modalBody.textContent =
                    question.text;
            }


            /*
             * Store quiz ID
             */

            modal.dataset.quizId = quizId;


            /*
             * Open modal
             */

            modal.hidden = false;

            document.body.classList.add(
                "quiz-modal-open"
            );

        } catch (error) {

            console.error(
                "Could not load quiz:",
                error
            );
        }
    }


    function closeQuiz() {

        modal.hidden = true;

        document.body.classList.remove(
            "quiz-modal-open"
        );

        delete modal.dataset.quizId;
    }


    /*
     * Start buttons
     */

    startButtons.forEach(button => {

        button.addEventListener("click", () => {
            openQuiz(button);
        });

    });


    /*
     * Close button
     */

    closeButton?.addEventListener(
        "click",
        closeQuiz
    );


    /*
     * Prevent clicks inside modal
     * from closing it.
     */

    modalContent?.addEventListener(
        "click",
        event => {
            event.stopPropagation();
        }
    );


    /*
     * Escape closes the modal.
     */

    document.addEventListener(
        "keydown",
        event => {

            if (
                event.key === "Escape" &&
                !modal.hidden
            ) {
                closeQuiz();
            }

        }
    );

});
