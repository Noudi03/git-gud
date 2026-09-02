package com.app.gitgud.controller;

import com.app.gitgud.model.User;
import com.app.gitgud.model.dto.LoginRequestDto;
import com.app.gitgud.model.dto.RegisterRequestDto;
import com.app.gitgud.service.AuthenticationService;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final CurrentUserService currentUserService;
    private final UserService userService;

    public AuthController(
            AuthenticationService authenticationService,
            CurrentUserService currentUserService,
            UserService userService
    ) {
        this.authenticationService =
                authenticationService;

        this.currentUserService =
                currentUserService;

        this.userService =
                userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequestDto request
    ) {

        if (
                request.getIdToken() == null ||
                        request.getIdToken().isBlank()
        ) {
            return ResponseEntity
                    .badRequest()
                    .body("Missing ID token");
        }

        if (
                request.getUsername() == null ||
                        request.getUsername().isBlank()
        ) {
            return ResponseEntity
                    .badRequest()
                    .body("Missing username");
        }

        try {
            FirebaseToken token =
                    verifyToken(
                            request.getIdToken()
                    );

            User user =
                    new User(
                            token.getUid(),
                            token.getEmail(),
                            request.getUsername()
                    );

            userService.createUser(user);

            currentUserService.login(
                    token.getUid()
            );

            return ResponseEntity
                    .ok()
                    .build();

        } catch (FirebaseAuthException e) {

            return ResponseEntity
                    .status(401)
                    .body("Invalid Firebase token");

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Could not create user");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDto request
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
                    verifyToken(
                            request.getIdToken()
                    );

            currentUserService.login(
                    token.getUid()
            );

            return ResponseEntity
                    .ok()
                    .build();

        } catch (FirebaseAuthException e) {

            return ResponseEntity
                    .status(401)
                    .body("Invalid Firebase token");

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Could not log in");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        currentUserService.logout();

        return ResponseEntity
                .ok()
                .build();
    }

    private FirebaseToken verifyToken(
            String idToken
    ) throws FirebaseAuthException {

        return authenticationService.verifyIdToken(
                idToken
        );
    }
}
