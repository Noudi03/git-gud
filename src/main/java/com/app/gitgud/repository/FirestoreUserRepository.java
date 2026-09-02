package com.app.gitgud.repository;

import com.app.gitgud.model.User;
import com.google.cloud.firestore.DocumentSnapshot;
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


        return new User(
                document.getId(),
                document.getString("email"),
                document.getString("username")
        );
    }
}
