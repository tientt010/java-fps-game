package fpsgame.network.protocol;

public class MsgPacket extends BaseMessage {
    public String msg;

    public MsgPacket() {
        this.type = "MSG_PACKET";
    }

    public MsgPacket(String msg) {
        this();
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MsgPacket{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
