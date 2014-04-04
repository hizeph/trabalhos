
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Cezar Bernardi
 */
public class Message implements Serializable {

    private int size;
    private byte[] musicBytes;
    private final int MAX_SIZE = 8338608;
    private String name;
    private FileOutputStream out;
    private boolean valid = false;

    public Message() {
        valid = false;
    }

    public Message(byte[] musicBytes, int size, String name) {
        this.name = name;
        this.size = size;
        this.musicBytes = new byte[size];
        this.musicBytes = musicBytes;
        valid = true;
    }

    public boolean isValid() {
        return valid;
    }

    public void saveMusic() {
        try {
            String path = System.getProperty("user.dir") + System.getProperty("file.separator") + name;
            out = new FileOutputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "received" + System.getProperty("file.separator") + name);
            out.write(musicBytes, 0, size);
            out.close();
            System.out.println("> Received " + size + " bytes. Saved on: "+path);
        } catch (IOException ex) {
            System.out.println("> Message.getMusic Failed");
        }
    }
}
