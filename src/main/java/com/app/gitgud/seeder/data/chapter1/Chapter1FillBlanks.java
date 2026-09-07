package com.app.gitgud.seeder.data.chapter1;

import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.QuestionDifficulty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter1FillBlanks {

    public static Map<String, List<FillInTheBlankQuestion>> getQuestions() {
        Map<String, List<FillInTheBlankQuestion>> map = new HashMap<>();
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

    private static List<FillInTheBlankQuestion> section11() {
        return Arrays.asList(

                build("fb1",
                        "Git is a ___ control system that tracks changes in your files over time.",
                        "version", "easy",
                        "Think about what type of control Git provides.",
                        "The name of the category Git belongs to contains this word."
                ),

                build("fb2",
                        "Git is described as ___ because every developer has a full copy of the repository.",
                        "distributed", "medium",
                        "Think about the opposite of centralized.",
                        "This word describes a system where no single point holds all the data."
                ),

                build("fb3",
                        "With Git, you can save ___ of your work at any point in time.",
                        "snapshots", "hard",
                        "Think about a photography analogy.",
                        "Git captures the full state of your project at a given moment, like a photo."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.2 — Initial Configuration
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section12() {
        return Arrays.asList(

                build("fb1",
                        "To set your `Git` username for all projects on your computer, you use the ___ flag.",
                        "--global", "easy",
                        "Think about what level of configuration applies to all your projects.",
                        "The flag is the same word as 'applies to everything'."
                ),

                build("fb2",
                        "You can view your current `Git` configuration by running `git config ___`.",
                        "--list", "medium",
                        "Think about a flag that shows all entries.",
                        "This flag prints all configuration key-value pairs."
                ),

                build("fb3",
                        "Global `Git` configuration is typically stored in a file called `___`.",
                        "~/.gitconfig", "hard",
                        "Think about where user-level config files are usually stored on Unix systems.",
                        "It lives in your home directory and starts with a dot."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.3 — Git Init
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section13() {
        return Arrays.asList(

                build("fb1",
                        "Running `git init` creates a hidden folder called `___` inside your project.",
                        ".git", "easy",
                        "Hidden folders on Unix start with a dot.",
                        "The folder name is the tool name prefixed with a dot."
                ),

                build("fb2",
                        "After `git init`, the folder becomes a `Git` ___ .",
                        "repository", "medium",
                        "Think about the term used for a project tracked by `Git`.",
                        "This is the standard word for a `Git`-tracked project folder."
                ),

                build("fb3",
                        "The `___` folder inside `.git` stores Git's objects, including file contents and commits.",
                        "objects/", "hard",
                        "Think about what Git calls the raw stored data.",
                        "The folder name reflects what Git stores inside it."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.4 — Git Add
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section14() {
        return Arrays.asList(

                build("fb1",
                        "`git add` moves your changed files to the ___ area.",
                        "staging", "easy",
                        "Think about the step between editing and committing.",
                        "This area is also called the index."
                ),

                build("fb2",
                        "To stage all changed files in the current directory at once, you run `git add ___`.",
                        ".", "medium",
                        "In the terminal, a single character refers to the current directory.",
                        "It is the shortest possible path reference."
                ),

                build("fb3",
                        "The staging area is also known as the `___`.",
                        "index", "hard",
                        "Think about the technical name Git uses internally.",
                        "It is a single noun used by Git for the staging area."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.5 — Git Commit
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section15() {
        return Arrays.asList(

                build("fb1",
                        "`git commit` saves your staged changes as a ___ of your project.",
                        "snapshot", "easy",
                        "Think about a photography analogy.",
                        "Git captures the complete state of your staged files at that moment."
                ),

                build("fb2",
                        "Good commit messages use the ___ mood, for example `Fix login bug` instead of `Fixed login bug`.",
                        "imperative", "medium",
                        "Think about giving a command or instruction.",
                        "This grammatical mood is used when giving orders or directions."
                ),

                build("fb3",
                        "The chain of parent commit pointers in Git forms a `DAG`, which stands for Directed ___ Graph.",
                        "Acyclic", "hard",
                        "Think about the property that means history never loops back.",
                        "This word means the graph has no cycles."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.6 — Log and Diff
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section16() {
        return Arrays.asList(

                build("fb1",
                        "`git log ___` shows each commit compressed into a single line.",
                        "--oneline", "easy",
                        "Think about the flag name that describes compact output.",
                        "The flag name describes the format: one line per commit."
                ),

                build("fb2",
                        "`git diff` without arguments shows changes that are not yet ___.",
                        "staged", "medium",
                        "Think about the state of changes before `git add`.",
                        "These are changes in your working directory that haven't been added yet."
                ),

                build("fb3",
                        "To visualize the branch tree in the terminal, you combine `git log` with `--oneline`, `--graph`, and ___.",
                        "--all", "hard",
                        "Think about what flag makes Git show every branch, not just the current one.",
                        "This flag ensures all branches are included in the output."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.7 — Ignore Files
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section17() {
        return Arrays.asList(

                build("fb1",
                        "The file that tells Git which files and folders to ignore is called `___`.",
                        ".gitignore", "easy",
                        "Think about the tool name combined with the word ignore.",
                        "It lives in your project root and starts with a dot."
                ),

                build("fb2",
                        "To stop tracking an already-tracked file without deleting it, you run `git rm ___ filename`.",
                        "--cached", "medium",
                        "Think about a flag that affects only Git's memory, not the actual file.",
                        "This flag targets the `index` (staging area / cache) rather than the working directory."
                ),

                build("fb3",
                        "In `.gitignore`, the `!` prefix creates an ___ to an ignore rule.",
                        "exception", "hard",
                        "Think about what the `!` symbol typically means in programming.",
                        "It negates or reverses the rule that would otherwise apply."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------------------

    private static FillInTheBlankQuestion build(
            String id,
            String text,
            String correctAnswer,
            String difficulty,
            String hint1,
            String hint2
    ) {
        FillInTheBlankQuestion q = new FillInTheBlankQuestion();
        q.setId(id);
        q.setText(text);
        q.setCorrectAnswer(correctAnswer);
        q.setDifficulty(QuestionDifficulty.fromValue(difficulty));
        q.setHints(Arrays.asList(hint1, hint2));
        return q;
    }
}
