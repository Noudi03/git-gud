import { initializeApp } from
        "https://www.gstatic.com/firebasejs/12.17.1/firebase-app.js";

import { getAuth } from
        "https://www.gstatic.com/firebasejs/12.17.1/firebase-auth.js";


const firebaseConfig = {
    apiKey: "AIzaSyA8GgTItw8Qv5VhpelRrzOKF9D9VC-9iVM",
    authDomain: "git-good-aab64.firebaseapp.com",
    projectId: "git-good-aab64",
    storageBucket: "git-good-aab64.firebasestorage.app",
    messagingSenderId: "361348619001",
    appId: "1:361348619001:web:5f070486fa38835cfb6028",
    measurementId: "G-M3H62EQR2P"
};


const app = initializeApp(firebaseConfig);

export const auth = getAuth(app);
