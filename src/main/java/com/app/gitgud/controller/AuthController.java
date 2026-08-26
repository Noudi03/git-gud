package com.app.gitgud.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> body,
            HttpSession session) {

        String idToken = body.get("idToken");

        if (idToken == null || idToken.isBlank()) {
            return ResponseEntity.badRequest().body("Missing ID token");
        }

        try {

            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance().verifyIdToken(idToken);

            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            session.setAttribute("uid", uid);
            session.setAttribute("email", email);

            return ResponseEntity.ok().build();

        } catch (FirebaseAuthException e) {

            return ResponseEntity.status(401)
                    .body("Invalid Firebase token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok().build();
    }
}
