package view;

import model.Authentication;
import model.Chat;


public class Main {
    public static void main(String[] args) {
        Authentication authentication = new Authentication();
        Chat chat = new Chat();
        authentication.signIn("t@mail.com", "test123");
        chat.sendMessage(authentication.getCurrentUser(), "nig@mail.com", "Test3");
        chat.readMessages(authentication.getCurrentUser());
    }
}
