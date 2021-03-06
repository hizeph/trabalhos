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
import java.io.FileNotFoundException;

public class Server {

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
            System.out.println("> Waiting for new client connection");
            socket = serverSocket.accept();
            
            buffIn = new BufferedInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
            buffOut = new BufferedOutputStream(socket.getOutputStream());
            System.out.println("> Connection Stablished");
            while(socket.isConnected()){
                // receive request
                System.out.println("> Waiting for request...");
                nBytes = buffIn.read(input, 0, MAX_SIZE_IN);
                if (nBytes < 0){
                    System.out.println("> Connection closed by client");
                    break;
                }
                request = new String(input, 0, nBytes);
                System.out.println("request: "+ request);

                this.search(request);

                this.deliver();
            }
        }
    }

    public void search(String request) throws IOException {
        try {
            // look in database
            music = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "music" + System.getProperty("file.separator") + request);
            nBytes = music.read(output, 0, MAX_SIZE_OUT);
            music.close();
        } catch (FileNotFoundException ex) {
            System.out.println("> Client request not found");
            nBytes = -1;
        }
    }

    public void deliver() throws IOException {
        // respond to request

        // send size in bytes of the music to be read
        outputSize = String.valueOf(nBytes).getBytes();
        buffOut.write(outputSize, 0, outputSize.length);
        buffOut.flush();

        if (nBytes > 0) {
            // send music
            objOut.write(output, 0, nBytes);
            objOut.flush();
        }
        System.out.println("> Request complete");
    }

}
