package riswan.fkti.pipa.models.auth;

public class Login {

    public String username, plain;

    public Login(String username, String plain){
        this.username = username;
        this.plain = plain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }
}
