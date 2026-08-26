import { signInWithEmailAndPassword } from
        "https://www.gstatic.com/firebasejs/12.17.1/firebase-auth.js";

import { auth } from "./firebase.js";


const loginForm = document.getElementById("login-form");
const errorMessage = document.getElementById("login-error");


loginForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    errorMessage.textContent = "";

    try {

        const userCredential =
            await signInWithEmailAndPassword(
                auth,
                email,
                password
            );

        const user = userCredential.user;

        const idToken = await user.getIdToken();

        const response = await fetch("/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                idToken: idToken
            })
        });

        if (!response.ok) {
            throw new Error("Could not create application session.");
        }

        window.location.href = "/home";

    } catch (error) {

        console.error("Login failed:", error);

        errorMessage.textContent = getLoginErrorMessage(error);
    }
});


function getLoginErrorMessage(error) {

    switch (error.code) {

        case "auth/invalid-credential":
            return "Incorrect email or password.";

        case "auth/user-disabled":
            return "This account has been disabled.";

        case "auth/too-many-requests":
            return "Too many login attempts. Please try again later.";

        default:
            return "Unable to log in. Please try again.";
    }
}
