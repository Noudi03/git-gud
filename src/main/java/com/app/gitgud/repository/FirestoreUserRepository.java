package com.app.gitgud.repository;

import com.app.gitgud.config.GitGudProperties;
import com.app.gitgud.model.User;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class FirestoreUserRepository
        implements UserRepository {

    private final Firestore db;
    private final GitGudProperties properties;

    public FirestoreUserRepository(
            Firestore db,
            GitGudProperties properties
    ) {

        this.db = db;
        this.properties = properties;
    }

    @Override
    public void createUser(
            User user
    ) throws Exception {

        db.collection("users")
                .document(user.getId())
                .set(user)
                .get();
    }

    @Override
    public User getUser(
            String userId
    ) throws Exception {

        DocumentSnapshot document =
                db.collection("users")
                        .document(userId)
                        .get()
                        .get();

        if (!document.exists()) {
            return null;
        }

        Long rating =
                document.getLong("rating");

        int resolvedRating =
                rating == null
                        ? properties.getRating().getDefaultRating()
                        : clampRating(
                        rating.intValue()
                );

        return new User(
                document.getId(),
                document.getString("email"),
                document.getString("username"),
                resolvedRating
        );
    }

    @Override
    public void setRating(
            String userId,
            int rating
    ) throws Exception {

        db.collection("users")
                .document(userId)
                .update(
                        "rating",
                        clampRating(rating)
                )
                .get();
    }

    private int clampRating(
            long rating
    ) {

        int minimum =
                properties.getRating().getMin();

        if (rating <= minimum) {
            return minimum;
        }

        if (rating >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        return (int) rating;
    }
}
