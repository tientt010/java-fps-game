package fpsgame.network.protocol;

public class LoginResponse extends BaseMessage {
    public int userId;
    public String username;
    public String email;
    public Long score;
    public boolean isSuccess;
    public String message;

    public LoginResponse() {
        this.type = "LOGIN_RESPONSE";
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                '}';
    }
}
