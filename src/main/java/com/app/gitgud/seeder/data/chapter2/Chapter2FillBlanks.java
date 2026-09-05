package com.app.gitgud.seeder.data.chapter2;

import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.QuestionDifficulty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter2FillBlanks {

    public static Map<String, List<FillInTheBlankQuestion>> getQuestions() {
        Map<String, List<FillInTheBlankQuestion>> map = new HashMap<>();
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

    private static List<FillInTheBlankQuestion> section21() {
        return Arrays.asList(

                build("fb1",
                        "A branch is a ___ to a commit, not a copy of your project files.",
                        "pointer", "easy",
                        "Think about what `Git` actually creates when you branch.",
                        "Creating a branch is fast because `Git` only creates this lightweight reference."
                ),

                build("fb2",
                        "To create a new branch named `feature`, you run `git branch ___`.",
                        "feature", "easy",
                        "Think about what argument the `branch` command takes.",
                        "You simply pass the name of the branch you want to create."
                ),

                build("fb3",
                        "When you commit on a feature branch, only the ___ branch pointer moves forward.",
                        "feature", "medium",
                        "Think about which pointer tracks commits on a given branch.",
                        "Each branch pointer only advances when you commit on that specific branch."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.2 — Working on different branches (Git switch)
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section22() {
        return Arrays.asList(

                build("fb1",
                        "To switch to an existing branch called `main`, you run `git switch ___`.",
                        "main", "easy",
                        "Think about what argument `switch` takes.",
                        "You pass the name of the branch you want to move to."
                ),

                build("fb2",
                        "The flag used with `git switch` to create and switch to a new branch in one step is ___.",
                        "-c", "medium",
                        "Think about what the flag stands for.",
                        "The flag stands for 'create'."
                ),

                build("fb3",
                        "`git switch` was introduced as a clearer alternative to `git ___`, which performed too many unrelated actions.",
                        "checkout", "hard",
                        "Think about the older command that handled branching, file restoring, and detaching `HEAD`.",
                        "This command still works but is considered confusing due to its multiple responsibilities."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.3 — Git Merge
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section23() {
        return Arrays.asList(

                build("fb1",
                        "To merge a branch named `feature` into your current branch, you run `git merge ___`.",
                        "feature", "easy",
                        "Think about what argument `merge` takes.",
                        "You pass the name of the branch whose changes you want to bring in."
                ),

                build("fb2",
                        "To delete a branch after merging, you run `git branch ___ feature`.",
                        "-d", "medium",
                        "Think about the flag that stands for delete.",
                        "It is a single-letter flag."
                ),

                build("fb3",
                        "A ___ merge happens when the target branch has not moved since the feature branch was created, allowing `Git` to simply move the pointer forward.",
                        "fast-forward", "hard",
                        "Think about the merge strategy that produces no merge commit.",
                        "The name describes how the pointer jumps ahead without needing a new commit."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.4 — Merge Conflicts
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section24() {
        return Arrays.asList(

                build("fb1",
                        "In conflict markers, the `=======` line separates your branch's version from the ___ branch's version.",
                        "incoming", "easy",
                        "Think about what is above and below the divider.",
                        "Above is your version (`HEAD`), below is the version coming in from the other branch."
                ),

                build("fb2",
                        "The correct order to resolve a merge conflict is: edit the file, then `git ___`, then `git commit`.",
                        "add", "medium",
                        "Think about the standard `Git` workflow after editing a file.",
                        "You must stage the resolved file before `Git` knows the conflict is fixed."
                ),

                build("fb3",
                        "To cancel a merge in progress and return to the previous state, you run `git merge ___`.",
                        "--abort", "hard",
                        "Think about the flag that stops any ongoing `Git` operation.",
                        "This flag tells `Git` to give up on the merge entirely."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.5 — Git Rebase
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section25() {
        return Arrays.asList(

                build("fb1",
                        "To rebase your feature branch on top of main, you switch to feature and run `git rebase ___`.",
                        "main", "easy",
                        "Think about what branch you want to use as the new base.",
                        "You pass the name of the branch you want to rebase onto."
                ),

                build("fb2",
                        "After a rebase, the original commits are recreated with new hashes because they have different ___.",
                        "parents", "medium",
                        "Think about what changes when a commit is placed on top of a different commit.",
                        "Each commit stores a reference to its parent. A new parent means a new hash."
                ),

                build("fb3",
                        "To interactively edit the last 3 commits, you run `git rebase -i ___`.",
                        "HEAD~3", "hard",
                        "Think about how to reference the last 3 commits relative to the current position.",
                        "The `~` symbol followed by a number steps back that many commits from `HEAD`."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.6 — Git Stash
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section26() {
        return Arrays.asList(

                build("fb1",
                        "`git stash` temporarily saves your uncommitted changes and ___ the working directory.",
                        "cleans", "easy",
                        "Think about the state of your working directory after stashing.",
                        "After stashing, the directory looks as if you hadn't made any changes."
                ),

                build("fb2",
                        "To restore the most recently stashed changes and remove them from the stash list, you run `git stash ___`.",
                        "pop", "medium",
                        "Think about the stack operation that retrieves and removes the top item.",
                        "It is a common term from stack data structures."
                ),

                build("fb3",
                        "To include untracked files in a stash, you use the ___ flag.",
                        "-u", "hard",
                        "Think about the first letter of 'untracked'.",
                        "It is a single-letter flag."
                )
        );
    }

    // -------------------------------------------------------------------------
    // Section 2.7 — Git Tags
    // -------------------------------------------------------------------------

    private static List<FillInTheBlankQuestion> section27() {
        return Arrays.asList(

                build("fb1",
                        "Tags are commonly used to mark ___ versions of a project, such as `v1.0`.",
                        "release", "easy",
                        "Think about when developers create tags in a project's lifecycle.",
                        "A tag typically marks a stable, published version of the software."
                ),

                build("fb2",
                        "To create an annotated tag with a message, you use `git tag ___ v2.0 -m \"Release version 2.0\"`.",
                        "-a", "medium",
                        "Think about the flag that stands for 'annotated'.",
                        "It is a single-letter flag."
                ),

                build("fb3",
                        "To push all local tags to a remote, you run `git push origin ___`.",
                        "--tags", "hard",
                        "Think about the flag that tells `Git` to include tags in the push.",
                        "Without this flag, `git push` ignores tags entirely."
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
