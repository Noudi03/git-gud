package com.app.gitgud.seeder.data.chapter3;

import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.TrueFalseQuestion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter3TrueFalse {

    public static Map<String, List<TrueFalseQuestion>> getQuestions() {
        Map<String, List<TrueFalseQuestion>> map = new HashMap<>();
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

    private static List<TrueFalseQuestion> section31() {
        return Arrays.asList(

                build("tf1",
                        "When you clone a repository, Git automatically creates a remote connection called `origin`.",
                        true, "easy",
                        "Think about what Git sets up for you automatically after cloning.",
                        "origin is the default name Git assigns to the remote you cloned from."
                ),

                build("tf2",
                        "You can only have one remote connection per Git repository.",
                        false, "medium",
                        "Think about the workflow when contributing to an open-source fork.",
                        "You can have multiple remotes, for example origin for your fork and upstream for the original project."
                ),

                build("tf3",
                        "Remote names like `origin` and `upstream` are enforced by Git and cannot be changed.",
                        false, "hard",
                        "Think about whether Git requires specific remote names.",
                        "These are conventions only. You can rename or remove remotes freely with `git remote rename` and `git remote remove`."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.2 — Push and Pull
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section32() {
        return Arrays.asList(

                build("tf1",
                        "`git push` uploads your local commits to the remote repository.",
                        true, "easy",
                        "Think about the direction of data flow with push.",
                        "Push sends your local work outward to the remote."
                ),

                build("tf2",
                        "`git pull` downloads changes from the remote but does not merge them into your local branch.",
                        false, "medium",
                        "Think about what pull does automatically compared to fetch.",
                        "git pull is fetch + merge. It downloads and merges in one step."
                ),

                build("tf3",
                        "`git push --force` is always safe to use on shared branches.",
                        false, "hard",
                        "Think about what force push does to the remote history.",
                        "Force push overwrites the remote history. Teammates who already pulled those commits can lose work."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.3 — Git Fetch
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section33() {
        return Arrays.asList(

                build("tf1",
                        "`git fetch` downloads changes from the remote but leaves your local branch unchanged.",
                        true, "easy",
                        "Think about what fetch does compared to pull.",
                        "Fetch only retrieves, it never modifies your current branch automatically."
                ),

                build("tf2",
                        "After running `git fetch origin`, you cannot inspect the downloaded commits before merging.",
                        false, "medium",
                        "Think about the reason fetch is considered safer than pull.",
                        "Fetched commits are stored in remote-tracking branches like origin/main, which you can inspect freely."
                ),

                build("tf3",
                        "`git fetch --prune` removes remote-tracking branches that no longer exist on the remote.",
                        true, "hard",
                        "Think about what prune means.",
                        "Pruning cleans up stale references to deleted remote branches."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.4 — Git Revert
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section34() {
        return Arrays.asList(

                build("tf1",
                        "`git revert HEAD` deletes the most recent commit from the history.",
                        false, "easy",
                        "Think about what revert produces.",
                        "git revert creates a new commit that undoes the changes, it never removes existing commits."
                ),

                build("tf2",
                        "`git revert` is safe to use on shared repositories because it does not rewrite history.",
                        true, "medium",
                        "Think about why revert is preferred over reset for shared branches.",
                        "Revert adds a new commit on top of the existing history, so teammates are not affected."
                ),

                build("tf3",
                        "`git revert --no-commit` applies the revert changes without immediately creating a commit.",
                        true, "hard",
                        "Think about what --no-commit gives you the opportunity to do.",
                        "It lets you review or combine the reverted changes before committing the result."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 3.5 — Git Reset
    // -------------------------------------------------------------------------

    private static List<TrueFalseQuestion> section35() {
        return Arrays.asList(

                build("tf1",
                        "`git reset --hard HEAD~1` discards all changes permanently and cannot be undone easily.",
                        true, "easy",
                        "Think about what the --hard flag does to your working directory.",
                        "--hard is destructive: it moves the pointer back and throws away all changes."
                ),

                build("tf2",
                        "`git reset --soft HEAD~1` keeps your changes staged and ready to recommit.",
                        true, "medium",
                        "Think about what --soft preserves after undoing the commit.",
                        "--soft only moves the branch pointer. Your staged changes stay exactly as they were."
                ),

                build("tf3",
                        "`git reset` is preferred over `git revert` when undoing commits that have already been pushed to a shared repository.",
                        false, "hard",
                        "Think about what reset does to the history that teammates already have.",
                        "Reset rewrites history. For shared branches, git revert is the safe choice because it preserves history."
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