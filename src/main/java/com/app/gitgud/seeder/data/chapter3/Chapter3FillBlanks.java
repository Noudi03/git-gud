package com.app.gitgud.seeder.data.chapter3;

import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.QuestionDifficulty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter3FillBlanks {

    public static Map<String, List<FillInTheBlankQuestion>> getQuestions() {
        Map<String, List<FillInTheBlankQuestion>> map = new HashMap<>();
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

    private static List<FillInTheBlankQuestion> section31() {
        return Arrays.asList(

                build("fb1",
                        "To download a remote repository to your computer, you run `git ___`.",
                        "clone", "easy",
                        "Think about what it means to make an exact copy.",
                        "This command creates a full local copy of the remote repository, including its history."
                ),

                build("fb2",
                        "The default name Git gives to the remote connection after cloning is ___.",
                        "origin", "medium",
                        "Think about the conventional name for the primary remote.",
                        "This is a convention, you can rename it, but origin is the standard."
                ),

                build("fb3",
                        "When working with a fork, you typically add a second remote called ___ that points to the original repository.",
                        "upstream", "hard",
                        "Think about the conventional name for the original project's remote.",
                        "This name indicates where the code originally came from."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.2 — Push and Pull
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section32() {
        return Arrays.asList(

                build("fb1",
                        "To upload your local commits on the main branch to the remote, you run `git push origin ___`.",
                        "main", "easy",
                        "Think about which branch you want to push.",
                        "You specify the branch name as the last argument."
                ),

                build("fb2",
                        "`git pull` is effectively `git fetch origin` followed by `git merge ___`.",
                        "origin/main", "medium",
                        "Think about the remote-tracking branch that fetch updates.",
                        "The fetched branch is referenced by the remote name followed by a slash and the branch name."
                ),

                build("fb3",
                        "The `-u` flag in `git push -u origin main` sets up ___ tracking between your local and remote branch.",
                        "upstream", "hard",
                        "Think about what -u enables for future pushes and pulls.",
                        "After setting this, Git knows which remote branch your local branch corresponds to."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.3 — Git Fetch
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section33() {
        return Arrays.asList(

                build("fb1",
                        "`git fetch` downloads changes but does not ___ them into your local branch.",
                        "merge", "easy",
                        "Think about what step fetch deliberately skips.",
                        "That step is left for you to do manually, which is why fetch is considered safer."
                ),

                build("fb2",
                        "After `git fetch origin`, the downloaded commits are available through remote-tracking branches such as ___.",
                        "origin/main", "medium",
                        "Think about how Git names the remote-tracking branch.",
                        "It combines the remote name and the branch name with a slash."
                ),

                build("fb3",
                        "To remove remote-tracking branches that no longer exist on the remote, you run `git fetch ___`.",
                        "--prune", "hard",
                        "Think about what pruning does in general.",
                        "This flag cleans up stale references left behind by deleted remote branches."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.4 — Git Revert
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section34() {
        return Arrays.asList(

                build("fb1",
                        "`git revert` creates a new ___ that undoes the changes of a previous commit.",
                        "commit", "easy",
                        "Think about what revert produces in the history.",
                        "The undo itself is recorded as a new entry in the history."
                ),

                build("fb2",
                        "To undo the most recent commit using revert, you run `git revert ___`.",
                        "HEAD", "medium",
                        "Think about how to reference the most recent commit.",
                        "HEAD always points to the latest commit on your current branch."
                ),

                build("fb3",
                        "The `--no-commit` flag in `git revert` applies the revert changes without immediately creating a ___.",
                        "commit", "hard",
                        "Think about what the flag delays.",
                        "It stages the revert changes so you can review or adjust them before committing."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.5 — Git Reset
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section35() {
        return Arrays.asList(

                build("fb1",
                        "`git reset --hard HEAD~1` moves the branch pointer back and ___ all changes permanently.",
                        "discards", "easy",
                        "Think about what --hard does to your working directory.",
                        "--hard is destructive, it throws away all changes without saving them."
                ),

                build("fb2",
                        "`git reset --soft HEAD~1` undoes the last commit but keeps your changes ___.",
                        "staged", "medium",
                        "Think about what --soft preserves.",
                        "--soft only moves the pointer. Changes remain in the staging area ready to recommit."
                ),

                build("fb3",
                        "`git reset ___` is the default mode: it undoes the commit, unstages changes, but keeps the files in the working directory.",
                        "--mixed", "hard",
                        "Think about the mode that sits between --soft and --hard.",
                        "This mode unstages the changes without deleting them from disk."
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