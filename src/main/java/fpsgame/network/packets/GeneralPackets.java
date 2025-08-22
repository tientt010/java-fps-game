package fpsgame.network.packets;

public class GeneralPackets {
    public static class LoginRequest {
        public String username;
        public String password;

        public LoginRequest() {
            // Default constructor for serialization
        }
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
            System.out.println("LoginRequest created: " + this);
        }

        
        @Override
        public String toString() {
            return "LoginRequest{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
        }
    }
    public static class LoginResponse{
        public int userId;
        public String username;
        public String email;
        public Long score;
        public boolean isSuccess;
        public String message;

        public LoginResponse(int userId, String username, String email, Long score, boolean isSuccess,
                String message) {
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.score = score;
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public LoginResponse() {
            
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

        public String getMessage() {
            return message;
        }
    }

    public static class RegisterRequest {
        public String username;
        public String password;
        public String email;

        public RegisterRequest() {
            // Default constructor for serialization
        }

        public RegisterRequest(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
    }

    public static class RegisterResponse {
        public boolean isSuccess;
        public String message;

        public RegisterResponse() {
            // Default constructor for serialization
        }

        public RegisterResponse(boolean isSuccess, String message) {
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


    public static class MsgPacket {
        public String msg;
        public MsgPacket(){

        }
        public MsgPacket(String msg) {
            this.msg = msg;
        }
        
    }
}
