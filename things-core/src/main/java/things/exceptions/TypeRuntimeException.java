package things.exceptions;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 4:10 PM
 */
public class TypeRuntimeException extends RuntimeException {

    private final String type;
    private final Class typeClass;

    public TypeRuntimeException(String msg) {
        super(msg);
        this.typeClass = null;
        this.type = null;
    }



    public TypeRuntimeException(String msg, Class typeClass) {
        super(msg);
        this.typeClass = typeClass;
        this.type = null;
    }

    public TypeRuntimeException(String msg, Class typeClass, Exception e) {
        super(msg, e);
        this.typeClass = typeClass;
        this.type = null;
    }

    public TypeRuntimeException(String msg, String type) {
        super(msg);
        this.type = type;
        this.typeClass = null;
    }

    public TypeRuntimeException(String msg, String type, Exception e) {
        super(msg, e);
        this.type = type;
        this.typeClass = null;
    }

    public String getType() {
        return type;
    }
}
