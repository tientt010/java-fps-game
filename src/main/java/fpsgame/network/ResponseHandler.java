package fpsgame.network;

import fpsgame.network.protocol.LoginResponse;
import fpsgame.network.protocol.RegisterResponse;

public interface ResponseHandler {
    

    void handleLoginResponse(LoginResponse response);
    
    void handleRegisterResponse(RegisterResponse response);
    

    default void onConnected() {
        System.out.println("Connected to server");
    }
    
    default void onDisconnected(String reason) {
        System.out.println("Disconnected from server: " + reason);
    }
    
    default void onError(Throwable error) {
        System.err.println("Connection error: " + error.getMessage());
        error.printStackTrace();
    }
}
