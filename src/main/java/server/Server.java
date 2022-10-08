package server;

import common.Settings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Server {
    private final String fileLogName = "server_log.log";
    private static final int PORT = Integer.parseInt(Settings.getResource("port"));
    private static List<ServerThread> users = new ArrayList<>();

    public void start() throws IOException {
        final ServerSocketChannel serverChanel = ServerSocketChannel.open();
        serverChanel.bind(new InetSocketAddress("localhost", PORT));
        out.println("Сервер запущен");
        Settings.log(fileLogName, "SERVER", "Сервер запущен");

        while (true) {
            SocketChannel socketChannel = serverChanel.accept();
            ServerThread serverThread = new ServerThread(socketChannel, users);
            users.add(serverThread);
            new Thread(serverThread).start();
        }
    }

    public void sendMsgAll(String msg) {
        for (ServerThread user : users) {
            user.sendMsg(msg);
        }
    }

    public void removeServerThread(ServerThread serverThread) {
        users.remove(serverThread);
    }
}