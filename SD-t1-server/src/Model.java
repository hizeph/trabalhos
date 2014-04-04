/**
 *
 * @author Cezar Bernardi
 */

import java.io.IOException;
import java.net.ServerSocket;

public class Model {

    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }
    
}
