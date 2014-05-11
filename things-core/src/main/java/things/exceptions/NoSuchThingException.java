package things.exceptions;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 21/03/14
 * Time: 4:46 PM
 */
public class NoSuchThingException extends RuntimeException {

    private final String key;
    private final String type;
    private final Object id;

    public NoSuchThingException(Object id) {
        super("Can't find Thing for id: "+id);
        this.key = null;
        this.type = null;
        this.id = id;
    }

    public NoSuchThingException(String msg, String type, String key, Object id) {
        super(msg);
        this.type = type;
        this.key = key;
        this.id = id;
    }

    public NoSuchThingException(String type, String key) {
        super("Can't find thing for type '"+type+"' and key '"+key+"'");
        this.type = type;
        this.key = key;
        this.id = null;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Object getId() {
        return id;
    }
}
