/**
 *
 * @author Cezar Bernardi
 */
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Server {

    private Message message;
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedInputStream buffIn;
    private ObjectOutputStream objOut;
    private final int MAX_SIZE_IN = 512;
    private final int MAX_SIZE_OUT = 8338608;
    private byte[] input, output, outputSize;
    private FileInputStream music;
    private int nBytes;
    private String databasePath;

    public Server() throws IOException {
        serverSocket = new ServerSocket(2020);
    }

    public void run() throws IOException {
        input = new byte[MAX_SIZE_IN];
        output = new byte[MAX_SIZE_OUT];
        String request;
        databasePath = System.getProperty("user.dir") + System.getProperty("file.separator");
        System.out.println("Server IP/mask:port : " + serverSocket.getLocalSocketAddress());
        System.out.println("Database Path: " + databasePath+"\n");
        while (true) {
            
            System.out.println("> Waiting for new client connection");
            socket = serverSocket.accept();

            buffIn = new BufferedInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("> Connection Stablished");
            while (true) {
                // receive request
                System.out.println("> Waiting for request...");
                try {
                    nBytes = buffIn.read(input, 0, MAX_SIZE_IN);
                    if (nBytes < 0) {
                        System.out.println("!> Connection closed by client");
                        break;
                    }
                    request = new String(input, 0, nBytes);
                    System.out.println(">Request received: " + request);

                    this.search(request);

                    this.deliver();
                } catch (java.net.SocketException ex) {
                    System.out.println("!> Connection reset");
                    break;
                }
            }
        }
    }

    public void search(String request) throws IOException {
        try {
            output = new byte[MAX_SIZE_OUT];
            // look in database
            music = new FileInputStream(databasePath + request);
            nBytes = music.read(output, 0, MAX_SIZE_OUT);
            message = new Message(output, nBytes, request);
            music.close();
        } catch (FileNotFoundException ex) {
            message = new Message();
            System.out.println("!> Request not found");
        }
    }

    public void deliver() {
        try {
            // respond to request
            objOut.writeObject(message);
            System.out.println("> Request complete");
        } catch (IOException ex) {
            System.out.println("!> Deliver failed");
        }
    }

}
