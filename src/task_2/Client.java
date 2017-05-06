package task_2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 5555);

        InputStreamReaderOutputStreamWriter fromClientToServer = new InputStreamReaderOutputStreamWriter(System.in, socket.getOutputStream());
        InputStreamReaderOutputStreamWriter fromServerToClient = new InputStreamReaderOutputStreamWriter(socket.getInputStream(), System.out);

        Thread toServer = new Thread(() -> {
            try {
                fromClientToServer.readWrite();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        toServer.start();

        fromServerToClient.readWrite();
    }
}