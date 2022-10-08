package client;

import common.Settings;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private final String fileLogName = "client_log.log";
    private static final String HOST = Settings.getResource("host");
    private static final int PORT = Integer.parseInt(Settings.getResource("port"));

    public void start() throws IOException {
        final InetSocketAddress socketAddress = new InetSocketAddress(HOST, PORT);
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 12);
            String msg;
            System.out.println("Добро пожаловать! Введите своё имя");
            String userName = scanner.nextLine();
            socketChannel.write(ByteBuffer.wrap(userName.getBytes(StandardCharsets.UTF_8)));
            ClientThread clientThread = new ClientThread(socketChannel, inputBuffer);
            new Thread(clientThread).start();

            while (true) {
                msg = scanner.nextLine();
                if (msg.equals("exit")) {
                    System.out.println("Вы покинули чат");
                    Settings.log(fileLogName, "SERVER", "Пользователь " + userName + " покинул чат");
                    break;
                }
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            }
        } finally {
            socketChannel.close();
        }
    }
}