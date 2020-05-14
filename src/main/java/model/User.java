package model;

public class User {
    private String navn;
    private String password;
    private String email;
    private String rolle;

    public User() {
    }

    public User(String navn, String password, String email, String rolle) {
        this.navn = navn;
        this.password = password;
        this.email = email;
        this.rolle = rolle;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }
}
