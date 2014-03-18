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
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection {
    
    private Socket socket;
    private InetAddress ip;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    
    public Connection(){}
    
   public int connect(String hostname) throws IOException{
        try {
            ip = InetAddress.getByName(hostname);
        } catch (UnknownHostException ex) {
            return 0;
        }
        socket = new Socket(ip,2020);
        return 1;
    }
    
    public void disconnect() throws IOException{
        socket.close();
    }
    
    public int requestSearch(String name){
        return 1;
    }
    
    public int requestDeliver(String name){
        return 1;
    }
}
