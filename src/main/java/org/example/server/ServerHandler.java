package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.Topic.Topic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, Topic> topics = new HashMap<>();

    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception{
        String message = (String) msg;
        System.out.println("Server received: " + message);
        ctx.writeAndFlush("Echo: " + message + "\n");

        message = (String) msg;
        String[] commandParts = message.trim().split(" ");

        if (commandParts.length == 0) return;

        String command = commandParts[0];
        switch (command) {
            case "exit":
                ctx.close();
                break;
            case "load":
                if (commandParts.length == 2) {
                    handleLoad(ctx, commandParts[1]);
                } else {
                    sendResponse(ctx, "Usage: load <filename>");
                }
                break;
            case "save":
                if (commandParts.length == 2) {
                    handleSave(ctx, commandParts[1]);
                } else {
                    sendResponse(ctx, "Usage: save <filename>");
                }
                break;
            default:
                System.out.println("ERROR");
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
    void handleLoad(ChannelHandlerContext ctx, String filename) {
        File file = new File(filename);
        try {
            Topic[] loadedTopics = objectMapper.readValue(file, Topic[].class);
            for (Topic topic : loadedTopics) {
                topics.put(topic.getName_topic(), topic);
            }
            sendResponse(ctx, "Data loaded successfully.");
        } catch (IOException e) {
            sendResponse(ctx, "Error loading data: " + e.getMessage());
        }
    }

    void handleSave(ChannelHandlerContext ctx, String filename) {
        File file = new File(filename);
        try {
            objectMapper.writeValue(file, topics.values());
            sendResponse(ctx, "Data saved successfully to " + filename);
        } catch (IOException e) {
            sendResponse(ctx, "Error saving data: " + e.getMessage());
        }
    }
    private void sendResponse(ChannelHandlerContext ctx, String response) {
        ctx.writeAndFlush(response + "\n");
    }
    public void setTopics(Map<String, Topic> topics) {
        this.topics = topics;
    }

    public Map<String, Topic> getTopics() {
        return topics;
    }

}
