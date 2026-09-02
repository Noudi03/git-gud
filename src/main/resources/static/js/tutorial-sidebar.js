const chapters =
    document.querySelectorAll(".tutorial-chapter");


chapters.forEach((chapter) => {

    const toggle =
        chapter.querySelector(
            ".tutorial-chapter-toggle"
        );

    if (!toggle) {
        return;
    }


    toggle.addEventListener("click", () => {

        const isExpanded =
            chapter.classList.contains("expanded");


        chapters.forEach((otherChapter) => {

            otherChapter.classList.remove(
                "expanded"
            );

            const otherToggle =
                otherChapter.querySelector(
                    ".tutorial-chapter-toggle"
                );

            if (otherToggle) {
                otherToggle.setAttribute(
                    "aria-expanded",
                    "false"
                );
            }
        });


        if (!isExpanded) {

            chapter.classList.add(
                "expanded"
            );

            toggle.setAttribute(
                "aria-expanded",
                "true"
            );

        }

    });

});
