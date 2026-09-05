package com.app.gitgud.seeder.data.chapter1;

import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.QuestionDifficulty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The initial form of this file, more specifically the getQuestions() and sectionXX() parts, was generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter1MultipleChoice {

    public static Map<String, List<MultipleChoiceQuestion>> getQuestions() {
        Map<String, List<MultipleChoiceQuestion>> map = new HashMap<>();
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

    private static List<MultipleChoiceQuestion> section11() {
        return Arrays.asList(

                build("mc1",
                        "What is git mainly used for?",
                        opts("Managing databases", "Storing files",
                                "Tracking changes in files over time", "Backing up a project"),
                        "C", "beginner",
                        "Think about what 'version' means in 'version control'.",
                        "Git was created by developers for developers. Think which one of the answers is the most common problem they face day to day."
                ),

                build("mc2",
                        "What happens if you break your code and you use git?",
                        opts("You lose everything",
                                "You can go back to a previous saved version",
                                "Git automatically fixes your mistakes",
                                "Git provides you with suggestions to fix your mistakes"),
                        "B", "intermediate",
                        "Think about the 'save history' analogy.",
                        "Git stores snapshots of your project at different points in time."
                ),

                build("mc3",
                        "What makes git distributed?",
                        opts("It works online",
                                "It distributes files across multiple hard drives",
                                "Every developer has a full copy of the repository",
                                "Multiple people host different parts of the project"),
                        "C", "advanced",
                        "Think about what 'decentralized' means.",
                        "If the main server goes down, can developers still work?"
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.2 — Initial Configuration
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section12() {
        return Arrays.asList(

                build("mc1",
                        "Why do you need to add your name and email to git?",
                        opts("So you can sign up and use Github",
                                "To be able to run git",
                                "Because git attaches your identity to every commit",
                                "To access private repositories only assigned to you"),
                        "C", "beginner",
                        "Think about what git does every time you save your work.",
                        "If multiple people work on the same project, how do you know who made each change?"
                ),
                build("mc2",
                        "Why how do you set your name in git?",
                        opts("git config --name \"Name\"",
                                "git name --config \"Name\"",
                                "git config --global user.name \"Name\"",
                                "git config --global --name \"Name\""),
                        "C", "intermediate",
                        "The configuration commands start with git config",
                        "It is a user configuration"
                ),

                build("mc3",
                        "Which configuration level overrides the global settings for a single project?",
                        opts("`--system`", "`--local`", "`--global`", "`--project`"),
                        "B", "advanced",
                        "There are 3 levels: system, global, local. Which one is the most specific?",
                        "The more specific the level, the higher its priority over broader settings."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.3 — Git Init
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section13() {
        return Arrays.asList(

                build("mc1",
                        "Which folder is created after running the command `git init`?",
                        opts("`.github`", "`.gitconfig`", "`refs`", "`.git`"),
                        "D", "beginner",
                        "Git creates a hidden folder. Hidden folders start with a dot.",
                        "The folder name is related to the tool itself."
                ),

                build("mc2",
                        "What happens after deleting the .git folder?",
                        opts("Git stops tracking the project and your files get deleted",
                                "Git stops tracking the project and your files remain",
                                "Git automatically creates another .git folder",
                                "Nothing changes"),
                        "B", "intermediate",
                        "The .git folder contains the history, not your actual files.",
                        "Think of .git as the memory of git. Your files are separate from that memory."
                ),

                build("mc3",
                        "Which file inside the .git directory points to your current branch?",
                        opts("`HEAD`", "`refs`", "`objects`", "`config`"),
                        "A", "advanced",
                        "'You are here'. Which file in .git tells git where you currently are?",
                        "Think about what HEAD means — it is a common term in navigation."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.4 — Git Add
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section14() {
        return Arrays.asList(

                build("mc1",
                        "What does `git add` do?",
                        opts("Upload files to github", "Save your files permanently",
                                "Move files to the staging area", "Commit files"),
                        "C", "beginner",
                        "Think about the step before saving. What do you do first?",
                        "git add prepares files, but does not save them permanently."
                ),

                build("mc2",
                        "Which command stages all changed files at once?",
                        opts("`git stage --all`", "`git add --all`", "`git add .`", "`git stage all`"),
                        "C", "intermediate",
                        "In the terminal, '.' usually refers to the current directory.",
                        "You want to add everything in the current folder."
                ),

                build("mc3",
                        "You run `git add file1`, then edit `file1`, then run `git commit`. What gets saved?",
                        opts("The latest version of the file",
                                "Both versions",
                                "Nothing, you need to run git add again",
                                "The version of the file at the time of git add"),
                        "D", "intermediate",
                        "Think about what 'snapshot' means. When was the snapshot taken?",
                        "git add takes a picture of the file at that exact moment. Changes after that are not in the snapshot."
                ),

                build("mc4",
                        "Which flag allows you to stage specific parts of a file?",
                        opts("`git add --section`", "`git add -p`", "`git add --chunk`", "`git add --select`"),
                        "B", "advanced",
                        "The flag stands for 'patch'.",
                        "The flag is a single letter, think about the first letter of 'patch'."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.5 — Git Commit
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section15() {
        return Arrays.asList(

                build("mc1",
                        "What does `git commit` do?",
                        opts("Uploads your files to github",
                                "Stages all changed files",
                                "Permanently saves your staged changes into git's history",
                                "Creates a new branch"),
                        "C", "beginner",
                        "This is the step after staging. What is the final goal?",
                        "You want to permanently save your current staged changes."
                ),

                build("mc2",
                        "Which of the following is the most correct commit message?",
                        opts("Fixed login bug", "Fixes", "Fix login bug", "Fix stuff"),
                        "C", "intermediate",
                        "Use imperative mood — like giving a command.",
                        "A good commit message is short, specific and uses imperative mood (e.g. 'Fix', not 'Fixed')."
                ),

                build("mc3",
                        "What does `git commit --amend` do?",
                        opts("Edits the most recent commit",
                                "Merges the last 2 commits",
                                "Deletes the last commit permanently",
                                "Changes the branch of a commit"),
                        "A", "intermediate",
                        "'Amend' means to make corrections to something.",
                        "It only affects the most recent commit, not older ones."
                ),

                build("mc4",
                        "What unique identifier does git assign to every commit?",
                        opts("A timestamp", "A random sequential number",
                                "An md5 hash", "A sha-1 hash"),
                        "D", "advanced",
                        "It is a cryptographic hash function — not MD5, not a timestamp.",
                        "It produces a 40-character hexadecimal string."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.6 — Log and Diff
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section16() {
        return Arrays.asList(

                build("mc1",
                        "What does `git log` do?",
                        opts("Shows the current status of your files",
                                "Shows the differences between two files",
                                "Shows a list of all commits in the repository",
                                "Creates a new branch"),
                        "C", "beginner",
                        "Think about what 'log' means in everyday language — like a ship's log or a diary.",
                        "It shows you the past, not the current state of files."
                ),

                build("mc2",
                        "What does `git log --oneline` do differently compared to `git log`?",
                        opts("Shows only the last commit in 1 line",
                                "Shows each commit compressed into one line",
                                "Shows only the commit messages",
                                "Filters commits by date"),
                        "B", "intermediate",
                        "What does 'oneline' suggest about the output of the command?",
                        "It shows the same information as git log, just more compact."
                ),

                build("mc3",
                        "What does `git diff` show when run without any arguments?",
                        opts("Changes between 2 branches",
                                "Changes not yet staged",
                                "The full commit history",
                                "Changes already staged"),
                        "B", "intermediate",
                        "Where are your changes before you run git add?",
                        "'diff' shows the difference between your working directory and the staging area."
                ),

                build("mc4",
                        "In `git diff` output, what does a line starting with `+` mean?",
                        opts("A line that was added", "A line that was deleted",
                                "A line that was modified", "An unchanged line"),
                        "A", "advanced",
                        "Just like maths: + means something was added, - means something was removed.",
                        "Red lines were removed, green lines were added. Which symbol matches the green lines?"
                ),

                build("mc5",
                        "Which command shows a visual graph of all branches in the terminal?",
                        opts("`git log --oneline --graph --all`",
                                "`git log --tree`",
                                "`git log --visual --all`",
                                "`git branch --show --graph`"),
                        "A", "advanced",
                        "You need 3 arguments combined.",
                        "1st argument for compact view, 2nd for graph, 3rd for all branches."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 1.7 — Ignore Files
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section17() {
        return Arrays.asList(

                build("mc1",
                        "What does `.gitignore` do?",
                        opts("Deletes files from the project",
                                "Tells git which files not to track",
                                "Stores git configuration settings",
                                "Commits files"),
                        "B", "beginner",
                        "What does 'ignore' mean?",
                        "It is a list of files and folders that git should pretend don't exist."
                ),

                build("mc2",
                        "Which of the following files belongs in the `.gitignore` file?",
                        opts("`index.html`", "`README.md`", "`.env`", "`style.css`"),
                        "C", "intermediate",
                        "Think about sensitive information that should never be shared publicly.",
                        "Files containing passwords, API keys or secrets should be ignored. Which file usually contains those?"
                ),

                build("mc3",
                        "What does `*.log` inside a `.gitignore` file mean?",
                        opts("Ignore only the file named `log`",
                                "Ignore the folder named `log`",
                                "Ignore files starting with ``log`",
                                "Ignore all files ending in `.log`"),
                        "D", "intermediate",
                        "* is a wildcard — it matches anything.",
                        "It means any filename combined with a .log extension."
                ),

                build("mc4",
                        "Which command untracks a file without deleting it from your computer?",
                        opts("`git remove --now filename`",
                                "`git rm --cached filename`",
                                "`git untrack filename`",
                                "`git ignore filename`"),
                        "B", "advanced",
                        "You want to remove it from git's memory, not from your computer.",
                        "The command uses 'rm' with a special flag that affects only the cache, not the actual file."
                )
        );
    }

    // Builders

    private static MultipleChoiceQuestion build(String id, String text, Map<String, String> options,
                                                String correctAnswer, String difficulty, String hint1, String hint2) {
        MultipleChoiceQuestion q = new MultipleChoiceQuestion();
        q.setId(id);
        q.setText(text);
        q.setOptions(options);
        q.setCorrectAnswer(correctAnswer);
        q.setDifficulty(QuestionDifficulty.fromValue(difficulty));
        q.setHints(Arrays.asList(hint1, hint2));
        return q;
    }

    private static Map<String, String> opts(String a, String b, String c, String d) {
        Map<String, String> map = new HashMap<>();
        map.put("A", a);
        map.put("B", b);
        map.put("C", c);
        map.put("D", d);
        return map;
    }
}