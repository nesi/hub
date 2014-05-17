package things.exceptions;


import things.thing.Thing;

/**
 * Project: researchHub
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/01/14
 * Time: 4:19 PM
 */
public class ThingRuntimeException extends RuntimeException {

    private final Thing p;

    public ThingRuntimeException() {
        super();
        this.p = null;
    }

    public ThingRuntimeException(String msg) {
        super(msg);
        this.p = null;
    }

    public ThingRuntimeException(Thing p, String msg) {
        super(msg);
        this.p = p;
    }

    public ThingRuntimeException(Thing p, String msg, Exception e) {
        super(msg, e);
        this.p = p;
    }

    public ThingRuntimeException(String s, Exception e) {
        super(s, e);
        this.p = null;
    }

    public Thing getThing() {
        return p;
    }
}
