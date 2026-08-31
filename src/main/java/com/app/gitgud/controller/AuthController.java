package com.app.gitgud.controller;

import com.app.gitgud.model.dto.LoginRequestDto;
import com.app.gitgud.service.AuthenticationService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(
            AuthenticationService authenticationService
    ) {
        this.authenticationService =
                authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDto request,
            HttpSession session
    ) {

        if (
                request.getIdToken() == null ||
                        request.getIdToken().isBlank()
        ) {
            return ResponseEntity
                    .badRequest()
                    .body("Missing ID token");
        }

        try {

            FirebaseToken token =
                    authenticationService.verifyIdToken(
                            request.getIdToken()
                    );

            session.setAttribute(
                    "uid",
                    token.getUid()
            );

            session.setAttribute(
                    "email",
                    token.getEmail()
            );

            return ResponseEntity.ok().build();

        } catch (FirebaseAuthException e) {

            return ResponseEntity
                    .status(401)
                    .body("Invalid Firebase token");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpSession session
    ) {

        session.invalidate();

        return ResponseEntity.ok().build();
    }
}
