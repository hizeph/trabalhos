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


public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        byte[] b = new byte[10];
        String s = "";
        while(true){
            System.in.read(b);
            s = b.toString();
            System.out.println("str: " + s);
        }
        
    }
    
}
