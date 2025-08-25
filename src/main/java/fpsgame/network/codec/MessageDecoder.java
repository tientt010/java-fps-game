package fpsgame.network.codec;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import fpsgame.network.protocol.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecoder extends ByteToMessageDecoder {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        
        in.markReaderIndex();
        
        int messageLength = in.readInt();        
        // Kiểm tra
        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
            return;
        }
        
        try {
            byte[] messageBytes = new byte[messageLength];
            in.readBytes(messageBytes);
            
            // Bytes => Json
            String json = new String(messageBytes, StandardCharsets.UTF_8);
            
            // Json => Object
            BaseMessage message = objectMapper.readValue(json, BaseMessage.class);
            
            out.add(message);
            
        } catch (Exception e) {
            System.err.println("Failed to decode message: " + e.getMessage());
            ctx.close();
        }
    }
}
