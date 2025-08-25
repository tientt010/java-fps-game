package fpsgame.network.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = LoginRequest.class, name = "LOGIN_REQUEST"),
    @JsonSubTypes.Type(value = LoginResponse.class, name = "LOGIN_RESPONSE"),
    @JsonSubTypes.Type(value = RegisterRequest.class, name = "REGISTER_REQUEST"),
    @JsonSubTypes.Type(value = RegisterResponse.class, name = "REGISTER_RESPONSE"),
    @JsonSubTypes.Type(value = MsgPacket.class, name = "MSG_PACKET")
})
public abstract class BaseMessage {
    public String type;
}
