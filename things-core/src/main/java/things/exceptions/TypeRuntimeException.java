package things.exceptions;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 4:10 PM
 */
public class TypeRuntimeException extends RuntimeException {

    private final String type;

    public TypeRuntimeException(String msg, String type) {
        super(msg);
        this.type = type;
    }

    public TypeRuntimeException(String msg, String type, Exception e) {
        super(msg, e);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
