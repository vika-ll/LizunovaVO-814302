package MainLizunovaVO;

import java.io.*;
import java.net.Socket;

public class ConnectLizunovaVO implements Serializable{
    static Socket clientSocket;
    private static BufferedReader stdin;
    public static ObjectOutputStream coos;
    public static ObjectInputStream cois;

    static void servConnect() throws IOException {
        clientSocket = new Socket("127.0.0.1", 2525);
        System.out.println("Server connected...");
        stdin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        coos = new ObjectOutputStream(clientSocket.getOutputStream());
        cois = new ObjectInputStream(clientSocket.getInputStream());
    }
    }