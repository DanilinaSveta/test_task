package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Client {
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start () throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new LineBasedFrameDecoder(1024));
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Scanner scanner = new Scanner(System.in);
            String message;
            String regex = Pattern.quote("=");
            while (true){
                message = scanner.nextLine();
                String[] mess_arr = message.split(regex);

                if ("exit".equalsIgnoreCase(message)){
                    break;
                }
                if ("login -u".equalsIgnoreCase(mess_arr[0])){
                    System.out.println("The client is connected to the server with the username: "  + mess_arr[1]);
                    ClientCommand clientCommand = new ClientCommand();
                    clientCommand.clientCommand();
                } else {
                    System.out.println("Error");
                }
                channelFuture.channel().writeAndFlush(message + "\n");
            }
            channelFuture.channel().close().sync();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
