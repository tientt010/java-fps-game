package fpsgame.network.protocol;

public class RegisterRequest extends BaseMessage {
    public String username;
    public String password;
    public String email;

    public RegisterRequest() {
        this.type = "REGISTER_REQUEST";
    }

    public RegisterRequest(String username, String password, String email) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
