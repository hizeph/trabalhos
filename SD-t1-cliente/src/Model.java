/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hizeph
 */


import java.io.IOException;
import java.util.Scanner;

public class Model {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String keyboard = "";
        Client client = new Client();
        client.connect("localhost");
        client.request("angra.mp3");
        //System.exit(0);
        
        while(true){
            keyboard = scan.next();
            switch(keyboard){
                case "request":
                    // request music from server
                    keyboard = scan.next();
                    client.request(keyboard);
                    break;
               case "close":
                    // close socket and quit client
                    client.disconnect();
                    System.exit(0);
                    break;
            }
        }
    }
    
}
