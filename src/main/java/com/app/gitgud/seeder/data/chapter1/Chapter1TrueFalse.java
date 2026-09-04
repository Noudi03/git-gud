package com.app.gitgud.seeder.data.chapter1;

import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.TrueFalseQuestion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter1TrueFalse {

    public static Map<String, List<TrueFalseQuestion>> getQuestions() {
        Map<String, List<TrueFalseQuestion>> map = new HashMap<>();
        map.put("section-1-1", section11());
        map.put("section-1-2", section12());
        map.put("section-1-3", section13());
        map.put("section-1-4", section14());
        map.put("section-1-5", section15());
        map.put("section-1-6", section16());
        map.put("section-1-7", section17());
        return map;
    }

    // -------------------------------------------------------------------------
    // Section 1.1 — What is Git?
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section11() {
        return Arrays.asList(

                build("tf1",
                        "`Git` is a version control system that tracks changes in your files over time.",
                        true, "beginner",
                        "Think about what 'version control' means.",
                        "That is exactly the core purpose of `Git`."
                ),

                build("tf2",
                        "`Git` requires a constant internet connection to save your work.",
                        false, "intermediate",
                        "Think about whether `Git` is centralized or distributed.",
                        "`Git` is distributed, you can commit, branch, and view history entirely offline."
                ),

                build("tf3",
                        "In a distributed version control system, only one central server holds the full repository history.",
                        false, "advanced",
                        "Think about what 'distributed' means for every developer.",
                        "Every developer holds a full copy of the repository, including its entire history."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.2 — Initial Configuration
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section12() {
        return Arrays.asList(

                build("tf1",
                        "`Git` attaches your name and email to every commit you make.",
                        true, "beginner",
                        "Think about why identity matters when collaborating.",
                        "`Git` records author metadata on each commit so collaborators know who made each change."
                ),

                build("tf2",
                        "The `--global` flag applies your `Git` configuration only to the current project.",
                        false, "intermediate",
                        "Think about what 'global' means versus 'local'.",
                        "`--global` applies to all projects for your user on the machine; `--local` is project-specific."
                ),

                build("tf3",
                        "A local repository configuration takes precedence over the global configuration.",
                        true, "advanced",
                        "Think about which level is more specific.",
                        "The more specific the level, the higher its priority: local overrides global, which overrides system."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.3 — Git Init
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section13() {
        return Arrays.asList(

                build("tf1",
                        "Running `git init` creates a hidden folder called `.git` inside your project.",
                        true, "beginner",
                        "Think about what `Git` needs to store its history.",
                        "The `.git` folder is `Git`'s internal database for the repository."
                ),

                build("tf2",
                        "Deleting the `.git` folder also deletes your project files.",
                        false, "intermediate",
                        "Think about what `.git` contains versus your working files.",
                        "Deleting `.git` removes `Git` tracking and history, but your actual project files remain intact."
                ),

                build("tf3",
                        "The `HEAD` file inside `.git` points to the current branch or commit.",
                        true, "advanced",
                        "Think about what `HEAD` is used for during navigation.",
                        "`HEAD` tells `Git` where you currently are in the repository history."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.4 — Git Add
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section14() {
        return Arrays.asList(

                build("tf1",
                        "`git add` permanently saves your changes into `Git`'s history.",
                        false, "beginner",
                        "Think about the step that comes after `git add`.",
                        "`git add` only moves files to the staging area. A commit is required to save them permanently."
                ),

                build("tf2",
                        "If you modify a file after running `git add` but before committing, the commit will contain the version that was staged, not the latest version.",
                        true, "intermediate",
                        "Think about when the snapshot is taken.",
                        "`git add` captures the file at that exact moment. Later edits are not included unless you run `git add` again."
                ),

                build("tf3",
                        "`git add -p` lets you stage specific parts of a file rather than the whole file.",
                        true, "advanced",
                        "Think about what the `-p` flag stands for.",
                        "The `-p` (patch) flag opens an interactive mode where you can choose which hunks to stage."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.5 — Git Commit
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section15() {
        return Arrays.asList(

                build("tf1",
                        "`git commit` uploads your changes to GitHub.",
                        false, "beginner",
                        "Think about what `git commit` does versus `git push`.",
                        "`git commit` saves changes locally into `Git`'s history. `git push` is what sends them to a remote."
                ),

                build("tf2",
                        "`git commit --amend` can be safely used on commits that have already been pushed to a shared repository.",
                        false, "intermediate",
                        "Think about what `amend` does to the commit.",
                        "Amending rewrites the commit and its hash. This causes problems for teammates who already have the original."
                ),

                build("tf3",
                        "Every commit in `Git` is identified by a unique SHA-1 hash.",
                        true, "advanced",
                        "Think about how `Git` uniquely identifies each snapshot.",
                        "`Git` generates a 40-character hexadecimal SHA-1 hash for every commit."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.6 — Log and Diff
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section16() {
        return Arrays.asList(

                build("tf1",
                        "`git log` shows the current status of your working directory.",
                        false, "beginner",
                        "Think about the difference between history and current state.",
                        "`git log` shows the commit history. `git status` shows the current state of your working directory."
                ),

                build("tf2",
                        "In `git diff` output, lines starting with `+` represent additions and lines starting with `-` represent deletions.",
                        true, "intermediate",
                        "Think about the mathematical meaning of `+` and `-`.",
                        "This is the unified diff format: `+` for added lines, `-` for removed lines."
                ),

                build("tf3",
                        "`git diff --staged` shows changes that have not yet been staged.",
                        false, "advanced",
                        "Think about what `--staged` refers to.",
                        "`--staged` (or `--cached`) shows changes already in the staging area, not unstaged ones."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.7 — Ignore Files
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section17() {
        return Arrays.asList(

                build("tf1",
                        "Adding a file to `.gitignore` automatically removes it from `Git` tracking if it was already committed.",
                        false, "beginner",
                        "Think about what happens to files already tracked by `Git`.",
                        "`Git` continues tracking already-committed files. You must run `git rm --cached` to untrack them first."
                ),

                build("tf2",
                        "The pattern `*.log` in a `.gitignore` file ignores all files with a `.log` extension.",
                        true, "intermediate",
                        "Think about what the `*` wildcard matches.",
                        "`*` matches any sequence of characters, so `*.log` catches any filename ending in `.log`."
                ),

                build("tf3",
                        "You can use `git add -f` to force-stage a file that is listed in `.gitignore`.",
                        true, "advanced",
                        "Think about what the `-f` flag stands for.",
                        "The `--force` (`-f`) flag tells `Git` to stage the file even though it matches an ignore rule."
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
