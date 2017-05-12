package task_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);

        ArrayList<BufferedWriter> bufferedWriters = new ArrayList<>();
        BufferedWriter serverWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        bufferedWriters.add(serverWriter);

        BufferedWriter historyWriter = new BufferedWriter(new FileWriter(new File("history.txt")));
        InputStreamReaderOutputStreamWriter fromServerToClients = new InputStreamReaderOutputStreamWriter(System.in, bufferedWriters);
        bufferedWriters.add(historyWriter);

        System.out.println(new String(Files.readAllBytes(Paths.get("history.txt")), StandardCharsets.UTF_8));

        Thread toClients = new Thread(() ->
        {
            try {
                fromServerToClients.readWrite(serverWriter, "server: ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        toClients.start();

        while (true) {
            Socket socket = serverSocket.accept();
            NewClient client = new NewClient(socket, bufferedWriters);
            client.start();
        }
    }
}