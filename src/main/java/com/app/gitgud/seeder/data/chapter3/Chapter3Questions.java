package com.app.gitgud.seeder.data.chapter3;

import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.QuestionDifficulty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The initial form of this file, more specifically the getQuestions() and sectionXX() parts, was generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter3Questions {

    public static Map<String, List<MultipleChoiceQuestion>> getQuestions() {
        Map<String, List<MultipleChoiceQuestion>> map = new HashMap<>();
        map.put("section-3-1", section31());
        map.put("section-3-2", section32());
        map.put("section-3-3", section33());
        map.put("section-3-4", section34());
        map.put("section-3-5", section35());
        return map;
    }

    // -------------------------------------------------------------------------
    // Section 3.1 — Remote Repositories
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section31() {
        return Arrays.asList(

                build("q1",
                        "What is a remote repository?",
                        opts("A copy of the entire project on a different computer",
                                "A branch that exists locally only",
                                "A version of your project hosted on the internet",
                                "A connection to a GitHub repository"),
                        "C", "beginner",
                        "Think about what 'remote' means.",
                        "It is hosted somewhere other than your local computer and allows you to share and collaborate."
                ),

                build("q2",
                        "What does `git clone` do?",
                        opts("Downloads a remote repository to your computer",
                                "Creates a new empty repository",
                                "Uploads your local repository to GitHub",
                                "Creates a copy of a local repository"),
                        "A", "intermediate",
                        "Think about what a clone is, it is an exact copy.",
                        "After cloning, you have a full local copy of the repository, including its history."
                ),

                build("q3",
                        "What is origin?",
                        opts("The default name of the remote connection",
                                "The first commit in a repository",
                                "The name of the default Git configuration file",
                                "The main branch of a repository"),
                        "A", "intermediate",
                        "When you clone a repository, Git automatically creates this connection.",
                        "It is a convention, not a rule. You can rename the remote to something else."
                ),

                build("q4",
                        "Why would you add a second remote called upstream?",
                        opts("To connect to the original repository when working on a fork",
                                "To create a backup of your remote",
                                "To speed up push and pull operations",
                                "To push to two servers at the same time"),
                        "A", "advanced",
                        "A fork is your own copy of someone else's repository.",
                        "You need a way to pull updates from the original project into your fork."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.2 — Push and Pull
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section32() {
        return Arrays.asList(

                build("q1",
                        "What does `git push origin main` do?",
                        opts("Downloads the latest changes from the remote",
                                "Creates a new branch on the remote called main",
                                "Uploads your local commits to the remote repository",
                                "Merges the remote main into your local main"),
                        "C", "beginner",
                        "Think about the direction. Push means you are sending something.",
                        "Your local commits become available to others after pushing."
                ),

                build("q2",
                        "What does `git pull` do?",
                        opts("Downloads changes and merges them into your local branch",
                                "Creates a new empty repository",
                                "Creates a copy of the remote branch locally",
                                "Downloads changes without merging them"),
                        "A", "intermediate",
                        "Think about the direction. Pull means you are receiving something.",
                        "After pulling, your local branch incorporates the latest changes from the remote."
                ),

                build("q3",
                        "What is the difference between `git push` and `git pull`?",
                        opts("Push is for branches, pull is for files",
                                "Push works offline, pull requires internet connectivity",
                                "There is no difference, they are aliases",
                                "Push uploads changes to remote, pull downloads from remote"),
                        "D", "intermediate",
                        "Think about the direction of data flow in each command.",
                        "One sends your commits to the remote, while the other retrieves changes from the remote."
                ),

                build("q4",
                        "What does `git push --force` do and why is it dangerous?",
                        opts("It overwrites the remote history, and teammates can lose their work",
                                "It bypasses authentication, exposing credentials",
                                "It deletes the remote branch and cannot be undone",
                                "It pushes all branches at once and is dangerous because it is slow"),
                        "A", "advanced",
                        "Force push replaces the remote with your local version.",
                        "It can overwrite commits that other developers have already pushed, potentially causing them to lose work."
                ),

                build("q5",
                        "`git pull` is effectively two commands in one. After `git fetch origin`, what command merges the changes?",
                        opts("git merge origin/main",
                                "git merge origin main",
                                "git push origin main",
                                "git commit"),
                        "A", "advanced",
                        "Git pull uses fetch and merge.",
                        "The fetched remote branch is merged into your current branch with `git merge origin/main`."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.3 — Git Fetch
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section33() {
        return Arrays.asList(

                build("q1",
                        "What does `git fetch` do differently from `git pull`?",
                        opts("Fetch is faster than pull, but they do the exact same thing",
                                "Fetch downloads changes but does not merge them",
                                "Fetch only works with branches, pull works with files",
                                "Fetch deletes remote branches, pull creates them"),
                        "B", "beginner",
                        "Think about the word 'fetch', you retrieve something but do not use it yet.",
                        "After fetching, your local branch remains unchanged until you merge the changes manually."
                ),

                build("q2",
                        "Why is `git fetch` considered safer than `git pull`?",
                        opts("Because it never modifies your local branch",
                                "Because it requires authentication",
                                "Because it creates a backup before downloading",
                                "Because it checks for conflicts before downloading"),
                        "A", "intermediate",
                        "Think about what pull does automatically that fetch does not.",
                        "Fetch lets you review the downloaded changes before integrating them into your local branch."
                ),

                build("q3",
                        "After running `git fetch origin`, how do you see the downloaded commits?",
                        opts("`git log origin/main`",
                                "`git show fetch`",
                                "`git diff --fetched`",
                                "`git show origin`"),
                        "A", "advanced",
                        "The downloaded changes are stored in remote-tracking branches.",
                        "The remote-tracking branch is named after the remote and the branch, such as `origin/main`."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.4 — Git Revert
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section34() {
        return Arrays.asList(

                build("q1",
                        "What does `git revert HEAD` do?",
                        opts("Creates a new commit that undoes the most recent commit",
                                "Deletes the most recent commit permanently",
                                "Moves HEAD back to the previous commit",
                                "Restores all files to their original state"),
                        "A", "beginner",
                        "Think about what 'revert' means.",
                        "It creates a new commit whose changes undo the changes introduced by the most recent commit."
                ),

                build("q2",
                        "What does `git revert` produce?",
                        opts("A deleted commit",
                                "A modified version of the previous commit",
                                "A new commit that reverses the changes of a previous one",
                                "A stashed version of the undone changes"),
                        "C", "intermediate",
                        "Git revert never removes commits.",
                        "The history shows both the original commit and the new revert commit."
                ),

                build("q3",
                        "What does `git revert HEAD~2..HEAD` do?",
                        opts("Reverts the last 2 commits",
                                "Reverts the last 3 commits",
                                "Reverts the last commit",
                                "Reverts the last 4 commits"),
                        "A", "advanced",
                        "This syntax reverts a range of commits.",
                        "The range contains two commits: `HEAD~1` and `HEAD`."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.5 — Git Reset
    // -------------------------------------------------------------------------

    private static List<MultipleChoiceQuestion> section35() {
        return Arrays.asList(

                build("q1",
                        "What does `git reset --hard HEAD~1` do?",
                        opts("Moves the branch back one commit and discards all changes permanently",
                                "Moves the branch back one commit but does not discard changes",
                                "Restores deleted files from the last commit",
                                "Moves the branch back two commits and keeps changes staged"),
                        "A", "beginner",
                        "`--hard` is destructive.",
                        "Your changes are discarded after running it."
                ),

                build("q2",
                        "What does `git reset --soft HEAD~1` do differently from `--hard`?",
                        opts("It keeps your changes staged instead of discarding them",
                                "It keeps your changes in the working directory but unstaged",
                                "It creates a backup before resetting",
                                "It only resets tracked files"),
                        "A", "intermediate",
                        "`--soft` is non-destructive.",
                        "Your changes are still there after running it, ready to be recommitted."
                ),

                build("q3",
                        "When should you avoid using `git reset` on a shared repository?",
                        opts("Never, it is always safe",
                                "Only when using `--hard`",
                                "Always, because it rewrites history",
                                "Only when there are less than 5 people working on the project"),
                        "C", "intermediate",
                        "It rewrites history.",
                        "You should generally use `git revert` instead when undoing changes that are already shared."
                ),

                build("q4",
                        "Why is `git revert` safer than `git reset`?",
                        opts("Because revert is faster",
                                "Because revert creates a backup",
                                "Because revert only affects local files",
                                "Because revert does not rewrite history"),
                        "D", "intermediate",
                        "Think about what your teammates have already pulled from the remote.",
                        "If you rewrite history that others already have, their copies no longer match yours."
                ),

                build("q5",
                        "When should you use `git revert` instead of `git reset`?",
                        opts("When you want to permanently delete a commit",
                                "When the commit has already been pushed to a shared repository",
                                "When you want to undo multiple commits at once",
                                "When you are working on a local branch only"),
                        "B", "advanced",
                        "Think about who else might have pulled the commit you want to undo.",
                        "If others have the commit, rewriting history can cause problems for their copies of the repository."
                ),

                build("q6",
                        "What does `git reset --mixed HEAD~1` do?",
                        opts("Discards all changes permanently",
                                "Undoes the commit and unstages changes but keeps them in the working directory",
                                "Moves all changes to the stash",
                                "Keeps changes staged and ready to commit"),
                        "B", "advanced",
                        "`--mixed` is between `--soft` and `--hard`.",
                        "Your files are still there, but you need to add them again before committing."
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
