package fpsgame.network.packets;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import fpsgame.network.packets.GeneralPackets.LoginRequest;
import fpsgame.network.packets.GeneralPackets.LoginResponse;
import fpsgame.network.packets.GeneralPackets.MsgPacket;
import fpsgame.network.packets.GeneralPackets.RegisterRequest;
import fpsgame.network.packets.GeneralPackets.RegisterResponse;

public class PacketsRegister {
    static public void register(EndPoint endPoint){
        Kryo kryo = endPoint.getKryo();
        kryo.register(MsgPacket.class);
        kryo.register(LoginRequest.class);
        kryo.register(LoginResponse.class);
        kryo.register(RegisterRequest.class);
        kryo.register(RegisterResponse.class);
    }
}
