package server;

import common.Settings;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.System.out;

public class ServerThread implements Runnable {
    private final String fileLogName = "server_log.log";
    private Server server = new Server();
    private final SocketChannel socket;
    private List<ServerThread> serverThreadsList;
    private final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 12);
    private String msg = "";
    private String userName = "";

    public ServerThread(SocketChannel socket, List<ServerThread> serverThreadsList) {
        this.socket = socket;
        this.serverThreadsList = serverThreadsList;
    }

    @Override
    public void run() {
        try {
            int bytesCount = socket.read(inputBuffer);
            userName = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
            inputBuffer.clear();
            Settings.log(fileLogName, "SERVER", "Пользователь " + userName + " присоединился к чату");
            server.sendMsgAll("Пользователь " + userName + " присоединился к чату");

            while (socket.isConnected()) {
                bytesCount = socket.read(inputBuffer);
                if (bytesCount == -1) break;
                msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                if (msg.equals("exit")) {
                    System.out.println("Пользователь " + userName + " покинул чат"); // TODO убрать
                    Settings.log(fileLogName, "SERVER", "Пользователь " + userName + " покинул чат");
                    server.removeServerThread(this);
                    inputBuffer.clear();
                    break;
                }
                inputBuffer.clear();
                Settings.log(fileLogName, userName, msg);
                server.sendMsgAll(this.userName + ": " + msg);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            socket.write(ByteBuffer.wrap((msg).getBytes(StandardCharsets.UTF_8)));
            out.println(msg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}