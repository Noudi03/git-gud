import { QuizSession } from "./quiz-session.js";


document.addEventListener(
    "DOMContentLoaded",
    () => {

        const modal =
            document.getElementById(
                "quiz-modal"
            );

        const closeButton =
            document.getElementById(
                "quiz-close-button"
            );

        const modalContent =
            modal?.querySelector(
                ".quiz-modal-content"
            );

        const modalTitle =
            document.getElementById(
                "quiz-modal-title"
            );

        const progress =
            document.getElementById(
                "quiz-progress"
            );

        const questionText =
            document.getElementById(
                "quiz-question"
            );

        const optionsContainer =
            document.getElementById(
                "quiz-options"
            );

        const feedback =
            document.getElementById(
                "quiz-feedback"
            );

        const skipButton =
            document.getElementById(
                "quiz-skip-button"
            );

        const nextButton =
            document.getElementById(
                "quiz-next-button"
            );

        const startButtons =
            document.querySelectorAll(
                ".quiz-start-button"
            );


        if (
            !modal ||
            !modalTitle ||
            !progress ||
            !questionText ||
            !optionsContainer ||
            !feedback ||
            !skipButton ||
            !nextButton
        ) {
            return;
        }


        const elements = {
            modalTitle,
            progress,
            questionText,
            optionsContainer,
            feedback,
            skipButton,
            nextButton
        };


        const session =
            new QuizSession(
                elements
            );


        async function openQuiz(link) {

            const quizUrl =
                link.dataset.quizUrl;

            if (!quizUrl) {
                return;
            }

            try {

                await session.load(
                    quizUrl
                );

                session.start();

                modal.dataset.quizUrl =
                    quizUrl;

                modal.hidden = false;

                document.body.classList.add(
                    "quiz-modal-open"
                );

            } catch (error) {

                console.error(
                    "Could not load quiz:",
                    error
                );

                /*
                 * A 403 means the quiz is locked.
                 * QuizSession.load() should expose the
                 * HTTP response status on the error.
                 */
                if (
                    error?.status === 403 ||
                    error?.response?.status === 403
                ) {

                    alert(
                        "This quiz is currently locked."
                    );

                    return;
                }

                alert(
                    "Could not load the quiz."
                );
            }
        }


        async function closeQuiz() {

            /*
             * If a quiz is currently active, closing
             * the modal means the user cancelled it.
             *
             * QuizSession.cancel() safely does nothing
             * if the quiz has already been completed.
             */
            await session.cancel();

            modal.hidden = true;

            document.body.classList.remove(
                "quiz-modal-open"
            );

            delete modal.dataset.quizUrl;
        }


        startButtons.forEach(
            link => {

                link.addEventListener(
                    "click",
                    event => {

                        event.preventDefault();

                        openQuiz(link);
                    }
                );

            }
        );


        closeButton?.addEventListener(
            "click",
            () => {
                closeQuiz();
            }
        );


        modalContent?.addEventListener(
            "click",
            event => {
                event.stopPropagation();
            }
        );


        skipButton.addEventListener(
            "click",
            () => session.skip()
        );


        nextButton.addEventListener(
            "click",
            () => session.next()
        );


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

    }
);
