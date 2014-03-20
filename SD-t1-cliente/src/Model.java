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
        String s[] = new String[2];
        Client client = new Client();
        //System.out.println("Enter server IP:");
        //keyboard = scan.next();
        //client.connect(keyboard);
        client.connect("localhost");
        //client.request("angra.mp3");
        
        while(true){
            keyboard = scan.nextLine();
            s = keyboard.split(" ");
            switch(s[0]){
                case "request":
                    // request music from server
                    client.request(s[1]+".mp3");
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
