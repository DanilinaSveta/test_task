package org.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.IOException;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start () throws InterruptedException, IOException {

        EventLoopGroup connectionClientGroup = new NioEventLoopGroup();
        EventLoopGroup otherEventsGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(connectionClientGroup,otherEventsGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new LineBasedFrameDecoder(1024));
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = server.bind(port).sync();
            System.out.println("Server started on port " + port);

//            connectionClientGroup.shutdownGracefully();
//            otherEventsGroup.shutdownGracefully();

            channelFuture.channel().closeFuture().sync();
        } finally {
            connectionClientGroup.shutdownGracefully();
            otherEventsGroup.shutdownGracefully();
        }
    }
}