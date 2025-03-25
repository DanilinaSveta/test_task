package org.example.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        int port = 8080;
        new Server(port).start();
    }
}