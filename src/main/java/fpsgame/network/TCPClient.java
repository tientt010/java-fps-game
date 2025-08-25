package fpsgame.network;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import fpsgame.network.codec.MessageDecoder;
import fpsgame.network.codec.MessageEncoder;
import fpsgame.network.protocol.BaseMessage;
import fpsgame.network.protocol.LoginRequest;
import fpsgame.network.protocol.LoginResponse;
import fpsgame.network.protocol.MsgPacket;
import fpsgame.network.protocol.RegisterRequest;
import fpsgame.network.protocol.RegisterResponse;
import fpsgame.utils.Config;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class TCPClient {
    
    private Bootstrap bootstrap;
    private EventLoopGroup workerGroup;
    private Channel channel;
    private boolean connected = false;
    
    private String host;
    private int port;
    
    private Consumer<LoginResponse> loginResponseHandler;
    private Consumer<RegisterResponse> registerResponseHandler;
    private Consumer<MsgPacket> messageHandler;
    private Consumer<String> connectionHandler;
    private Consumer<Throwable> errorHandler;

    public TCPClient() {
        initializeNetty();
    }
    
    private void initializeNetty() {
        workerGroup = new NioEventLoopGroup();
        
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Config.clientTimeout)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    
                    // Framing handlers
                    pipeline.addLast("frameDecoder", 
                        new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
                    pipeline.addLast("frameEncoder", 
                        new LengthFieldPrepender(4));
                    
                    // Gọi codec
                    pipeline.addLast("messageDecoder", new MessageDecoder());
                    pipeline.addLast("messageEncoder", new MessageEncoder());
                    
                    pipeline.addLast("clientHandler", new ClientMessageHandler());
                }
            });
    }
    
    public CompletableFuture<Boolean> connect(String host, int port) {
        this.host = host;
        this.port = port;
        
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        
        ChannelFuture connectFuture = bootstrap.connect(host, port);
        
        connectFuture.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                this.channel = channelFuture.channel();
                this.connected = true;
                
                if (connectionHandler != null) {
                    connectionHandler.accept("Connected to " + host + ":" + port);
                }
                future.complete(true);

                channel.closeFuture().addListener(closeFuture -> {
                    this.connected = false;
                    if (connectionHandler != null) {
                        connectionHandler.accept("Disconnected from server");
                    }
                });
                
            } else {
                this.connected = false;
                if (errorHandler != null) {
                    errorHandler.accept(channelFuture.cause());
                }
                future.complete(false);
            }
        });
        
        return future;
    }
    
    public CompletableFuture<Void> disconnect() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        if (channel != null && channel.isActive()) {
            channel.close().addListener(closeFuture -> {
                this.connected = false;
                future.complete(null);
            });
        } else {
            this.connected = false;
            future.complete(null);
        }
        
        return future;
    }
    
    public void shutdown() {
        try {
            disconnect().get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("Error during disconnect: " + e.getMessage());
        }
        
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
    
    private CompletableFuture<Boolean> sendMessage(Object message) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        
        if (!connected || channel == null || !channel.isActive()) {
            future.complete(false);
            return future;
        }
        
        channel.writeAndFlush(message).addListener((ChannelFutureListener) writeFuture -> {
            if (writeFuture.isSuccess()) {
                future.complete(true);
            } else {
                if (errorHandler != null) {
                    errorHandler.accept(writeFuture.cause());
                }
                future.complete(false);
            }
        });
        
        return future;
    }

    public CompletableFuture<Boolean> sendLoginRequest(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.username = username;
        request.password = password;
        
        return sendMessage(request);
    }
    
    public CompletableFuture<Boolean> sendRegisterRequest(String username, String password, String email) {
        RegisterRequest request = new RegisterRequest();
        request.username = username;
        request.password = password;
        request.email = email;
        
        return sendMessage(request);
    }
    
    public CompletableFuture<Boolean> sendTextMessage(String message) {
        MsgPacket packet = new MsgPacket();
        packet.msg = message;
        
        return sendMessage(packet);
    }

    // Setters
    public void setLoginResponseHandler(Consumer<LoginResponse> handler) {
        this.loginResponseHandler = handler;
    }
    
    public void setRegisterResponseHandler(Consumer<RegisterResponse> handler) {
        this.registerResponseHandler = handler;
    }

    public void setMessageHandler(Consumer<MsgPacket> handler) {
        this.messageHandler = handler;
    }
    
    public void setConnectionHandler(Consumer<String> handler) {
        this.connectionHandler = handler;
    }

    public void setErrorHandler(Consumer<Throwable> handler) {
        this.errorHandler = handler;
    }

    // Getters

    public boolean isConnected() {
        return connected && channel != null && channel.isActive();
    }
    
    public String getHost() {
        return host;
    }
    
    public int getPort() {
        return port;
    }
    
    // Handler xử lý messages nhận được từ server
    private class ClientMessageHandler extends SimpleChannelInboundHandler<BaseMessage> {
        
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, BaseMessage message) {
            // Route message đến handler phù hợp
            if (message instanceof LoginResponse loginResponse) {
                handleLoginResponse(loginResponse);
            } else if (message instanceof RegisterResponse registerResponse) {
                handleRegisterResponse(registerResponse);
            } else if (message instanceof MsgPacket msgPacket) {
                handleMessage(msgPacket);
            }
        }
        
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            if (errorHandler != null) {
                errorHandler.accept(cause);
            }
        }
        
        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            connected = false;
        }
        
        private void handleLoginResponse(LoginResponse response) {
            if (loginResponseHandler != null) {
                loginResponseHandler.accept(response);
            }
        }
        
        private void handleRegisterResponse(RegisterResponse response) {
            if (registerResponseHandler != null) {
                registerResponseHandler.accept(response);
            }
        }
        
        private void handleMessage(MsgPacket packet) {
            if (messageHandler != null) {
                messageHandler.accept(packet);
            }
        }
    }
}
