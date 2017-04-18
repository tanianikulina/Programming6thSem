package chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);
        BufferedReader bufferedReaderFromServer = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<BufferedWriter> bufferedWriters = new ArrayList<>();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                OneMoreClient client = new OneMoreClient(socket);
                client.start();
                bufferedWriters.add(client.getBufferedWriter());

                Thread writeToSocket = new Thread(() -> {
                    String s;
                    try {
                        while ((s = bufferedReaderFromServer.readLine()) != null) {
                            for (BufferedWriter bufferedWriter : bufferedWriters) {
                                bufferedWriter.write(s);
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                writeToSocket.start();

                Thread readFromSocket = new Thread(() -> {
                    String s;
                    BufferedReader socketReader = client.getSocketReader();
                    try {
                        while ((s = socketReader.readLine()) != null) {
                            System.out.println(s);
                            BufferedWriter thisBufferedWriter = client.getBufferedWriter();
                            for (BufferedWriter bufferedWriter : bufferedWriters) {
                                if (bufferedWriter != thisBufferedWriter) {
                                    bufferedWriter.write(s);
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                readFromSocket.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}