package fpsgame.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import fpsgame.network.packets.GeneralPackets.LoginRequest;
import fpsgame.network.packets.GeneralPackets.LoginResponse;
import fpsgame.network.packets.GeneralPackets.MsgPacket;
import fpsgame.network.packets.GeneralPackets.RegisterRequest;
import fpsgame.network.packets.GeneralPackets.RegisterResponse;
import fpsgame.network.packets.PacketsRegister;

public class ClientNetwork {
    private Client client;
    private int timeout;
    private int tcpPort;
    private int udpPort;
    private String serverAddress;
    private boolean isConnected = false;
    private ClientResponseHandle responseHandle;

    public ClientNetwork(int timeout, int tcpPort, int udpPort, String serverAddress) {
        this.timeout = timeout;
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        this.serverAddress = serverAddress;
        client = new Client(2 * 1024 * 1024, 2 * 1024 * 1024);
        PacketsRegister.register(client);
    }

    public void connectServer() throws Exception {
        // Add listener to handle connection events
        isConnected = false;
        client.start();
        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                isConnected = true;
            }
            
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof MsgPacket response) {
                    System.out.println(response.msg);
                } else if(object instanceof LoginResponse loginResponse){
                    responseHandle.handleLoginResponse(loginResponse);
                }else if(object instanceof RegisterResponse registerResponse){
                    responseHandle.handleRegisterResponse(registerResponse);
                }
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Disconnected from server!");
                isConnected = false;
            }
        });

        new Thread() {
            public void run() {
                try {
                    client.connect(timeout, serverAddress, tcpPort, udpPort);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        }.start();

        while (!isConnected) {
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setResponseHandle(ClientResponseHandle responseHandle) {
        this.responseHandle = responseHandle;
    }

    public void sendRequest(Object object){
        if(object instanceof LoginRequest loginRequest){
            client.sendTCP(loginRequest);
        }
        if(object instanceof RegisterRequest registerRequest){
            client.sendTCP(registerRequest);
        }
    }
}
