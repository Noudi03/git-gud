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
                                A branch refers to an independent line of development. It is a version of your project \
                                in which you can make changes, without affecting the main code. By default, git creates \
                                a branch called master or main. This is where your stable, working code exists.
                                
                                git branch           # list all branches
                                git branch feature   # create a new branch named "feature"
                                
                                When you create a branch, you get an exact copy of the current state of your project. \
                                The changes you make on the new branch do not affect the state of other branches.""",
                        """
                                A branch is a pointer to a commit. When creating a branch, no files are copied, \
                                and a new pointer is created.
                                
                                main -> commit A
                                feature -> commit A  # (same commit, different pointer)
                                
                                When you make a new commit on the feature branch, only that pointer moves forward:
                                
                                main -> commit A
                                feature -> commit B"""
                ),

                buildSection(
                        "section-2-2", "Working on different branches (Git switch)", 2, "section-2-1",
                        """
                                With `git switch` you can move between branches. When you switch to a branch, \
                                your files update to reflect the state of that other branch.
                                
                                git switch main       # switch to the main branch
                                git switch feature    # switch to the feature branch
                                git switch -c feature2  # create and switch to a new branch named feature2""",
                        """
                                The command `git switch` was introduced as an alternative to `git checkout`. \
                                The old command performed too many actions at once, making things confusing to developers. \
                                `git checkout` can switch branches, restore files and detach HEAD.
                                
                                # with git checkout
                                git checkout feature      # switch to branch
                                git checkout -b newbranch # create branch and switch
                                git checkout -- file.txt  # restore a file
                                
                                # with git switch
                                git switch feature        # switch to branch
                                git switch -c newbranch   # create and switch
                                git restore file.txt      # restore a file (using a separate command)"""
                ),

                buildSection(
                        "section-2-3", "Git Merge", 3, "section-2-2",
                        """
                                `git merge` combines the changes from one branch into another one. \
                                This is how you bring your work back to a branch after finishing a feature.
                                
                                git switch main      # first, you switch to the branch you want to merge into
                                git merge feature    # then you merge the feature branch into main
                                
                                After merging, the main branch contains all the changes from feature. \
                                The feature branch still exists, and you can delete it if you no longer need it.
                                
                                git branch -d feature""",
                        """
                                Git has two main merge strategies: fast-forward merge and 3-way merge. \
                                Fast-forward merge happens when the target branch has not moved since the feature branch was created, \
                                in which case git simply moves the pointer forward. In 3-way merge, both branches have new commits. \
                                Git creates a new merge commit that has two parents.
                                
                                git merge --no-ff feature    # force a merge commit (even if fast forward is possible)
                                git merge --squash feature   # combine all feature commits into one before merging"""
                ),

                buildSection(
                        "section-2-4", "Merge Conflicts", 4, "section-2-3",
                        """
                                A merge conflict happens when two branches have changed the same part of the same file(s). \
                                Git cannot decide on its own which version to keep. Thus you are asked to resolve it manually.
                                
                                git switch main
                                git merge feature   # feature has changed the same file as main
                                # CONFLICT (content): Merge conflict in main.py
                                # Automatic merge failed; fix conflicts and then commit the result.
                                
                                Git marks the conflict inside the file:
                                
                                <<<<<<< HEAD (Current Change)
                                your version of the code
                                =======
                                the feature branch version
                                >>>>>>> feature (Incoming Change)
                                
                                To resolve the merge conflict, choose which version to keep, \
                                the conflict markers will get removed. Then stage the file and commit.
                                
                                git add main.py
                                git commit -m "Resolve merge conflict" """,
                        """
                                To prevent conflicts, you should keep your branches short-lived and merge often \
                                (for example, when you are done developing a feature). Long running branches that \
                                diverge significantly and miscommunication with teammates are the main causes of complex conflicts.
                                
                                git merge --abort    # cancel the merge, go back to the previous state
                                git diff --merge     # see all conflicts
                                git mergetool        # open a visual tool for merge resolution"""
                ),

                buildSection(
                        "section-2-5", "Git Rebase", 5, "section-2-4",
                        """
                                `git rebase` moves your branch so that it starts from the top of another branch. \
                                This results in a cleaner and linear history, without any merge commits.
                                
                                git switch feature
                                git rebase main   # reapply feature commits on top of main
                                
                                Before rebase:
                                main -> A -> B -> C
                                feature -> A -> D -> E
                                
                                After rebase:
                                main -> A -> B -> C
                                feature -> A -> B -> C -> D' -> E'
                                
                                Important: Never rebase commits that have been pushed to a shared repository. \
                                It rewrites history and will cause problems for your teammates.""",
                        """
                                Interactive rebase lets you edit, squash, reorder or drop commits before merging.
                                
                                git rebase -i HEAD~3   # interactively edit the last 3 commits
                                
                                In the editor you can:
                                1. pick: keep the commit as is
                                2. squash: combine with the previous commit
                                3. reword: change the commit message
                                4. drop: ignore commit entirely
                                
                                git rebase --abort     # cancel the rebase
                                git rebase --continue  # continue after resolving a conflict"""
                ),

                buildSection(
                        "section-2-6", "Git Stash", 6, "section-2-5",
                        """
                                `git stash` temporarily saves your uncommitted changes, so you can switch branches \
                                or work on something else without losing your work. \
                                You can restore a stash with `git stash pop` or delete it with `git stash drop`.
                                
                                git stash       # save current changes and clean the working directory
                                git stash pop   # restore the most recently stashed changes
                                git stash list  # see all stashed changes""",
                        """
                                You can add descriptions to stashes, make multiple ones and create a branch from a stash.
                                
                                git stash push -m "WIP on landing page"  # stash with a description
                                git stash pop stash@{2}                  # restore a specific stash
                                git stash drop stash@{0}                 # delete a specific stash
                                git stash clear                          # delete all stashes
                                git stash branch feature                 # create a branch from a stash
                                git stash -u                             # include untracked files in the stash"""
                ),

                buildSection(
                        "section-2-7", "Git Tags", 7, "section-2-6",
                        """
                                In git, a tag marks a specific commit as important. \
                                Tags are mainly used to mark release versions of your project.
                                
                                git tag                       # list all tags
                                git tag v1.0                  # create a tag on the current commit
                                git tag v1.0 a2c5d1c          # create a tag on a specific commit
                                
                                Unlike branches, tags do not move when you make new commits. \
                                They always point to the same commit.""",
                        """
                                Git has two types of tags: lightweight and annotated tags. \
                                A lightweight tag is a pointer to a commit, while an annotated tag is a git object \
                                with a message, author and date. Annotated tags are recommended for releases.
                                
                                git tag v1.0                               # lightweight tag
                                git tag -a v2.0 -m "Release version 2.0"  # annotated tag
                                git show v2.0                              # view tag details
                                
                                Tags are not pushed to remote repositories by default.
                                
                                git push origin v2.0       # push a specific tag
                                git push origin --tags     # push all tags"""
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