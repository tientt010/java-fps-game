package fpsgame.network.codec;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

import fpsgame.network.protocol.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<BaseMessage> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, ByteBuf out) throws Exception {
        try {
            String json = objectMapper.writeValueAsString(msg);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
            
            System.out.println("Encoded message: " + json);
            
        } catch (Exception e) {
            System.err.println("Failed to encode message: " + msg + ", Error: " + e.getMessage());
            throw new RuntimeException("Encoding failed", e);
        }
    }
}
