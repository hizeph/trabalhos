import java.io.Serializable;

/**
 *
 * @author Cezar Bernardi
 */
public class Message implements Serializable {

    private int size;
    private byte[] musicBytes;
    private String name;
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
    
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public byte[] getMusicBytes() {
        return musicBytes;
    }

}
