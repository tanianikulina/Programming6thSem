package task_1;

import java.io.*;
import java.net.*;

public class NewClient extends Thread {
    private BufferedWriter bufferedWriter;
    private BufferedReader socketReader;

    public NewClient(Socket socket) throws IOException {
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public BufferedReader getSocketReader() {
        return socketReader;
    }
}