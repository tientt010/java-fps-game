package fpsgame.network.protocol;

public class RegisterResponse extends BaseMessage {
    public boolean isSuccess;
    public String message;

    public RegisterResponse() {
        this.type = "REGISTER_RESPONSE";
    }

    public RegisterResponse(boolean isSuccess, String message) {
        this();
        this.isSuccess = isSuccess;
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                '}';
    }
}
