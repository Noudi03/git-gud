import { createUserWithEmailAndPassword } from
        "https://www.gstatic.com/firebasejs/12.17.1/firebase-auth.js";

import { auth } from "./firebase.js";


const registerForm =
    document.getElementById("register-form");


registerForm.addEventListener("submit", async (event) => {

    event.preventDefault();


    const username =
        document.getElementById("username").value;

    const email =
        document.getElementById("email").value;

    const password =
        document.getElementById("password").value;

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


        const user =
            userCredential.user;


        const idToken =
            await user.getIdToken();


        const response =
            await fetch("/api/auth/register", {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    idToken: idToken,
                    username: username
                })
            });


        if (!response.ok) {

            throw new Error(
                "Could not create application account."
            );
        }


        window.location.href =
            "/home";


    } catch (error) {

        console.error(
            "Registration failed:",
            error
        );

        alert(
            getRegisterErrorMessage(error)
        );
    }
});


function getRegisterErrorMessage(error) {

    switch (error.code) {

        case "auth/email-already-in-use":
            return "An account with this email already exists.";

        case "auth/invalid-email":
            return "Please enter a valid email address.";

        case "auth/weak-password":
            return "Your password is too weak.";

        case "auth/operation-not-allowed":
            return "Registration is currently unavailable.";

        default:
            return "Unable to create your account. Please try again.";
    }
}
