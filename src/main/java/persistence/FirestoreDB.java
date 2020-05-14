package persistence;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import model.Message;
import model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreDB {
    private final Firestore db;
    private static FirestoreDB instance = null;


    private FirestoreDB() {
        initializeDB();
        db = FirestoreClient.getFirestore();
    }

    private void initializeDB() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("accessKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://foersteaarsprojekt.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static FirestoreDB getInstance() {
        if (instance == null) {
            instance = new FirestoreDB();
        }
        return instance;
    }
    public void sendMessageToUser(String authorMail, String recipientMail, Message message) {
        Query query = db.collection("chat").whereEqualTo("sender", authorMail).whereEqualTo("modtager", recipientMail);
        try {
            if (query.get().get().isEmpty()) {
                Map<String, Object> newChat = new HashMap<>();
                newChat.put("sender", authorMail);
                newChat.put("modtager", recipientMail);
                db.collection("chat").document().set(newChat);
            }
            query.get().get().getDocuments().get(0).getReference().collection("beskeder").document().create(message);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Message> readMessagesForEmail(String email) {
        ArrayList<Message> messages = new ArrayList<>();
        ApiFuture<QuerySnapshot> query = db.collection("chat").whereEqualTo("modtager", email).get();
        try {
            if (!query.get().isEmpty()) {
                List<QueryDocumentSnapshot> snapshots = query.get().getDocuments();
                for (QueryDocumentSnapshot snapshot : snapshots) {
                    ApiFuture<QuerySnapshot> subQuery = snapshot.getReference().collection("beskeder").orderBy("timestamp").get();
                    List<QueryDocumentSnapshot> documents = subQuery.get().getDocuments();
                    for (QueryDocumentSnapshot document : documents) {
                        Message message = document.toObject(Message.class);
                        messages.add(message);
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void createAccount(String name, String password, String email, String role) {
        Map<String, Object> newAccount = new HashMap<>();
        newAccount.put("navn", name);
        newAccount.put("password", password);
        newAccount.put("email", email);
        newAccount.put("rolle", role);
        db.collection("brugere").document(email).create(newAccount);
    }

    public User getUserByEmail(String email) {
        ApiFuture<DocumentSnapshot> document = db.collection("brugere").document(email).get();
        User user = null;
        try {
            if (document.get().exists()) {
                user = document.get().toObject(User.class);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return user;
    }


}
