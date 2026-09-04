package com.app.gitgud.seeder.data.chapter2;

import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.QuestionDifficulty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The initial form of this file, more specifically the getQuestions() and sectionXX() parts, was generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter2MultipleChoice {

    public static Map<String, List<MultipleChoiceQuestion>> getQuestions() {
        Map<String, List<MultipleChoiceQuestion>> map = new HashMap<>();
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

    private static List<MultipleChoiceQuestion> section21() {
        return Arrays.asList(

                build("mc1",
                        "What is a branch?",
                        opts("A copy of the entire project on a different computer",
                                "An independent line of development",
                                "A saved version of a file",
                                "A connection to a GitHub repository"),
                        "B", "beginner",
                        "Think about what parallel means when developing software with others.",
                        "A branch lets you work on a new feature without touching the main code."
                ),

                build("mc2",
                        "What is the default branch git creates called?",
                        opts("master or main",
                                "root",
                                "dev",
                                "feature"),
                        "A", "intermediate",
                        "What is the main stable version of a project usually called?",
                        "Git gives 2 possible names for that branch."
                ),

                build("mc3",
                        "In technical terms, what is a branch in git?",
                        opts("A full copy of the repository",
                                "A pointer to a commit",
                                "A snapshot of all files",
                                "A different repository with all changes up to that point"),
                        "B", "advanced",
                        "Think about why creating a branch is very fast.",
                        "No files are copied when you create a branch."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.2 — Working on different branches (Git switch)
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section22() {
        return Arrays.asList(

                build("mc1",
                        "What does `git switch main` do?",
                        opts("Moves you to the main branch",
                                "Creates a branch named main",
                                "Merges your current branch into main",
                                "Renames the current branch to main"),
                        "A", "beginner",
                        "What does switch refer to in everyday language?",
                        "Your files update to reflect the state of the branch you switch to."
                ),

                build("mc2",
                        "Which command creates a new branch and switches to it?",
                        opts("git branch -c feature",
                                "git switch --branch feature",
                                "git switch --new feature",
                                "git switch -c feature"),
                        "D", "intermediate",
                        "You need a flag. What does the flag -c stand for?",
                        "The flag stands for create."
                ),

                build("mc3",
                        "What happens to your files when you switch branches?",
                        opts("They are deleted and redownloaded",
                                "Nothing changes until you commit a change",
                                "They are temporarily saved in the stash",
                                "They update to reflect the state of the new branch"),
                        "D", "intermediate",
                        "Think about what the purpose of switching branches is.",
                        "Each branch holds its own version of the project."
                ),

                build("mc4",
                        "Why was `git switch` introduced as a replacement for `git checkout`?",
                        opts("Because git checkout was too slow",
                                "Because git checkout required an internet connection",
                                "Because git checkout was taking up too many resources",
                                "Because git checkout was doing too many unrelated things"),
                        "D", "advanced",
                        "Git checkout can switch branches, restore files and detach HEAD.",
                        "Having one command do many unrelated things is considered bad design."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.3 — Git Merge
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section23() {
        return Arrays.asList(

                build("mc1",
                        "What does `git merge feature` do when run from main?",
                        opts("Deletes the feature branch",
                                "Brings the changes from feature to main",
                                "Brings the changes from main to feature",
                                "Moves all commits from main to feature"),
                        "B", "beginner",
                        "Think about the direction: you are on main and merging feature into it.",
                        "The branch you run it from is the one that receives the changes."
                ),

                build("mc2",
                        "Which command deletes a branch after merging?",
                        opts("git branch --remove feature",
                                "git delete feature",
                                "git branch -d feature",
                                "git merge --delete feature"),
                        "C", "intermediate",
                        "The command starts with `git branch`.",
                        "The flag stands for delete."
                ),

                build("mc3",
                        "After merging a feature branch into main, what happens to the feature branch?",
                        opts("It is automatically deleted",
                                "It still exists until you manually delete it",
                                "It is renamed to merged",
                                "It is moved inside main"),
                        "B", "intermediate",
                        "`git merge` function is to combine changes.",
                        "You need a separate command to delete a branch."
                ),

                build("mc4",
                        "What is a fast-forward merge?",
                        opts("A merge that skips conflict resolution automatically",
                                "A merge where git moves the pointer forward without creating a merge commit",
                                "A merge that combines all commits into one",
                                "A merge that happens when both branches have commits"),
                        "B", "advanced",
                        "What happens when the main branch has not changed since you created your feature branch?",
                        "If the history is linear, git doesn't need to create a new commit, it can just move the pointer."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.4 — Merge Conflicts
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section24() {
        return Arrays.asList(

                build("mc1",
                        "When does a merge conflict happen?",
                        opts("When trying to merge a branch that doesn't exist",
                                "When two branches have a change at the same part of a file",
                                "When the feature branch has no new commits",
                                "When you forget to stage your files before merging"),
                        "B", "beginner",
                        "Think about what git needs to decide when merging two branches.",
                        "If both branches changed different files, git can merge them automatically."
                ),

                build("mc2",
                        "In a conflict marker, what does the `=======` line separate?",
                        opts("The old and new version",
                                "The file name from its content",
                                "Your branch's version from the incoming branch's version",
                                "The staged changes from the unstaged changes"),
                        "C", "intermediate",
                        "Look at the conflict markers: <<<<<<< is your version, >>>>>>> is theirs.",
                        "The ======= is the dividing line between the two versions."
                ),

                build("mc3",
                        "What is the correct order for resolving a merge conflict?",
                        opts("commit -> add -> edit the file",
                                "edit the file -> commit -> add",
                                "edit the file -> add -> commit",
                                "add -> edit the file -> commit"),
                        "C", "intermediate",
                        "What comes before a commit in the standard git workflow?",
                        "You need to stage the resolved file before git knows the conflict is fixed."
                ),

                build("mc4",
                        "Which command cancels a merge and returns to the state before it started?",
                        opts("git merge --abort",
                                "git merge --cancel",
                                "git reset merge",
                                "git merge --revert"),
                        "A", "advanced",
                        "What happens when something goes wrong and you want to undo the merge attempt?",
                        "The flag means you stop any operation that goes wrong."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.5 — Git Rebase
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section25() {
        return Arrays.asList(

                build("mc1",
                        "What does `git rebase main` do when run from a feature branch?",
                        opts("Merges main into feature creating a merge commit",
                                "Moves the feature branch commits on top of the latest main commit",
                                "Deletes the feature branch and replaces it with main",
                                "Creates a copy of main inside feature"),
                        "B", "beginner",
                        "Think about the word 'rebase'. It changes the base of your branch.",
                        "The result is a linear history."
                ),

                build("mc2",
                        "Why should you never rebase commits that have been pushed to a shared repository?",
                        opts("Because rebase only works on local branches",
                                "Because rebase deletes the original commits permanently",
                                "Because rebase rewrites history and will cause conflicts for teammates",
                                "Because rebasing deletes the original branch"),
                        "C", "intermediate",
                        "Think about what your teammates have on their machines.",
                        "If you rewrite history that others have already pulled, their history no longer matches yours."
                ),

                build("mc3",
                        "What does `git rebase -i HEAD~2` do?",
                        opts("Interactively edit, squash or drop the last 2 commits",
                                "Undo the last 2 commits permanently",
                                "Compare the last 2 commits with the main branch",
                                "Rebase the last 2 branches"),
                        "A", "advanced",
                        "The `-i` flag stands for interactive.",
                        "You get an editor that lets you choose what to do with each commit."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.6 — Git Stash
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section26() {
        return Arrays.asList(

                build("mc1",
                        "What does `git stash` do?",
                        opts("Permanently deletes your uncommitted changes",
                                "Commits your changes with an automatic message",
                                "Temporarily saves your uncommitted changes and cleans the working directory",
                                "Moves your changes to a new branch"),
                        "C", "beginner",
                        "Think about the word 'stash'. Where do you put things you want to keep but not use right away?",
                        "Your working directory becomes clean after stashing, as if you hadn't made any changes."
                ),

                build("mc2",
                        "Which command restores the most recently stashed changes?",
                        opts("git stash restore",
                                "git stash apply",
                                "git stash pop",
                                "git stash get"),
                        "C", "intermediate",
                        "Think about what happens to a stack.",
                        "Pop removes the stash from the list after restoring it."
                ),

                build("mc3",
                        "When would you use `git stash`?",
                        opts("When you want to permanently delete work in progress",
                                "When you want to commit without writing a message",
                                "When you want to undo the last commit",
                                "When you need to switch branches but have uncommitted changes"),
                        "D", "intermediate",
                        "What if you wanted to put your changes aside for a bit?",
                        "What if you wanted to check out someone else's work on the main branch?"
                ),

                build("mc4",
                        "Which command stashes changes including untracked files?",
                        opts("git stash -u",
                                "git stash --all",
                                "git untracked --stash",
                                "git stash -f"),
                        "A", "advanced",
                        "Untracked files are not included by default.",
                        "The flag is a single letter."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.7 — Git Tags
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section27() {
        return Arrays.asList(

                build("mc1",
                        "What is a git tag used for?",
                        opts("To mark a specific commit as important, usually a release version",
                                "To add a comment to a file",
                                "To create a new branch at a specific commit",
                                "To rename a branch"),
                        "A", "beginner",
                        "Think about how software versions are tracked (v1.0, v2.0 etc).",
                        "Tags are permanent markers on a specific point in history."
                ),

                build("mc2",
                        "What is the key difference between a tag and a branch?",
                        opts("Tags can only be created on the main branch",
                                "Tags are deleted after merging",
                                "Tags do not move when new commits are made",
                                "Tags only exist locally and cannot be pushed"),
                        "C", "intermediate",
                        "Think about what a branch does when you make a new commit.",
                        "A tag always points to the same commit, forever."
                ),

                build("mc3",
                        "What is the difference between a lightweight and an annotated tag?",
                        opts("Lightweight tags can be pushed, annotated tags cannot",
                                "Lightweight tags move with new commits, annotated tags do not",
                                "Annotated tags include a message, author and date, lightweight tags are just pointers",
                                "Annotated tags are automatically created on every commit"),
                        "C", "advanced",
                        "Think about what 'annotated' means, it has extra information attached.",
                        "For official releases, annotated tags are recommended because they carry metadata."
                ),

                build("mc4",
                        "Why do you need a special command to push tags to a remote?",
                        opts("Because tags require approval from the repository owner",
                                "Because tags are not included in a regular git push",
                                "Because tags can only be pushed by the original author",
                                "Because tags are stored in a different server"),
                        "B", "advanced",
                        "Think about what git push does by default.",
                        "Git push only pushes commits."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Builders
    // -------------------------------------------------------------------------

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