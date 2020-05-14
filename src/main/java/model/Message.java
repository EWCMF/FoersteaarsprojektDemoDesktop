package model;

public class Message {
    private String sender;
    private String modtager;
    private String besked;
    private String tidspunkt;

    public Message() {
    }

    public Message(String sender, String modtager, String besked, String tidspunkt) {
        this.sender = sender;
        this.modtager = modtager;
        this.besked = besked;
        this.tidspunkt = tidspunkt;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getModtager() {
        return modtager;
    }

    public void setModtager(String modtager) {
        this.modtager = modtager;
    }

    public String getBesked() {
        return besked;
    }

    public void setBesked(String besked) {
        this.besked = besked;
    }

    public String getTidspunkt() {
        return tidspunkt;
    }

    public void setTidspunkt(String tidspunkt) {
        this.tidspunkt = tidspunkt;
    }
}
