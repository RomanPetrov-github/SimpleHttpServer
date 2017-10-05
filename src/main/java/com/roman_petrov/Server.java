package main.java.com.roman_petrov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(5555);

        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("Client connect");
            new Thread(new SocketProcessor(socket)).start();
        }
    }
}
