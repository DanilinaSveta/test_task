package org.example.client;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port =  8080;
        new Client(host, port).start();
    }
}
