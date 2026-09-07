const quizSelector =
    document.getElementById(
        "quiz-selector"
    );

const statisticsContent =
    document.getElementById(
        "statistics-content"
    );

const statisticsEmpty =
    document.getElementById(
        "statistics-empty"
    );

const completedAttempts =
    document.getElementById(
        "completed-attempts"
    );

const cancelledAttempts =
    document.getElementById(
        "cancelled-attempts"
    );

const averageScore =
    document.getElementById(
        "average-score"
    );

const averageAccuracy =
    document.getElementById(
        "average-accuracy"
    );

const averageCompletionTime =
    document.getElementById(
        "average-completion-time"
    );

const totalHints =
    document.getElementById(
        "total-hints"
    );

const totalTime =
    document.getElementById(
        "total-time"
    );

const totalIncorrect =
    document.getElementById(
        "total-incorrect"
    );

const totalSkipped =
    document.getElementById(
        "total-skipped"
    );

const bestAttemptPanel =
    document.getElementById(
        "best-attempt-panel"
    );

const bestScore =
    document.getElementById(
        "best-score"
    );

const bestTime =
    document.getElementById(
        "best-time"
    );

const bestHints =
    document.getElementById(
        "best-hints"
    );

const bestSkipped =
    document.getElementById(
        "best-skipped"
    );

const bestGrade =
    document.getElementById(
        "best-grade"
    );

let performanceChart = null;
let answerChart = null;
let difficultyChart = null;

async function loadStatistics(
    quizKey = ""
) {

    const url =
        quizKey
            ? `/api/quiz-attempts/statistics?quizKey=${encodeURIComponent(quizKey)}`
            : "/api/quiz-attempts/statistics";

    const response =
        await fetch(url);

    if (!response.ok) {
        throw new Error(
            "Could not load statistics."
        );
    }

    return response.json();
}

function updateSummary(
    statistics
) {

    const summary =
        statistics.summary;

    completedAttempts.textContent =
        summary.completedAttempts;

    cancelledAttempts.textContent =
        summary.cancelledAttempts;

    averageScore.textContent =
        `${summary.averageScore} pts`;

    averageAccuracy.textContent =
        `${summary.averageAccuracy}%`;

    averageCompletionTime.textContent =
        formatDuration(
            summary.averageCompletionTimeMillis
        );

    totalHints.textContent =
        summary.totalHintsUsed;

    totalIncorrect.textContent =
        summary.totalIncorrect;

    totalSkipped.textContent =
        summary.totalSkipped;

    totalTime.textContent =
        formatDuration(
            summary.totalTimeSpentMillis
        );
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
    statistics
) {

    const currentValue =
        quizSelector.value;

    quizSelector.innerHTML =
        `
            <option value="">
                All quizzes
            </option>
        `;

    statistics.quizzes.forEach(
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

            quizSelector.appendChild(
                option
            );
        }
    );

    quizSelector.value =
        statistics.quizzes.some(
            quiz =>
                quiz.key ===
                currentValue
        )
            ? currentValue
            : "";
}

function updateBestAttempt(
    statistics
) {

    const bestAttempt =
        statistics.bestAttempt;

    if (!bestAttempt) {

        bestAttemptPanel.hidden =
            true;

        return;
    }

    bestAttemptPanel.hidden =
        false;

    bestScore.textContent =
        `${bestAttempt.score} pts`;

    bestTime.textContent =
        formatDuration(
            bestAttempt.totalTimeSpentMillis
        );

    bestHints.textContent =
        bestAttempt.hintsUsed;

    bestSkipped.textContent =
        bestAttempt.skipped;

    bestGrade.textContent =
        capitalize(
            bestAttempt.grade
        );
}

function renderPerformanceChart(
    statistics
) {

    performanceChart?.destroy();

    const context =
        document
            .getElementById(
                "performance-chart"
            )
            .getContext("2d");

    performanceChart =
        new Chart(
            context,
            {
                type: "line",

                data: {
                    labels:
                        statistics.performance.map(
                            point =>
                                `Attempt ${point.attempt}`
                        ),

                    datasets: [
                        {
                            label: "Score",

                            data:
                                statistics.performance.map(
                                    point =>
                                        point.score
                                ),

                            tension: 0.3,

                            fill: false
                        }
                    ]
                },

                options: {
                    responsive: true,

                    maintainAspectRatio: false,

                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    },

                    plugins: {
                        legend: {
                            display: false
                        }
                    }
                }
            }
        );
}

function renderAnswerChart(
    statistics
) {

    answerChart?.destroy();

    const context =
        document
            .getElementById(
                "answer-chart"
            )
            .getContext("2d");

    const breakdown =
        statistics.answerBreakdown;

    answerChart =
        new Chart(
            context,
            {
                type: "doughnut",

                data: {
                    labels: [
                        "Correct",
                        "Incorrect",
                        "Skipped"
                    ],

                    datasets: [
                        {
                            data: [
                                breakdown.correct,
                                breakdown.incorrect,
                                breakdown.skipped
                            ]
                        }
                    ]
                },

                options: {
                    responsive: true,

                    maintainAspectRatio: false,

                    plugins: {
                        legend: {
                            position: "bottom"
                        }
                    }
                }
            }
        );
}

function renderDifficultyChart(
    statistics
) {

    difficultyChart?.destroy();

    const context =
        document
            .getElementById(
                "difficulty-chart"
            )
            .getContext("2d");

    difficultyChart =
        new Chart(
            context,
            {
                type: "bar",

                data: {
                    labels:
                        statistics.difficulty.map(
                            item =>
                                capitalize(
                                    item.difficulty
                                )
                        ),

                    datasets: [
                        {
                            label: "Accuracy",

                            data:
                                statistics.difficulty.map(
                                    item =>
                                        item.percentage
                                ),

                            borderWidth: 1
                        }
                    ]
                },

                options: {
                    responsive: true,

                    maintainAspectRatio: false,

                    scales: {
                        y: {
                            beginAtZero: true,
                            max: 100,

                            ticks: {
                                callback:
                                    value =>
                                        `${value}%`
                            }
                        }
                    },

                    plugins: {
                        legend: {
                            display: false
                        }
                    }
                }
            }
        );
}

function renderStatistics(
    statistics
) {

    updateSummary(
        statistics
    );

    updateBestAttempt(
        statistics
    );

    renderPerformanceChart(
        statistics
    );

    renderAnswerChart(
        statistics
    );

    renderDifficultyChart(
        statistics
    );
}

function updateVisibility(
    statistics
) {

    const hasData =
        statistics.summary.completedAttempts > 0;

    statisticsContent.hidden =
        !hasData;

    statisticsEmpty.hidden =
        hasData;
}

function formatDuration(
    milliseconds
) {

    const totalSeconds =
        Math.round(
            milliseconds / 1000
        );

    const hours =
        Math.floor(
            totalSeconds / 3600
        );

    const minutes =
        Math.floor(
            (totalSeconds % 3600) / 60
        );

    const seconds =
        totalSeconds % 60;

    if (hours > 0) {
        return `${hours}h ${minutes}m ${seconds}s`;
    }

    if (minutes > 0) {
        return `${minutes}m ${seconds}s`;
    }

    return `${seconds}s`;
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

async function refreshStatistics() {

    try {

        const statistics =
            await loadStatistics(
                quizSelector.value
            );

        updateQuizSelector(
            statistics
        );

        renderStatistics(
            statistics
        );

        updateVisibility(
            statistics
        );

    } catch (error) {

        console.error(
            "Could not load statistics:",
            error
        );
    }
}

quizSelector.addEventListener(
    "change",
    refreshStatistics
);

refreshStatistics();
