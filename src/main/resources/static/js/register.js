import { createUserWithEmailAndPassword } from
        "https://www.gstatic.com/firebasejs/12.17.1/firebase-auth.js";

import { auth } from "./firebase.js";


const registerForm = document.getElementById("register-form");

registerForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword =
        document.getElementById("confirm-password").value;


    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return;
    }


    try {

        const userCredential =
            await createUserWithEmailAndPassword(
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

        console.error("Registration failed:", error);

        alert(error.message);
    }
});
