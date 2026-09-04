package com.app.gitgud.repository;

import com.app.gitgud.model.User;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class FirestoreUserRepository
        implements UserRepository {

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
                rating.intValue()
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
                                0,
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

        db.collection("users")
                .document(userId)
                .update(
                        "rating",
                        FieldValue.increment(
                                ratingChange
                        )
                )
                .get();
    }
}
