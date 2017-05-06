package task_2;

import java.io.*;
import java.util.*;

//lecture of 3.03

public class InputStreamReaderOutputStreamWriter {
    private BufferedReader bufferedReader;
    private List<BufferedWriter> bufferedWriters;

    public InputStreamReaderOutputStreamWriter(InputStream inputStream, OutputStream outputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedWriters = new ArrayList<>();
        bufferedWriters.add(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }

    public InputStreamReaderOutputStreamWriter(InputStream inputStream, List<BufferedWriter> outputStreams) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedWriters = outputStreams;
    }

    public void readWrite() throws IOException {
        this.readWriteInternal(null, "");
    }

    public void readWrite(BufferedWriter notWriteTo, String login) throws IOException {
        readWriteInternal(notWriteTo, login);
    }

    public void readWriteInternal(BufferedWriter notWriteTo, String login) throws IOException {
        while (true) {
            for (BufferedWriter bufferedWriter : bufferedWriters) {
                if (bufferedWriter == notWriteTo) continue;
                justWrite(bufferedWriter, login + bufferedReader.readLine());
            }
        }
    }

    public static void justWrite (BufferedWriter bufferedWriter, String string) {
        try {
            bufferedWriter.write(string);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
