package com.app.gitgud.seeder.data.chapter1;
/*
import com.app.gitgud.model.Section;

import java.util.Arrays;
import java.util.List;

// The initial form of this file, more specifically the getSections() part, was generated using Claude LLM.
// All the curriculum and questions were written by hand in the Obsidian notetaking app

public class Chapter1Curriculum {

    public static List<Section> getSections() {
        return Arrays.asList(

                buildSection(
                        "section-1-1", "What is Git?", 1, null,
                        """
                                **Git** is a version control system. It tracks changes in your files over time. \
                                You can think of it as a **save history for your code**. If you break something, \
                                you can always go back to a previous version.

                                ### With Git You Can

                                1. Save snapshots of your work at any point.
                                2. Work on different features without breaking the main code.
                                3. Collaborate with others without overwriting each other's code.
                                """,
                        """
                                Git is a **distributed version control system**, meaning it is decentralized.

                                Every developer has a full copy of the repository, including its entire history. \
                                This means you can perform many Git operations locally without needing a connection \
                                to a remote repository.
                                """
                ),

                buildSection(
                        "section-1-2", "Initial Configuration", 2, "section-1-1",
                        """
                                Before you start working with Git, you need to specify your name and email. \
                                Git attaches this information to every commit you make. You usually only need \
                                to configure this once on your computer.

                                ### Configure Your Identity

                                ```bash
                                git config --global user.name "Your name"
                                git config --global user.email "your@email.com"
                                ```

                                The `--global` parameter applies the configuration to all Git projects for \
                                your user on this computer.

                                You can view your current configuration with:

                                ```bash
                                git config --list
                                ```

                                Without a configured identity, Git does not know who is making the commits.
                                """,
                        """
                                Git has **three configuration levels**:

                                1. **`--global`**: applies to all projects for a user.
                                2. **`--local`**: applies only to the current project.
                                3. **`--system`**: applies to all users on the machine.

                                ### Configuration Files

                                Global configuration is typically stored in:

                                ```text
                                ~/.gitconfig
                                ```

                                Local repository configuration is stored in:

                                ```text
                                .git/config
                                ```

                                Local configuration takes precedence over global configuration.
                                """
                ),

                buildSection(
                        "section-1-3", "Git Init", 3, "section-1-2",
                        """
                                `git init` is the command you run when starting a new Git project. \
                                It tells Git to start tracking the folder you are currently in.

                                ### Initialize a Repository

                                ```bash
                                cd my-project
                                git init
                                ```

                                After running `git init`, Git creates a hidden folder called `.git` inside \
                                your project folder. This folder contains the history and internal information \
                                Git needs to manage your project.

                                After running the command, the folder is now a **Git repository**. Git is watching \
                                for changes, but it has not saved any snapshots yet.

                                **Important:** Deleting the `.git` folder removes Git tracking from the project. \
                                Your project files remain intact, but the repository's Git history and configuration \
                                are deleted.
                                """,
                        """
                                The `.git` folder contains everything Git needs to manage the repository.

                                ### What's Inside `.git`?

                                1. **`HEAD`**: points to the current branch or commit.
                                2. **`objects/`**: stores Git's objects, including file contents and commits.
                                3. **`refs/`**: stores references such as branches and tags.
                                4. **`config`**: stores local repository settings.

                                The `.git` directory is the internal database that makes the current folder a Git repository.
                                """
                ),

                buildSection(
                        "section-1-4", "Git Add", 4, "section-1-3",
                        """
                                In order for Git to save your changes, you need to tell it which files should be included. \
                                This is called **staging**.

                                Think of staging as the state between your local changes and the next commit.

                                ### Stage Changes

                                ```bash
                                git add file1    # stage only file1
                                git add .        # stage all changed files
                                ```

                                The files are now **staged**, meaning they are ready to be saved in a commit.

                                You can stage multiple files before committing:

                                ```bash
                                git add file1 file2 file3
                                ```

                                Staging does not permanently save your changes. To save them in Git's history, \
                                you still need to create a commit.
                                """,
                        """
                                The staging area is also called the **index**. It lives inside the `.git` directory.

                                When you run `git add`, Git records the version of the file that exists at that \
                                exact moment in the staging area.

                                This means that if you modify the file after running `git add`, but before committing, \
                                the commit will contain the **staged version**, not the latest version of the file.

                                ### Stage Parts of a File

                                You can interactively choose which parts of a file to stage using patch mode:

                                ```bash
                                git add -p file1
                                ```

                                This is useful when a file contains multiple unrelated changes and you only want \
                                some of them to be included in the next commit.
                                """
                ),

                buildSection(
                        "section-1-5", "Git Commit", 5, "section-1-4",
                        """
                                `git commit` is the command that saves your staged changes into Git's history. \
                                Essentially, it creates a **snapshot of your project** at that exact moment.

                                Every commit should have a message that describes what you changed. Good commit \
                                messages help you and your collaborators understand the history of the project.

                                ### Create a Commit

                                ```bash
                                git commit -m "Initial commit"
                                ```

                                ### Writing Good Commit Messages

                                Some guidelines for writing commit messages are:

                                1. Keep them **short and descriptive**.
                                2. Describe **what changed**, not how it was implemented.
                                3. Use the **imperative mood**, such as "Add login page" instead of "Added login page".

                                After running `git commit`, your staged changes are saved in Git's history and \
                                the staging area is cleared.

                                You can always return to that point in the future.

                                ### Amend a Commit

                                You can modify the most recent commit with `git commit --amend`:

                                ```bash
                                git commit --amend -m "Fix message"  # edit the last commit message
                                git commit -am "Fix bug"             # stage tracked files and commit
                                ```

                                **Important:** Do not amend commits that have already been pushed to a shared repository. \
                                Amending changes the commit and rewrites history.
                                """,
                        """
                                Every commit in Git is identified by a unique **SHA-1 hash**.

                                A commit object contains:

                                - A pointer to the **tree**, which represents the snapshot of files.
                                - A pointer to the **parent commit**, which represents the previous snapshot.
                                - The **author**, date, and commit message metadata.

                                The chain of parent pointers forms a **DAG (Directed Acyclic Graph)**, \
                                which is the backbone of Git's history.

                                ```
                                Commit A
                                   |
                                   v
                                Commit B
                                   |
                                   v
                                Commit C
                                ```

                                Branches and merges can cause this history to branch and join, which is why Git's \
                                history is represented as a graph rather than a simple list.
                                """
                ),

                buildSection(
                        "section-1-6", "Log and Diff", 6, "section-1-5",
                        """
                                With `git log`, you can see the list of commits made in the project. \
                                Each entry shows information such as the commit hash, author, date, and commit message.

                                ### View Commit History

                                ```bash
                                git log               # show the full history
                                git log --oneline     # show a compact history
                                ```

                                There is also a way to see exactly what changed in your files using `git diff`.

                                ### View Changes

                                ```bash
                                git diff              # show changes that are not yet staged
                                git diff --staged     # show changes that are already staged
                                ```
                                """,
                        """
                                Git provides several options for exploring your history and comparing changes.

                                ### Explore Commit History

                                ```bash
                                git log --oneline --graph --all   # visualize the branch tree
                                git log --author="name"           # filter by author
                                git log --since="3 weeks ago"     # filter by date
                                ```

                                ### Compare Commits

                                You can compare two different commits using:

                                ```bash
                                git diff commit1 commit2
                                ```

                                `git diff` uses a **unified diff format**:

                                - Lines starting with `+` represent additions.
                                - Lines starting with `-` represent deletions.
                                - Lines with no prefix represent unchanged context.
                                """
                ),

                buildSection(
                        "section-1-7", "Ignore Files", 7, "section-1-6",
                        """
                                Not every file in your project should be tracked by Git.

                                For example, you generally should not commit files containing **credentials or API keys**, \
                                log files, or automatically generated files.

                                The **`.gitignore`** file tells Git which files and folders should be ignored.

                                ### Create a .gitignore File

                                ```bash
                                touch .gitignore
                                ```

                                This creates a `.gitignore` file in your project root.

                                ### Example .gitignore

                                When working with Python, you might have:

                                ```gitignore
                                test/           # ignore this entire folder
                                .env            # ignore this specific file
                                *.log           # ignore all files ending in .log
                                __pycache__/    # ignore this entire folder
                                ```

                                If a file was already tracked by Git before being added to `.gitignore`, \
                                Git will continue to track it.

                                You need to remove it from tracking first:

                                ```bash
                                git rm --cached file1
                                ```
                                """,
                        """
                                `.gitignore` supports patterns that allow you to ignore groups of files while \
                                making exceptions for specific files.

                                ### Ignore Everything Except a Specific File

                                ```gitignore
                                *.txt            # ignore all .txt files
                                !important.txt   # except important.txt
                                ```

                                The `!` prefix creates an exception to an ignore rule.

                                ### Force Add an Ignored File

                                You can stage a file that is normally ignored by using the `--force` flag:

                                ```bash
                                git add -f file1
                                ```

                                This tells Git to stage the file even though it matches a `.gitignore` rule.
                                """
                )
        );
    }

    // builder
    private static Section buildSection(String id, String title, int order, String prerequisiteId, String content,
                                        String advancedContent) {
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
*/