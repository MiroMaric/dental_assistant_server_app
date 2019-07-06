package start;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import thread.ClientThread;

public class Start {

    private final boolean active = true;

    public void startServer() throws IOException, ClassNotFoundException {
        ServerSocket ss = new ServerSocket(9000);
        System.out.println("Server is up and runnig");
        while (active) {
            Socket socket = ss.accept();
            System.out.println("Accepted");
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Start().startServer();
    }

}
