import { signOut } from
        "https://www.gstatic.com/firebasejs/12.17.1/firebase-auth.js";

import { auth } from "./firebase.js";


const logoutButton = document.getElementById("logout-button");

if (logoutButton) {

    logoutButton.addEventListener("click", async (event) => {

        event.preventDefault();

        try {

            await signOut(auth);

            const response = await fetch("/api/auth/logout", {
                method: "POST"
            });

            if (!response.ok) {
                throw new Error("Could not log out of application.");
            }

            window.location.href = "/login";

        } catch (error) {

            console.error("Logout failed:", error);

        }

    });

}
