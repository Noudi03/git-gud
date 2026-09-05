package com.app.gitgud.service.selection;

import com.app.gitgud.model.Question;
import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.User;
import com.app.gitgud.model.UserTier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class WeightedQuestionSelectionStrategy
        implements QuestionSelectionStrategy {

    private final UserTierResolver userTierResolver;

    private final Map<UserTier, Map<QuestionDifficulty, Integer>>
            weights;

    private final Random random =
            new Random();

    public WeightedQuestionSelectionStrategy(
            UserTierResolver userTierResolver,

            @Value("${gitgud.question-selection.novice.easy}")
            int noviceEasy,

            @Value("${gitgud.question-selection.novice.medium}")
            int noviceMedium,

            @Value("${gitgud.question-selection.novice.hard}")
            int noviceHard,

            @Value("${gitgud.question-selection.experienced.easy}")
            int experiencedEasy,

            @Value("${gitgud.question-selection.experienced.medium}")
            int experiencedMedium,

            @Value("${gitgud.question-selection.experienced.hard}")
            int experiencedHard
    ) {

        this.userTierResolver =
                userTierResolver;

        this.weights =
                new EnumMap<>(
                        UserTier.class
                );

        this.weights.put(
                UserTier.NOVICE,
                createWeights(
                        noviceEasy,
                        noviceMedium,
                        noviceHard
                )
        );

        this.weights.put(
                UserTier.EXPERIENCED,
                createWeights(
                        experiencedEasy,
                        experiencedMedium,
                        experiencedHard
                )
        );
    }

    @Override
    public List<Question> selectQuestions(
            User user,
            List<Question> questions,
            int questionCount
    ) {

        if (
                questionCount <= 0 ||
                        questions == null ||
                        questions.isEmpty()
        ) {

            return List.of();
        }

        UserTier tier =
                userTierResolver.resolve(user);

        Map<QuestionDifficulty, Integer>
                difficultyWeights =
                weights.get(tier);

        List<Question> remaining =
                new ArrayList<>(
                        questions
                );

        List<Question> selected =
                new ArrayList<>(
                        questionCount
                );

        if (
                remaining.size() < questionCount
        ) {

            return List.of();
        }

        for (
                int i = 0;
                i < questionCount;
                i++
        ) {

            QuestionDifficulty difficulty =
                    selectDifficulty(
                            remaining,
                            difficultyWeights
                    );

            Question question =
                    selectQuestion(
                            remaining,
                            difficulty
                    );

            if (question == null) {

                question =
                        selectRandomQuestion(
                                remaining
                        );
            }

            if (question == null) {
                return List.of();
            }

            selected.add(
                    question
            );

            remaining.remove(
                    question
            );
        }

        return selected;
    }

    private QuestionDifficulty selectDifficulty(
            List<Question> questions,
            Map<QuestionDifficulty, Integer> weights
    ) {

        List<QuestionDifficulty>
                availableDifficulties =
                questions.stream()
                        .map(
                                Question::getDifficulty
                        )
                        .distinct()
                        .toList();

        int totalWeight =
                availableDifficulties.stream()
                        .mapToInt(
                                difficulty ->
                                        weights.getOrDefault(
                                                difficulty,
                                                0
                                        )
                        )
                        .sum();

        if (totalWeight <= 0) {

            return availableDifficulties.get(
                    random.nextInt(
                            availableDifficulties.size()
                    )
            );
        }

        int value =
                random.nextInt(
                        totalWeight
                );

        int cumulative = 0;

        for (
                QuestionDifficulty difficulty :
                availableDifficulties
        ) {

            cumulative +=
                    weights.getOrDefault(
                            difficulty,
                            0
                    );

            if (value < cumulative) {
                return difficulty;
            }
        }

        return availableDifficulties.get(
                availableDifficulties.size() - 1
        );
    }

    private Question selectQuestion(
            List<Question> questions,
            QuestionDifficulty difficulty
    ) {

        List<Question> candidates =
                questions.stream()
                        .filter(
                                question ->
                                        question.getDifficulty()
                                                == difficulty
                        )
                        .toList();

        if (candidates.isEmpty()) {
            return null;
        }

        return candidates.get(
                random.nextInt(
                        candidates.size()
                )
        );
    }

    private Question selectRandomQuestion(
            List<Question> questions
    ) {

        if (questions.isEmpty()) {
            return null;
        }

        return questions.get(
                random.nextInt(
                        questions.size()
                )
        );
    }

    private Map<QuestionDifficulty, Integer>
    createWeights(
            int easy,
            int medium,
            int hard
    ) {

        Map<QuestionDifficulty, Integer>
                result =
                new EnumMap<>(
                        QuestionDifficulty.class
                );

        result.put(
                QuestionDifficulty.EASY,
                Math.max(0, easy)
        );

        result.put(
                QuestionDifficulty.MEDIUM,
                Math.max(0, medium)
        );

        result.put(
                QuestionDifficulty.HARD,
                Math.max(0, hard)
        );

        return result;
    }
}
