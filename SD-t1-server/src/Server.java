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
import java.io.BufferedInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.FileInputStream;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedInputStream buffIn;
    private ObjectOutputStream objOut;
    private final int MAX_SIZE_IN = 512;
    private final int MAX_SIZE_OUT = 4096;
    private byte[] input, output;
    private FileInputStream music;

    public Server() throws IOException {
        serverSocket = new ServerSocket(2020);
    }
    
    public void run() throws IOException{
        input = new byte[MAX_SIZE_IN];
        output = new byte[MAX_SIZE_OUT];
        String request;
        int nBytes;
        while(true){
            
            socket = serverSocket.accept();
            buffIn = new BufferedInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("aceitou conexão!");
            
            // receive request
            nBytes = buffIn.read(input, 0, MAX_SIZE_IN);
            request = new String(input, 0, nBytes);
            System.out.println("in: " + request);
            
            // respond to request
            //output = search(request);
            
            music = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "dmc.mp3");
            music.read(output, 0, MAX_SIZE_OUT);
            request = new String(output);
            objOut.writeBytes(request);
            objOut.flush();
            music.close();
            System.out.println("finished");
        }
    }

    public byte[] search(String music) {
        // look in database
        byte[] b;
        b = deliver(music);
        return b;
    }

    public byte[] deliver(String path) {
        // serialize music
        byte[] b=new byte[MAX_SIZE_OUT];
        return b;
    }
    
}
