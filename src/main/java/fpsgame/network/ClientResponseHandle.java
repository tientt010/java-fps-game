package fpsgame.network;

import fpsgame.network.packets.GeneralPackets.LoginResponse;
import fpsgame.network.packets.GeneralPackets.RegisterResponse;

public interface ClientResponseHandle {
    void handleLoginResponse(LoginResponse response);
    void handleRegisterResponse(RegisterResponse response);
}
