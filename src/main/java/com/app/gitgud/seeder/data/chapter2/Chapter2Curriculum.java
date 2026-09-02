package com.app.gitgud.seeder.data.chapter2;

import com.app.gitgud.model.Section;

import java.util.Arrays;
import java.util.List;

// The initial form of this file, more specifically the getSections() part, was generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter2Curriculum {

    public static List<Section> getSections() {
        return Arrays.asList(

                buildSection(
                        "section-2-1", "What is a branch?", 1, null,
                        """
                                A **branch** refers to an independent line of development. It is a version of your project \
                                in which you can make changes without affecting the main code. By default, Git creates \
                                a branch called `master` or `main`. This is where your stable, working code usually exists.

                                ### Basic Branch Commands

                                ```bash
                                git branch           # list all branches
                                git branch feature   # create a new branch named "feature"
                                ```

                                When you create a branch, you get an exact copy of the current state of your project. \
                                The changes you make on the new branch do not affect the state of other branches.
                                """,
                        """
                                A branch is a **pointer to a commit**. When creating a branch, no files are copied. \
                                Instead, Git creates a new pointer that references the same commit.

                                ```
                                main -> commit A
                                feature -> commit A  # same commit, different pointer
                                ```

                                When you make a new commit on the `feature` branch, only that pointer moves forward:

                                ```
                                main -> commit A
                                feature -> commit B
                                ```

                                This is why creating a branch is fast and does not duplicate your project files.
                                """
                ),

                buildSection(
                        "section-2-2", "Working on different branches (Git switch)", 2, "section-2-1",
                        """
                                With `git switch`, you can move between branches. When you switch to a branch, \
                                your files are updated to reflect the state of that branch.

                                ### Switching Branches

                                ```bash
                                git switch main          # switch to the main branch
                                git switch feature       # switch to the feature branch
                                git switch -c feature2   # create and switch to a new branch
                                ```
                                """,
                        """
                                The `git switch` command was introduced as an alternative to `git checkout`. \
                                The old command performed several different actions, which could make it confusing \
                                for developers.

                                `git checkout` can switch branches, restore files, and detach `HEAD`.

                                ### Using git checkout

                                ```bash
                                git checkout feature       # switch to a branch
                                git checkout -b newbranch  # create a branch and switch to it
                                git checkout -- file.txt   # restore a file
                                ```

                                ### Using git switch and git restore

                                ```bash
                                git switch feature         # switch to a branch
                                git switch -c newbranch    # create and switch to a branch
                                git restore file.txt       # restore a file
                                ```

                                Git separates these operations into different commands, making their purpose clearer.
                                """
                ),

                buildSection(
                        "section-2-3", "Git Merge", 3, "section-2-2",
                        """
                                `git merge` combines the changes from one branch into another. \
                                This is how you bring your work back into a branch after finishing a feature.

                                ### Merging a Branch

                                ```bash
                                git switch main      # switch to the branch you want to merge into
                                git merge feature    # merge the feature branch into main
                                ```

                                After merging, the `main` branch contains all the changes from `feature`. \
                                The `feature` branch still exists, and you can delete it if you no longer need it.

                                ```bash
                                git branch -d feature
                                ```
                                """,
                        """
                                Git has two main merge strategies: **fast-forward merge** and **3-way merge**.

                                A **fast-forward merge** happens when the target branch has not moved since \
                                the feature branch was created. Git can simply move the target branch pointer forward.

                                A **3-way merge** happens when both branches have new commits. Git creates a new \
                                merge commit that has both branch tips as its parents.

                                ### Merge Options

                                ```bash
                                git merge --no-ff feature     # force a merge commit
                                git merge --squash feature    # combine feature changes without creating a merge commit
                                ```

                                The `--no-ff` option forces Git to create a merge commit even when a fast-forward \
                                merge would be possible.
                                """
                ),

                buildSection(
                        "section-2-4", "Merge Conflicts", 4, "section-2-3",
                        """
                                A **merge conflict** happens when two branches have changed the same part of the \
                                same file, and Git cannot determine which version should be kept. \
                                You must resolve the conflict manually.

                                ### Creating a Conflict

                                ```bash
                                git switch main
                                git merge feature
                                ```

                                Git may report something similar to:

                                ```text
                                CONFLICT (content): Merge conflict in main.py
                                Automatic merge failed; fix conflicts and then commit the result.
                                ```

                                Git marks the conflicting section inside the file:

                                ```text
                                <<<<<<< HEAD
                                your version of the code
                                =======
                                the feature branch version
                                >>>>>>> feature
                                ```

                                To resolve the conflict, choose which version to keep and remove the conflict \
                                markers. Then stage the file and commit the result.

                                ```bash
                                git add main.py
                                git commit -m "Resolve merge conflict"
                                ```
                                """,
                        """
                                To prevent complex conflicts, keep your branches **short-lived** and merge often. \
                                For example, merge a feature branch when you finish developing the feature.

                                Long-running branches that diverge significantly from the main branch can make \
                                conflicts much harder to resolve. Poor communication between teammates can also \
                                contribute to complex conflicts.

                                ### Useful Conflict Commands

                                ```bash
                                git merge --abort   # cancel the merge and return to the previous state
                                git diff --merge    # inspect merge conflicts
                                git mergetool       # open a visual merge tool
                                ```

                                When possible, resolve conflicts early rather than allowing branches to diverge \
                                for a long time.
                                """
                ),

                buildSection(
                        "section-2-5", "Git Rebase", 5, "section-2-4",
                        """
                                `git rebase` moves your branch so that it starts from the tip of another branch. \
                                This can result in a cleaner and more linear history without merge commits.

                                ### Rebasing a Branch

                                ```bash
                                git switch feature
                                git rebase main   # reapply feature commits on top of main
                                ```

                                Before the rebase:

                                ```
                                main -> A -> B -> C
                                feature -> A -> D -> E
                                ```

                                After the rebase:

                                ```
                                main -> A -> B -> C
                                feature -> A -> B -> C -> D' -> E'
                                ```

                                The commits `D` and `E` are recreated as `D'` and `E'` because they now have \
                                different parents.

                                **Important:** Never rebase commits that have already been pushed to a shared repository. \
                                Rebasing rewrites history and can cause problems for your teammates.
                                """,
                        """
                                **Interactive rebase** lets you edit, squash, reorder, or drop commits before \
                                integrating your work.

                                ### Interactive Rebase

                                ```bash
                                git rebase -i HEAD~3   # interactively edit the last 3 commits
                                ```

                                In the interactive rebase editor, you can use:

                                1. **pick**: keep the commit as is
                                2. **squash**: combine the commit with the previous commit
                                3. **reword**: change the commit message
                                4. **drop**: remove the commit entirely

                                ### Handling Rebase Conflicts

                                ```bash
                                git rebase --abort      # cancel the rebase
                                git rebase --continue   # continue after resolving a conflict
                                ```

                                Interactive rebase is especially useful for cleaning up your local commit history \
                                before sharing your work.
                                """
                ),

                buildSection(
                        "section-2-6", "Git Stash", 6, "section-2-5",
                        """
                                `git stash` temporarily saves your uncommitted changes so you can switch branches \
                                or work on something else without committing unfinished work.

                                You can restore a stash with `git stash pop` or delete it with `git stash drop`.

                                ### Basic Stash Commands

                                ```bash
                                git stash       # save current changes and clean the working directory
                                git stash pop   # restore the most recently stashed changes
                                git stash list  # list all stashed changes
                                ```

                                Stashing is useful when you need to temporarily put your current work aside.
                                """,
                        """
                                You can add descriptions to stashes, create multiple stashes, restore specific stashes, \
                                and even create a new branch from a stash.

                                ### Advanced Stash Commands

                                ```bash
                                git stash push -m "WIP on landing page"  # stash with a description
                                git stash pop stash@{2}                  # restore a specific stash
                                git stash drop stash@{0}                 # delete a specific stash
                                git stash clear                          # delete all stashes
                                git stash branch feature                 # create a branch from a stash
                                git stash -u                             # include untracked files in the stash
                                ```

                                The `-u` option includes untracked files in the stash, which are normally left out.
                                """
                ),

                buildSection(
                        "section-2-7", "Git Tags", 7, "section-2-6",
                        """
                                In Git, a **tag** marks a specific commit as important. \
                                Tags are commonly used to mark **release versions** of your project.

                                ### Basic Tag Commands

                                ```bash
                                git tag                       # list all tags
                                git tag v1.0                  # create a tag on the current commit
                                git tag v1.0 a2c5d1c          # create a tag on a specific commit
                                ```

                                Unlike branches, tags do not move when you create new commits. \
                                Once a tag is created, it continues to point to the same commit.
                                """,
                        """
                                Git has two main types of tags: **lightweight** and **annotated** tags.

                                A **lightweight tag** is simply a pointer to a commit, while an **annotated tag** \
                                is a Git object that contains additional information such as a message, author, \
                                and date. Annotated tags are generally recommended for releases.

                                ### Lightweight and Annotated Tags

                                ```bash
                                git tag v1.0                               # lightweight tag
                                git tag -a v2.0 -m "Release version 2.0"  # annotated tag
                                git show v2.0                              # view tag details
                                ```

                                ### Pushing Tags

                                Tags are **not pushed to remote repositories by default**.

                                ```bash
                                git push origin v2.0       # push a specific tag
                                git push origin --tags     # push all tags
                                ```
                                """
                )
        );
    }

    private static Section buildSection(String id, String title, int order, String prerequisiteId,
                                        String content, String advancedContent) {
        Section s = new Section();
        s.setId(id);
        s.setTitle(title);
        s.setOrder(order);
        s.setPrerequisiteId(prerequisiteId);
        s.setLocked(prerequisiteId != null);
        s.setContent(content);
        s.setAdvancedContent(advancedContent);
        return s;
    }
}
