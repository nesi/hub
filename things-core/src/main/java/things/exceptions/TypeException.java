package things.exceptions;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 28/03/14
 * Time: 1:50 PM
 */
public class TypeException extends Exception {

    private final String type;

    public TypeException(String msg, String type) {
        super(msg);
        this.type = type;
    }

    public TypeException(String msg, String type, Exception e) {
        super(msg, e);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
