package main.java.com.roman_petrov;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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

                String[] paremeters = requestLine.substring(requestLine.indexOf("?") + 1).split("&");
                Map<String, String> map = new HashMap<>();
                for(String parameter: paremeters){
                    System.out.println(parameter);
                    String key = parameter.substring(0, parameter.indexOf("="));
                    String value = parameter.substring(parameter.indexOf("=") + 1);
                    map.put(key,value);
                }
                String html = "<html><body><h1>Hello world " + map.get("name")+ "</h1></body></html>";
                writeResponse(html);
                this.close();
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResponse(String html) throws IOException{
        String header = String.format("HTTP/1.1 200 OK\r\n" +
                                      "Server: Apache\r\n" +
                                      "Content-Length: %d\r\n" +
                                      "Content-Type: text/html\r\n" +
                                      "Connection: close\r\n\r\n",
                                       html.length());
        String response = header + html;
        System.out.println("---------------------------------------");
        System.out.println(response);
        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    private void close() throws IOException{
        socket.close();
        inputStream.close();
        outputStream.close();
    }
}
