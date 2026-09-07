package com.app.gitgud.controller;

import com.app.gitgud.model.dto.LeaderboardDto;
import com.app.gitgud.service.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leaderboards")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(
            LeaderboardService leaderboardService
    ) {
        this.leaderboardService =
                leaderboardService;
    }

    @GetMapping
    public ResponseEntity<?> getLeaderboard(
            @RequestParam(required = false)
            String quizKey
    ) {

        try {

            LeaderboardDto leaderboard =
                    leaderboardService.getLeaderboard(
                            quizKey
                    );

            return ResponseEntity.ok(
                    leaderboard
            );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not get leaderboard"
                    );
        }
    }
}
