/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hizeph
 */
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.FileOutputStream;

public class Client {

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

    public int connect(String hostname) throws IOException {
        try {
            ip = InetAddress.getByName(hostname);
        } catch (UnknownHostException ex) {
            return 0;
        }
        socket = new Socket(ip, 2020);

        objIn = new ObjectInputStream(socket.getInputStream());
        buffOut = new BufferedOutputStream(socket.getOutputStream());
        buffIn = new BufferedInputStream(socket.getInputStream());

        input = new byte[MAX_SIZE_IN];
        output = new byte[MAX_SIZE_OUT];

        return 1;
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void request(String name) throws IOException {
        // send request
        output = name.getBytes();
        buffOut.write(output, 0, output.length);
        buffOut.flush();

        // wait for answer
        nBytes = buffIn.read(input, 0, 128);
        String s = new String(input, 0, nBytes);
        nBytes = Integer.parseInt(s);
        if (nBytes > 0){
            this.receive(name);
        } else {
            System.out.println("> File not found on server");
        } 
    }
    
    public void receive(String name) throws IOException{
        
        objIn.readFully(input, 0, nBytes);

        music = new FileOutputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "received" + System.getProperty("file.separator") + name);
        music.write(input, 0, nBytes);
        music.close();
        System.out.println("> Received");
    }
}
