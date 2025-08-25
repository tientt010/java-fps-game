package fpsgame.network.protocol;

public class LoginRequest extends BaseMessage {
    public String username;
    public String password;

    public LoginRequest() {
        this.type = "LOGIN_REQUEST";
    }

    public LoginRequest(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
