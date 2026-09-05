package com.app.gitgud.repository;

import com.app.gitgud.model.User;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class FirestoreUserRepository implements UserRepository {

    private static final int MIN_RATING = 0;

    private final Firestore db;

    public FirestoreUserRepository(
            Firestore db
    ) {
        this.db = db;
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

        if (rating == null) {
            return new User(
                    document.getId(),
                    document.getString("email"),
                    document.getString("username")
            );
        }

        return new User(
                document.getId(),
                document.getString("email"),
                document.getString("username"),
                Math.max(
                        MIN_RATING,
                        rating.intValue()
                )
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
                        Math.max(
                                MIN_RATING,
                                rating
                        )
                )
                .get();
    }

    @Override
    public void changeRating(
            String userId,
            int ratingChange
    ) throws Exception {

        var userReference =
                db.collection("users")
                        .document(userId);

        db.runTransaction(
                transaction -> {

                    DocumentSnapshot document =
                            transaction.get(
                                    userReference
                            ).get();

                    if (!document.exists()) {
                        throw new IllegalArgumentException(
                                "User not found"
                        );
                    }

                    Long storedRating =
                            document.getLong("rating");

                    int currentRating =
                            storedRating == null
                                    ? User.DEFAULT_RATING
                                    : storedRating.intValue();

                    int newRating =
                            Math.max(
                                    MIN_RATING,
                                    currentRating + ratingChange
                            );

                    transaction.update(
                            userReference,
                            "rating",
                            newRating
                    );

                    return null;
                }
        ).get();
    }
}
