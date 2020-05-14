package model;

import persistence.FirestoreDB;

import java.sql.Timestamp;

public class Chat {
    private final FirestoreDB db = FirestoreDB.getInstance();

    public void sendMessage(User author, String recipientEmail, String messageBody) {
        if (db.getUserByEmail(recipientEmail) == null) {
            return;
        }
        User recipient = db.getUserByEmail(recipientEmail);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Message message = new Message(author.getNavn(), recipient.getNavn(), messageBody, now.toString());
        db.sendMessageToUser(author.getEmail(), recipientEmail, message);
    }

    public void readMessages(User user) {
        for (Message message : db.readMessagesForEmail(user.getEmail())) {
            System.out.println(message.getBesked());
        }
    }
}
