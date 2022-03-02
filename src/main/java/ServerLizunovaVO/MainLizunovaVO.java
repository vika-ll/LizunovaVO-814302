package ServerLizunovaVO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class MainLizunovaVO {
    public static void main(String[] arg) {

        ServerSocket serverSocket;
        Socket clientSocket;
        ObjectInputStream sois = null;
        ObjectOutputStream soos = null;

        try {
            serverSocket = new ServerSocket(2525);
            System.out.println("Server port - " + serverSocket.getLocalPort());
            System.out.println("Server has started:");
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("_______connection______");
                System.out.println("Client:" + clientSocket.getLocalSocketAddress());
                ServThreadLizunovaVO thread = new ServThreadLizunovaVO(clientSocket);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}