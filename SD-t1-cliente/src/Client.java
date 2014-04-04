/**
 *
 * @author Cezar Bernardi
 */
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private Message message;
    private Socket socket;
    private InetAddress ip;
    private ObjectInputStream objIn;
    private BufferedOutputStream buffOut;
    private BufferedInputStream buffIn;
    private final int MAX_SIZE_OUT = 512;
    private final int MAX_SIZE_IN = 8338608;
    private byte[] input, output;
    private FileOutputStream music;
    private int nBytes;

    public Client() {
    }

    public boolean connect(String hostname) {
        try {
            try {
                ip = InetAddress.getByName(hostname);
            } catch (UnknownHostException ex) {
                return false;
            }
            socket = new Socket(ip, 2020);
            System.out.println("> Connection Stablished");
            
            objIn = new ObjectInputStream(socket.getInputStream());
            buffOut = new BufferedOutputStream(socket.getOutputStream());
            buffIn = new BufferedInputStream(socket.getInputStream());
            
            input = new byte[MAX_SIZE_IN];
            output = new byte[MAX_SIZE_OUT];
            
            return true;
        } catch (IOException ex) {
            System.out.println("> Connection Failed");
            return false;
        }
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void request(String name) throws IOException, ClassNotFoundException {
        // send request
        output = name.getBytes();
        buffOut.write(output, 0, output.length);
        buffOut.flush();

        // wait for answer
        this.receive();
    }
    
    public void receive() throws ClassNotFoundException{
        message = new Message();
        try {
            message = (Message) objIn.readObject();
            if (message.isValid()){
                message.saveMusic();
            } else {
                System.out.println("> Request not found on server");
            }
        } catch (IOException ex) {
            System.out.println("> Receive Failed");
        }
    }
}
