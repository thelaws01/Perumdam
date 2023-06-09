package riswan.fkti.pipa.models.auth;

public class Register {

    private String username, password, status;

    public Register(String username,String password){
        this.username = username;
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 5;
    }

}
