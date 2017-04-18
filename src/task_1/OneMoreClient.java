package chat;

import java.io.*;
import java.net.*;

public class OneMoreClient extends Thread {
    private BufferedWriter bufferedWriter;
    private BufferedReader socketReader;

    public OneMoreClient(Socket socket) throws IOException {
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