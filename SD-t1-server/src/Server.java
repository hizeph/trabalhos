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
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.io.FileInputStream;


public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedInputStream buffIn;
    private ObjectOutputStream objOut;
    private BufferedOutputStream buffOut;
    private final int MAX_SIZE_IN = 512;
    private final int MAX_SIZE_OUT = 8338608;
    private byte[] input, output;
    private FileInputStream music;

    public Server() throws IOException {
        serverSocket = new ServerSocket(2020);
    }
    
    public void run() throws IOException{
        input = new byte[MAX_SIZE_IN];
        output = new byte[MAX_SIZE_OUT];
        String request, answer;
        byte[] size = new byte[8];
        int nBytes;
        //while(true){
            
            socket = serverSocket.accept();
            buffIn = new BufferedInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
            buffOut = new BufferedOutputStream(socket.getOutputStream());
            System.out.println("aceitou conexão!");
            
            // receive request
            nBytes = buffIn.read(input, 0, MAX_SIZE_IN);
            request = new String(input, 0, nBytes);
            // this.search(request);
            System.out.println("in: " + request);
            
            // respond to request
            
            music = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "dmc.mp3");
            nBytes = music.read(output, 0, MAX_SIZE_OUT);
            
            
            //answer
            // send size in bytes of the music to be read
            size = String.valueOf(nBytes).getBytes();
            buffOut.write(size, 0, size.length);
            buffOut.flush();
            
            // send music
            objOut.write(output, 0, nBytes);
            objOut.flush();
            
            System.out.println("finished");
            
            music.close();
        //}
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
