/**
 *
 * @author Cezar Bernardi
 */


import java.io.IOException;
import java.util.Scanner;

public class Model {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        String keyboard = "";
        String s[] = new String[2];
        Client client = new Client();
        System.out.println("Enter server IP:");
        keyboard = scan.next();
        if (client.connect(keyboard)){
        
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
    
}
