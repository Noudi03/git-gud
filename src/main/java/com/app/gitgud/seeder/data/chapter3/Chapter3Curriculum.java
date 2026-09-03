package com.app.gitgud.seeder.data.chapter3;

import com.app.gitgud.model.Section;

import java.util.Arrays;
import java.util.List;

// The initial form of this file, more specifically the getSections() part, was generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter3Curriculum {

    public static List<Section> getSections() {
        return Arrays.asList(

                buildSection(
                        "section-3-1", "Remote Repositories", 1,
                        """
                                A **remote repository** is a repository hosted online on a platform. \
                                It allows you to share your code with others and collaborate. The most popular \
                                platforms for hosting remote repositories are GitHub followed by GitLab and BitBucket.

                                ```bash
                                git clone https://github.com/user/repo.git # download a remote repository
                                git remote -v # list all remote connections
                                git remote add origin https://github.com/user/repo.git # connect to a remote
                                ```

                                When you clone a repository, Git automatically creates a remote connection called \
                                `origin`, which points to the original repository. `origin` is the default name Git \
                                assigns, but you can name your remote anything.

                                **Advanced**

                                You can have multiple remotes pointing at different locations.

                                ```bash
                                git remote add upstream https://github.com/original/repo.git # add a second remote
                                git remote rename origin newname # rename a remote
                                git remote remove origin # remove a remote
                                ```
                                """,
                        """
                                A Git repository does not have to communicate with only one remote.

                                You can configure multiple remote connections, each pointing to a different repository.

                                For example, when working with a fork, you might have:

                                - `origin`: your fork of the repository.
                                - `upstream`: the original repository.

                                This allows you to push your own work to `origin` while fetching updates from \
                                the original project through `upstream`.

                                Remote names such as `origin` and `upstream` are conventions. Git does not require \
                                you to use these names.
                                """
                ),

                buildSection(
                        "section-3-2", "Push and Pull", 2,
                        """
                                `git push` uploads your local commits to the remote repository. `git pull` downloads \
                                the latest changes from the remote and merges them into your local branch.

                                ```bash
                                git push origin main # push local main branch to remote
                                git pull origin main # pull latest changes from remote main
                                ```

                                With push you send your work to the remote, while with pull you get other people's \
                                work from the remote.
                                """,
                        """
                                Git pull is actually two commands in one:

                                ```bash
                                git fetch origin # download changes
                                git merge origin/main # merge them into your branch
                                ```

                                There are several useful variations of push and pull:

                                ```bash
                                git push -u origin main # set upstream tracking, after this you only run git push
                                git push --force # overwrite remote history (use with caution)
                                git push origin --delete feature # delete a remote branch
                                git pull --rebase origin main # pull and rebase instead of merge
                                ```

                                `git push --force` can overwrite commits on the remote repository. Use it with caution, \
                                especially when working with shared branches.

                                The `-u` flag sets up upstream tracking between your local branch and the remote branch. \
                                After that, Git can often determine the remote and branch automatically when you run \
                                `git push` or `git pull`.
                                """
                ),

                buildSection(
                        "section-3-3", "Git Fetch", 3,
                        """
                                `git fetch` downloads changes from the remote repository but does not merge them into \
                                your local branch. It lets you see what others have done before deciding whether to \
                                integrate their changes.

                                ```bash
                                git fetch origin # download all changes from remote
                                git fetch origin main # download changes from a specific branch
                                git log origin/main # view the remote commits after fetching
                                git diff origin/main # see what is different
                                ```
                                """,
                        """
                                The difference between `git fetch` and `git pull` is that with fetch you download the \
                                changes and decide when to merge them, while with pull you download and merge them \
                                automatically.

                                ```bash
                                git fetch --all # fetch from all remotes
                                git fetch --prune # remove remote-tracking branches that no longer exist
                                ```

                                After fetching, the downloaded commits are available through remote-tracking \
                                branches such as `origin/main`. Your current local branch is not changed by the fetch.

                                This makes `git fetch` useful when you want to inspect incoming changes before integrating them.
                                """
                ),

                buildSection(
                        "section-3-4", "Git Revert", 4,
                        """
                                `git revert` creates a new commit that undoes the changes of a previous commit. \
                                It is the safe way to undo changes because it does not rewrite history.

                                ```bash
                                git revert a3b5fc2 # undo the commit with this hash
                                git revert HEAD # undo the most recent commit
                                ```

                                After running `git revert`, Git opens an editor for a commit message. The new commit \
                                will appear in the history, showing that you reversed a previous change.

                                **Advanced**

                                ```bash
                                git revert --no-commit a3b5fc2 # stage the revert without committing immediately
                                git revert HEAD~3..HEAD # revert the last 3 commits
                                ```
                                """,
                        """
                                `git revert` is particularly useful for shared repositories because it preserves \
                                the existing commit history.

                                Instead of removing or changing an existing commit, Git creates another commit whose \
                                changes undo the earlier commit.

                                This means the history clearly shows both the original change and the decision to undo it.

                                The `--no-commit` option applies the revert changes without immediately creating the \
                                revert commit, allowing you to review or combine the changes before committing.
                                """
                ),

                buildSection(
                        "section-3-5", "Git Reset", 5,
                        """
                                `git reset` moves the branch pointer back to a previous commit. Unlike `git revert`, \
                                it rewrites history.

                                There are three main options:

                                1. **`--soft`**: moves the pointer back but keeps your changes staged.
                                2. **`--hard`**: moves the pointer back and discards all changes permanently.
                                3. **`--mixed`**: moves the pointer back and unstages changes, keeping them in the \
                                   working directory. This is the default option.

                                ```bash
                                git reset --soft HEAD~1 # undo last commit but keep changes staged
                                git reset --hard HEAD~1 # undo last commit and delete all changes
                                git reset HEAD~1 # same as --mixed
                                git reset --mixed HEAD~1 # undo last commit, unstage changes, keep files
                                ```
                                """,
                        """
                                The key difference between `revert` and `reset` is that revert adds a new commit that \
                                undoes changes, which is safer for shared repositories.

                                Reset instead moves the branch pointer back, meaning it rewrites history. This can be \
                                dangerous on shared repositories because other developers may already have the commits \
                                that you removed from the branch history.

                                ### Reset Modes

                                - **`--soft`**: the commit is undone, but changes remain staged and ready to commit.
                                - **`--mixed`**: the commit is undone and changes are unstaged but remain in the working directory.
                                - **`--hard`**: the commit is undone and changes are discarded.

                                Because `--hard` can permanently discard uncommitted work, it should be used carefully.
                                """
                )
        );
    }

    private static Section buildSection(
            String id,
            String title,
            int order,
            String content,
            String advancedContent
    ) {
        Section s = new Section();
        s.setId(id);
        s.setTitle(title);
        s.setOrder(order);
        s.setContent(content);
        s.setAdvancedContent(advancedContent);
        return s;
    }
}
