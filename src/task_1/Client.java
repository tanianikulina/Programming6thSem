package chat;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 5555);
        BufferedReader bufferedReaderFromClient = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        Thread writeToSocket = new Thread(() -> {
            while (true) {
                try {
                    socketWriter.write(bufferedReaderFromClient.readLine());
                    socketWriter.newLine();
                    socketWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        writeToSocket.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            System.out.println(bufferedReader.readLine());
        }
    }
}