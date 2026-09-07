const leaderboardSelector =
    document.getElementById(
        "leaderboard-selector"
    );

const leaderboardTitle =
    document.getElementById(
        "leaderboard-title"
    );

const leaderboardDescription =
    document.getElementById(
        "leaderboard-description"
    );

const leaderboardList =
    document.getElementById(
        "leaderboard-list"
    );

const leaderboardEmpty =
    document.getElementById(
        "leaderboard-empty"
    );

async function loadLeaderboard(
    quizKey = ""
) {

    const url =
        quizKey
            ? `/api/leaderboards?quizKey=${encodeURIComponent(quizKey)}`
            : "/api/leaderboards";

    const response =
        await fetch(url);

    if (!response.ok) {
        throw new Error(
            "Could not load leaderboard."
        );
    }

    return response.json();
}

function getContextLabel(
    context
) {

    if (
        context.type === "section"
    ) {

        return `Section · ${context.chapterId} · ${context.sectionId}`;
    }

    if (
        context.type === "chapter"
    ) {

        return `Chapter · ${context.chapterId}`;
    }

    return "Global";
}

function updateQuizSelector(
    leaderboard
) {

    const currentValue =
        leaderboardSelector.value;

    leaderboardSelector.innerHTML =
        `
            <option value="">
                Rating
            </option>
        `;

    leaderboard.quizzes.forEach(
        quiz => {

            const option =
                document.createElement(
                    "option"
                );

            option.value =
                quiz.key;

            const typeLabel =
                quiz.type
                    ? ` · ${capitalize(quiz.type)}`
                    : "";

            option.textContent =
                `${quiz.title}${typeLabel} · ${getContextLabel(quiz.context)}`;

            leaderboardSelector.appendChild(
                option
            );
        }
    );

    leaderboardSelector.value =
        leaderboard.quizzes.some(
            quiz =>
                quiz.key ===
                currentValue
        )
            ? currentValue
            : "";
}

function renderLeaderboard(
    leaderboard
) {

    leaderboardList.innerHTML =
        "";

    if (
        leaderboard.entries.length === 0
    ) {

        leaderboardEmpty.hidden =
            false;

        return;
    }

    leaderboardEmpty.hidden =
        true;

    const isQuizLeaderboard =
        leaderboardSelector.value !== "";

    leaderboard.entries.forEach(
        entry => {

            const row =
                document.createElement(
                    "div"
                );

            row.className =
                "leaderboard-entry";

            row.innerHTML =
                `
                    <span class="leaderboard-rank">
                        ${entry.rank}
                    </span>

                    <span class="leaderboard-username">
                        ${entry.username}
                    </span>

                    <strong class="leaderboard-score">
                        ${entry.score}${isQuizLeaderboard ? " pts" : ""}
                    </strong>
                `;

            leaderboardList.appendChild(
                row
            );
        }
    );
}

function updateHeading() {

    if (
        leaderboardSelector.value === ""
    ) {

        leaderboardTitle.textContent =
            "Rating";

        leaderboardDescription.textContent =
            "Top users by rating.";

        return;
    }

    const option =
        leaderboardSelector
            .selectedOptions[0];

    leaderboardTitle.textContent =
        option.textContent;

    leaderboardDescription.textContent =
        "Top scores for this quiz.";
}

function capitalize(
    value
) {

    if (!value) {
        return "";
    }

    return (
        value.charAt(0).toUpperCase() +
        value.slice(1).toLowerCase()
    );
}

async function refreshLeaderboard() {

    try {

        const leaderboard =
            await loadLeaderboard(
                leaderboardSelector.value
            );

        updateQuizSelector(
            leaderboard
        );

        updateHeading();

        renderLeaderboard(
            leaderboard
        );

    } catch (error) {

        console.error(
            "Could not load leaderboard:",
            error
        );
    }
}

leaderboardSelector.addEventListener(
    "change",
    refreshLeaderboard
);

refreshLeaderboard();
