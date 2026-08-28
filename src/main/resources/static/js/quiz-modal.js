document.addEventListener("DOMContentLoaded", () => {

    const modal = document.getElementById("quiz-modal");
    const closeButton = document.getElementById("quiz-close-button");
    const overlay = modal?.querySelector(".quiz-modal-overlay");
    const startButtons = document.querySelectorAll(".quiz-start-button");

    if (!modal) {
        return;
    }

    function openQuiz(quizId) {
        modal.hidden = false;
        document.body.classList.add("quiz-modal-open");

        modal.dataset.quizId = quizId;

        console.log("Opening quiz:", quizId);
    }

    function closeQuiz() {
        modal.hidden = true;
        document.body.classList.remove("quiz-modal-open");

        delete modal.dataset.quizId;
    }

    startButtons.forEach(button => {

        button.addEventListener("click", () => {

            const quizId = button.dataset.quizId;

            if (!quizId) {
                return;
            }

            openQuiz(quizId);
        });

    });

    closeButton?.addEventListener("click", closeQuiz);

    overlay?.addEventListener("click", closeQuiz);

    document.addEventListener("keydown", event => {

        if (event.key === "Escape" && !modal.hidden) {
            closeQuiz();
        }

    });

});
