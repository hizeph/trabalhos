
/**
 *
 * @author Cezar Bernardi
 */
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private Message message;
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedInputStream buffIn;
    private ObjectOutputStream objOut;
    private BufferedOutputStream buffOut;
    private final int MAX_SIZE_IN = 512;
    private final int MAX_SIZE_OUT = 8338608;
    private byte[] input, output, outputSize;
    private FileInputStream music;
    private int nBytes;

    public Server() throws IOException {
        serverSocket = new ServerSocket(2020);
    }

    public void run() throws IOException {
        input = new byte[MAX_SIZE_IN];
        output = new byte[MAX_SIZE_OUT];
        String request;
        while (true) {
            System.out.println("Server IP: "+serverSocket.getInetAddress().getHostAddress());
            System.out.println("Server port: "+serverSocket.getLocalPort());
            System.out.println("> Waiting for new client connection");
            socket = serverSocket.accept();

            buffIn = new BufferedInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
            buffOut = new BufferedOutputStream(socket.getOutputStream());
            System.out.println("> Connection Stablished");
            while (socket.isConnected()) {
                // receive request
                System.out.println("> Waiting for request...");
                nBytes = buffIn.read(input, 0, MAX_SIZE_IN);
                if (nBytes < 0) {
                    System.out.println("> Connection closed by client");
                    break;
                }
                request = new String(input, 0, nBytes);
                System.out.println("request: " + request);

                this.search(request);

                this.deliver();
            }
        }
    }

    public void search(String request) throws IOException {
        try {
            output = new byte[MAX_SIZE_OUT];
            // look in database
            music = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + request);
            nBytes = music.read(output, 0, MAX_SIZE_OUT);
            message = new Message(output, nBytes, request);
            music.close();
        } catch (FileNotFoundException ex) {
            message = new Message();
            System.out.println("> Request not found");
        }
    }

    public void deliver() {
        try {
            // respond to request
            objOut.writeObject(message);
            System.out.println("> Request complete");
        } catch (IOException ex) {
            System.out.println("> Deliver failed");
            ex.printStackTrace();
        }
    }

}
