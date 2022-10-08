package client;

import common.Settings;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import static java.lang.System.out;

public class ClientThread implements Runnable {
    private final String fileLogName = "client_log.log";
    private final SocketChannel socketChannel;
    private final ByteBuffer byteBuffer;
    private String userName = "";

    public ClientThread(SocketChannel socketChannel, ByteBuffer byteBuffer) {
        this.socketChannel = socketChannel;
        this.byteBuffer = byteBuffer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int bytesCount = socketChannel.read(byteBuffer);
                if (bytesCount == -1) break;
                String msg = new String(byteBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim();
                Settings.log(fileLogName, userName, msg);
                out.println(msg);
                byteBuffer.clear();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            socketChannel.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}