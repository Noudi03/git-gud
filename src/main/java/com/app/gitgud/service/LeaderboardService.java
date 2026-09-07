package com.app.gitgud.service;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.model.User;
import com.app.gitgud.model.QuizResult;
import com.app.gitgud.model.dto.LeaderboardDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaderboardService {

    private final UserService userService;
    private final QuizAttemptService quizAttemptService;
    private final QuizResultService quizResultService;

    public LeaderboardService(
            UserService userService,
            QuizAttemptService quizAttemptService,
            QuizResultService quizResultService
    ) {
        this.userService =
                userService;

        this.quizAttemptService =
                quizAttemptService;

        this.quizResultService =
                quizResultService;
    }

    public LeaderboardDto getLeaderboard(
            String selectedQuizKey
    ) throws Exception {

        List<User> users =
                userService.getUsers();

        Map<String, Quiz> quizzes =
                new LinkedHashMap<>();

        if (
                selectedQuizKey == null
                        || selectedQuizKey.isBlank()
        ) {

            List<LeaderboardDto.Entry> entries =
                    users.stream()
                            .sorted(
                                    Comparator.comparingInt(
                                            User::getRating
                                    ).reversed()
                            )
                            .limit(10)
                            .map(
                                    user ->
                                            new LeaderboardDto.Entry(
                                                    0,
                                                    user.getUsername(),
                                                    user.getRating()
                                            )
                            )
                            .toList();

            List<LeaderboardDto.Entry> rankedEntries =
                    new ArrayList<>();

            for (
                    int index = 0;
                    index < entries.size();
                    index++
            ) {

                LeaderboardDto.Entry entry =
                        entries.get(index);

                rankedEntries.add(
                        new LeaderboardDto.Entry(
                                index + 1,
                                entry.getUsername(),
                                entry.getScore()
                        )
                );
            }

            collectAvailableQuizzes(
                    users,
                    quizzes
            );

            return new LeaderboardDto(
                    createQuizSummaries(quizzes),
                    rankedEntries
            );
        }

        List<LeaderboardScore> scores =
                new ArrayList<>();

        for (User user : users) {

            List<QuizAttempt> attempts =
                    quizAttemptService.getAttempts(
                            user.getId()
                    );

            for (QuizAttempt attempt : attempts) {

                if (
                        attempt.getStatus()
                                != QuizAttemptStatus.COMPLETED
                ) {
                    continue;
                }

                Quiz quiz =
                        quizAttemptService.getQuiz(
                                attempt
                        );

                String quizKey =
                        createQuizKey(
                                quiz
                        );

                quizzes.put(
                        quizKey,
                        quiz
                );

                if (
                        !selectedQuizKey.equals(
                                quizKey
                        )
                ) {
                    continue;
                }

                QuizResult result =
                        quizResultService.getResult(
                                user.getId(),
                                attempt.getId()
                        );

                scores.add(
                        new LeaderboardScore(
                                user.getUsername(),
                                result.getScore()
                        )
                );
            }
        }

        List<LeaderboardScore> topScores =
                scores.stream()
                        .sorted(
                                Comparator.comparingInt(
                                        LeaderboardScore::getScore
                                ).reversed()
                        )
                        .limit(10)
                        .toList();

        List<LeaderboardDto.Entry> entries =
                new ArrayList<>();

        for (
                int index = 0;
                index < topScores.size();
                index++
        ) {

            LeaderboardScore score =
                    topScores.get(index);

            entries.add(
                    new LeaderboardDto.Entry(
                            index + 1,
                            score.username,
                            score.score
                    )
            );
        }

        return new LeaderboardDto(
                createQuizSummaries(quizzes),
                entries
        );
    }

    private void collectAvailableQuizzes(
            List<User> users,
            Map<String, Quiz> quizzes
    ) throws Exception {

        for (User user : users) {

            List<QuizAttempt> attempts =
                    quizAttemptService.getAttempts(
                            user.getId()
                    );

            for (QuizAttempt attempt : attempts) {

                if (
                        attempt.getStatus()
                                != QuizAttemptStatus.COMPLETED
                ) {
                    continue;
                }

                Quiz quiz =
                        quizAttemptService.getQuiz(
                                attempt
                        );

                quizzes.put(
                        createQuizKey(quiz),
                        quiz
                );
            }
        }
    }

    private List<LeaderboardDto.QuizSummary>
    createQuizSummaries(
            Map<String, Quiz> quizzes
    ) {

        return quizzes.entrySet()
                .stream()
                .map(entry -> {

                    Quiz quiz =
                            entry.getValue();

                    return new LeaderboardDto.QuizSummary(
                            entry.getKey(),
                            quiz.getId(),
                            quiz.getTitle(),
                            quiz.getType() == null
                                    ? null
                                    : quiz.getType().getValue(),
                            createContextDto(
                                    quiz.getContext()
                            )
                    );
                })
                .sorted(
                        Comparator.comparing(
                                LeaderboardDto.QuizSummary::getTitle
                        )
                )
                .toList();
    }

    private String createQuizKey(
            Quiz quiz
    ) {

        QuizContext context =
                quiz.getContext();

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            return "section:"
                    + sectionContext.getChapterId()
                    + ":"
                    + sectionContext.getSectionId()
                    + ":"
                    + quiz.getId();
        }

        if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            return "chapter:"
                    + chapterContext.getChapterId()
                    + ":"
                    + quiz.getId();
        }

        if (
                context instanceof GlobalQuizContext
        ) {

            return "global:"
                    + quiz.getId();
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }

    private LeaderboardDto.QuizContextDto
    createContextDto(
            QuizContext context
    ) {

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            return new LeaderboardDto.QuizContextDto(
                    "section",
                    sectionContext.getChapterId(),
                    sectionContext.getSectionId()
            );
        }

        if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            return new LeaderboardDto.QuizContextDto(
                    "chapter",
                    chapterContext.getChapterId(),
                    null
            );
        }

        if (
                context instanceof GlobalQuizContext
        ) {

            return new LeaderboardDto.QuizContextDto(
                    "global",
                    null,
                    null
            );
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }

    private static class LeaderboardScore {

        private final String username;
        private final int score;

        private LeaderboardScore(
                String username,
                int score
        ) {
            this.username = username;
            this.score = score;
        }

        private int getScore() {
            return score;
        }
    }
}
