package com.app.gitgud.seeder.data.chapter1;

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
                                Git is a version control system. It tracks changes in your files over time. \
                                It functions like a save history for your code. If you break something you can always go back to a previous version.
                                
                                With git you can:
                                1. Save snapshots of your work at any point
                                2. Work on different features without breaking the main code
                                3. Collaborate with others without overwriting each other's code""",
                        "Git is a distributed version control system, meaning it is decentralized. " +
                                "Every developer has a full copy of the repository, including its entire history."
                ),

                buildSection(
                        "section-1-2", "Initial Configuration", 2, "section-1-1",
                        """
                                Before you start working with git, you need to specify your email and name. \
                                Git attaches them to every commit you make. You only have to do it once on your computer.
                                
                                git config --global user.name "Your name"
                                git config --global user.email "your@email.com"
                                
                                The parameter --global applies the configuration to all git projects on this computer. \
                                Without it git doesn't know who is making the commits. \
                                You can view your configuration with the command: git config --list""",
                        """
                                Git has 3 configuration levels:
                                1. --global: applies to all projects for a user
                                2. --local: applies to only the current project
                                3. --system: applies to all users on this machine
                                
                                The configuration is stored in ~/.gitconfig (global) or .git/config (local)"""
                ),

                buildSection(
                        "section-1-3", "Git Init", 3, "section-1-2",
                        """
                                git init is the very first command you run when starting a new git project. \
                                It tells git to start tracking the folder you are currently in.
                                
                                After running git init, git creates a folder called .git inside your project folder. \
                                This folder is where git stores all the history and information about your project. \
                                This folder is hidden by default.
                                
                                cd my-project
                                git init
                                
                                After running the command, the folder is now considered a repository. \
                                Git is watching for changes but it hasn't saved anything yet. \
                                Deleting .git completely removes git tracking from your project. \
                                This means that your project will stay intact, but the history will be deleted.""",
                        """
                                The .git folder contains everything git needs:
                                1. HEAD — points to the current branch
                                2. objects/ — stores all your file snapshots
                                3. refs/ — stores branch and tag pointers
                                4. config — stores local repository settings"""
                ),

                buildSection(
                        "section-1-4", "Git Add", 4, "section-1-3",
                        """
                                In order for git to start saving your changes you need to tell it which files to save. \
                                This is called staging. Think of staging as the state between your local changes and the production code.
                                
                                git add file1    # stage only file1
                                git add .        # stage all changed files
                                
                                The files are now staged, meaning they are ready to be saved. \
                                For them to get saved permanently you need to commit them. \
                                You can stage multiple files before committing.""",
                        """
                                The staging area is called the index. The index lives inside the .git folder as a binary file. \
                                When you run git add, git creates a snapshot of the file at that exact moment and stores it in the object database.
                                
                                1. If you modify the file after git add but before git commit, the commit will contain the staged version, not the latest one.
                                2. You can stage parts of a file with git add -p. This is called patch mode.
                                
                                git add -p file1    # stage parts of a file"""
                ),

                buildSection(
                        "section-1-5", "Git Commit", 5, "section-1-4",
                        """
                                Git commit is the command that saves your staged changes into git's history. \
                                Essentially, it creates a snapshot of your project at that exact moment.
                                
                                Every commit requires a message that describes what you changed, \
                                which will help you and your collaborators understand the history of your project.
                                
                                git commit -m "Initial commit"
                                
                                Writing a good commit message is very important. Some guidelines are:
                                1. Short and descriptive
                                2. Describes what changed, not how it changed
                                3. Use imperative mood
                                
                                After running git commit your changes are saved in git's history, \
                                the staging area is cleared and you can always come back to that point in the future.
                                
                                You can amend a commit by running:
                                git commit --amend "Fix message"    # edit the last commit message
                                git commit -am "Fix bug"            # stage all tracked files and commit
                                
                                Important: Do not amend commits that have already been pushed to a shared repository.""",
                        """
                                Every commit in git is identified by a unique SHA-1 hash. \
                                A commit object contains:
                                - A pointer to the tree (snapshot of files)
                                - A pointer to the parent commit (previous snapshot)
                                - The author, date and message metadata
                                
                                The chain of parent pointers forms the DAG (Directed Acyclic Graph), \
                                which is considered the backbone of git's history."""
                ),

                buildSection(
                        "section-1-6", "Log and Diff", 6, "section-1-5",
                        """
                                With git log you can see the list of all commits made in the project. \
                                Each entry shows the commit hash, the author and date, and the commit message.
                                
                                git log               # full history
                                git log --oneline     # cleaner, one line per commit
                                
                                There is a way to check exactly what changed in your files, using the git diff command.
                                
                                git diff              # changes not yet staged
                                git diff --staged     # changes already staged""",
                        """
                                There are options for viewing a visual branch tree, filtering commits and comparing 2 different commits.
                                
                                git log --oneline --graph --all     # visual branch tree
                                git log --author="name"             # filter by author name
                                git log --since="3 weeks ago"       # filter by date
                                git diff commit1 commit2             # compare 2 different commits
                                
                                git diff uses a unified diff format:
                                - Lines starting with + are additions
                                - Lines starting with - are deletions
                                - Lines with no prefix represent unchanged context"""
                ),

                buildSection(
                        "section-1-7", "Ignore Files", 7, "section-1-6",
                        """
                                Not every file in your project should be tracked by git. \
                                For example, files containing credentials or API keys, log files, \
                                and automatically generated files should not be tracked.
                                
                                The .gitignore file tells git which files and folders to completely ignore.
                                
                                touch .gitignore    # create a .gitignore file in your project root
                                
                                An example .gitignore when working with Python:
                                test/           # ignore this entire folder
                                .env            # ignore this specific file
                                *.log           # ignore all files ending in .log
                                __pycache__/    # ignore this entire folder
                                
                                If a file was already tracked by git before being added to .gitignore, \
                                git will continue to track it. You need to untrack it first:
                                git rm --cached file1""",
                        """
                                You can ignore all files matching a condition, except manually specified ones.
                                
                                *.txt           # ignore all txt files
                                !important.txt  # except important.txt
                                
                                You can also stage a file that is ignored by a condition by using the --force flag:
                                git add -f file1"""
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