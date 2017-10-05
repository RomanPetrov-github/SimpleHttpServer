package main.java.com.roman_petrov;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketProcessor implements Runnable{

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketProcessor(Socket socket) throws IOException{
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    @Override
    public void run() {

        try {
            byte[] bufer = new byte[inputStream.available()];
            inputStream.read(bufer);
            String httpRequest = new String(bufer);

            System.out.println(httpRequest);
            System.out.println("=============================================");

            if (httpRequest.length() != 0) {
                String requestLine = httpRequest.substring(httpRequest.indexOf("GET ") + 4, httpRequest.indexOf(" HTTP"));
                System.out.println("=============================================");
                System.out.println(requestLine);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException{
        socket.close();
        inputStream.close();
        outputStream.close();
    }
}
