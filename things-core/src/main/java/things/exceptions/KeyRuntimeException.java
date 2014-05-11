package things.exceptions;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 2/04/14 Time: 12:55 PM
 */
public class KeyRuntimeException extends RuntimeException {

    private final String key;

    public KeyRuntimeException(String s) {
        super(s);
        this.key = null;
    }

    public KeyRuntimeException(String msg, String key) {
        super(msg);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
