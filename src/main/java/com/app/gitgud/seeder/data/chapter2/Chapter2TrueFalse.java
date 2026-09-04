package com.app.gitgud.seeder.data.chapter2;

import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.TrueFalseQuestion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter2TrueFalse {

    public static Map<String, List<TrueFalseQuestion>> getQuestions() {
        Map<String, List<TrueFalseQuestion>> map = new HashMap<>();
        map.put("section-2-1", section21());
        map.put("section-2-2", section22());
        map.put("section-2-3", section23());
        map.put("section-2-4", section24());
        map.put("section-2-5", section25());
        map.put("section-2-6", section26());
        map.put("section-2-7", section27());
        return map;
    }

    // -------------------------------------------------------------------------
    // Section 2.1 — What is a branch?
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section21() {
        return Arrays.asList(

                build("tf1",
                        "Creating a Git branch copies all your project files to a new location.",
                        false, "beginner",
                        "Think about why branching is so fast in Git.",
                        "A branch is just a pointer to a commit, no files are duplicated."
                ),

                build("tf2",
                        "The default branch Git creates is called `master` or `main`.",
                        true, "intermediate",
                        "Think about what Git sets up when you run git init.",
                        "Both names are used depending on the Git version and configuration."
                ),

                build("tf3",
                        "When you make a new commit on a feature branch, the main branch pointer also moves forward.",
                        false, "advanced",
                        "Think about what a branch pointer tracks.",
                        "Each branch pointer only moves when you commit on that branch. Other branches are unaffected."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.2 — Working on different branches (Git switch)
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section22() {
        return Arrays.asList(

                build("tf1",
                        "When you switch branches, your files update to reflect the state of the new branch.",
                        true, "beginner",
                        "Think about the purpose of switching branches.",
                        "Git updates your working directory to match the target branch's state."
                ),

                build("tf2",
                        "`git switch -c feature` creates a new branch and switches to it in one step.",
                        true, "intermediate",
                        "Think about what the -c flag stands for.",
                        "-c stands for create, it combines branch creation and switching."
                ),

                build("tf3",
                        "`git switch` was introduced because `git checkout` was too slow.",
                        false, "advanced",
                        "Think about the design reason, not a performance reason.",
                        "git checkout was replaced because it performed too many unrelated actions, making it confusing."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.3 — Git Merge
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section23() {
        return Arrays.asList(

                build("tf1",
                        "When you run `git merge feature` from main, the changes from feature are brought into main.",
                        true, "beginner",
                        "Think about the direction of the merge.",
                        "You merge into the branch you are currently on."
                ),

                build("tf2",
                        "After merging a feature branch into main, Git automatically deletes the feature branch.",
                        false, "intermediate",
                        "Think about whether merge and delete are the same operation.",
                        "You must manually delete the branch with `git branch -d feature` after merging."
                ),

                build("tf3",
                        "A fast-forward merge happens when both branches have new commits since they diverged.",
                        false, "advanced",
                        "Think about what makes a merge 'fast-forward'.",
                        "A fast-forward merge only happens when the target branch has not moved since the feature was created, so no merge commit is needed."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.4 — Merge Conflicts
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section24() {
        return Arrays.asList(

                build("tf1",
                        "A merge conflict occurs when two branches change the same part of the same file.",
                        true, "beginner",
                        "Think about what Git cannot decide automatically.",
                        "When both branches edit the same lines, Git doesn't know which version to keep."
                ),

                build("tf2",
                        "After resolving a merge conflict, you only need to commit, no need to run `git add`.",
                        false, "intermediate",
                        "Think about the normal Git workflow after editing a file.",
                        "You must stage the resolved file with git add before Git knows the conflict is fixed."
                ),

                build("tf3",
                        "`git merge --abort` cancels a merge in progress and returns to the state before it started.",
                        true, "advanced",
                        "Think about what abort does to any ongoing operation.",
                        "This is the safe escape hatch when a merge goes wrong."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.5 — Git Rebase
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section25() {
        return Arrays.asList(

                build("tf1",
                        "`git rebase main` run from a feature branch moves the feature commits on top of the latest main commit.",
                        true, "beginner",
                        "Think about what 'rebase' means for the starting point of a branch.",
                        "Rebasing changes the base of your branch to the tip of another branch."
                ),

                build("tf2",
                        "It is safe to rebase commits that have already been pushed to a shared repository.",
                        false, "intermediate",
                        "Think about what your teammates already have on their machines.",
                        "Rebasing rewrites history. If others have pulled the original commits, their history no longer matches yours."
                ),

                build("tf3",
                        "Interactive rebase (`git rebase -i`) lets you edit, squash, reorder, or drop commits.",
                        true, "advanced",
                        "Think about what the -i flag enables.",
                        "Interactive rebase opens an editor where you choose what to do with each commit."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.6 — Git Stash
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section26() {
        return Arrays.asList(

                build("tf1",
                        "`git stash` permanently deletes your uncommitted changes.",
                        false, "beginner",
                        "Think about the word 'stash', where do you put things you want to keep?",
                        "Stash saves your changes temporarily. You can restore them later with git stash pop."
                ),

                build("tf2",
                        "`git stash pop` restores the most recently stashed changes and removes them from the stash list.",
                        true, "intermediate",
                        "Think about what 'pop' means in a stack data structure.",
                        "Pop retrieves and removes the top item, here the most recent stash."
                ),

                build("tf3",
                        "By default, `git stash` includes untracked files in the stash.",
                        false, "advanced",
                        "Think about what Git tracks by default.",
                        "Untracked files are excluded by default. You need the -u flag to include them."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.7 — Git Tags
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section27() {
        return Arrays.asList(

                build("tf1",
                        "A Git tag moves forward automatically as you create new commits, just like a branch.",
                        false, "beginner",
                        "Think about the key difference between tags and branches.",
                        "Tags are fixed markers. They always point to the same commit and never move."
                ),

                build("tf2",
                        "Annotated tags store extra metadata such as a message, author, and date.",
                        true, "intermediate",
                        "Think about what 'annotated' means.",
                        "Unlike lightweight tags, annotated tags are full Git objects with additional information."
                ),

                build("tf3",
                        "Tags are automatically pushed to a remote repository when you run `git push`.",
                        false, "advanced",
                        "Think about what git push sends by default.",
                        "git push only pushes commits. Tags require `git push origin --tags` or a specific tag name."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------------------

    private static TrueFalseQuestion build(
            String id,
            String text,
            boolean correctAnswer,
            String difficulty,
            String hint1,
            String hint2
    ) {
        TrueFalseQuestion q = new TrueFalseQuestion();
        q.setId(id);
        q.setText(text);
        q.setCorrectAnswer(correctAnswer);
        q.setDifficulty(QuestionDifficulty.fromValue(difficulty));
        q.setHints(Arrays.asList(hint1, hint2));
        return q;
    }
}