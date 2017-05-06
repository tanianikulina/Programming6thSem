package task_2;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class NewClient extends Thread {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private ArrayList<BufferedWriter> bufferedWriters;
    private String login;

    public NewClient(Socket socket, ArrayList<BufferedWriter> bufferedWriters) throws IOException {
        this.socket = socket;
        this.bufferedWriters = bufferedWriters;

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        bufferedWriter = new BufferedWriter(outputStreamWriter);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("loginsPasswords.txt")));
            String line;
            String[] temp; //временное для логина/пароля
            Map loginsPasswords = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                temp = line.split(", ");
                loginsPasswords.put(temp[0], temp[1]);
            }


            InputStreamReaderOutputStreamWriter.justWrite(bufferedWriter, "Enter your login");
            while (!loginsPasswords.containsKey(login = bufferedReader.readLine()))
                InputStreamReaderOutputStreamWriter.justWrite(bufferedWriter, "No such login");

            InputStreamReaderOutputStreamWriter.justWrite(bufferedWriter, "Enter your password");
            while (!loginsPasswords.get(login).equals(bufferedReader.readLine()))
                InputStreamReaderOutputStreamWriter.justWrite(bufferedWriter, "Wrong password! Try again");

            InputStreamReaderOutputStreamWriter clientToOthers = new InputStreamReaderOutputStreamWriter(socket.getInputStream(), bufferedWriters);

            Thread fromServer = new Thread(() ->
            {
                try {
                    clientToOthers.readWrite(bufferedWriter, login + ": ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fromServer.start();

            //https://habrahabr.ru/post/269667/
            InputStreamReaderOutputStreamWriter.justWrite(bufferedWriter, new String(Files.readAllBytes(Paths.get("history.txt")), StandardCharsets.UTF_8));
            bufferedWriters.add(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public String getLogin() {
        return login;
    }
}