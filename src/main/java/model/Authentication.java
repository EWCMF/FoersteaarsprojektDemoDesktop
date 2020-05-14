package model;

import org.apache.commons.codec.binary.Hex;
import persistence.FirestoreDB;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authentication {
    private final FirestoreDB db = FirestoreDB.getInstance();
    private boolean signedIn;
    private User currentUser;

    public boolean signIn(String email, String password) {
        User check = db.getUserByEmail(email);
        if (check != null) {
            if (!check.getPassword().equals(encryptText(password))) {
                return false;
            }
        }
        else {
            return false;
        }

        currentUser = db.getUserByEmail(email);
        signedIn = true;
        return true;
    }

    public boolean createAccount(String name, String password, String email, boolean isPractitioner) {
        if (db.getUserByEmail(email) != null) {
            return false;
        }

        String roleString;
        if (isPractitioner) {
            roleString = "behandler";
        }
        else {
            roleString = "klient";
        }

        db.createAccount(name, encryptText(password), email, roleString);
        currentUser = new User(name, encryptText(password), email, roleString);
        signedIn = true;
        return true;
    }

    private String encryptText(String string) {
        String sha256hex = null;
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            sha256hex = new String(Hex.encodeHex(hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha256hex;
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
