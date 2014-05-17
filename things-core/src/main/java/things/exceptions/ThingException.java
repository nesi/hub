package things.exceptions;


import things.thing.Thing;

/**
 * Project: researchHub
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/01/14
 * Time: 4:19 PM
 */
public class ThingException extends Exception {

    private final Thing p;

    public ThingException() {
        super();
        this.p = null;
    }

    public ThingException(String msg) {
        super(msg);
        this.p = null;
    }

    public ThingException(Thing p, String msg) {
        super(msg);
        this.p = p;
    }

    public ThingException(String s, Exception e) {
        super(s, e);
        this.p = null;
    }

    public ThingException(Thing p, String msg, Exception e) {
        super(msg, e);
        this.p = p;
    }


    public Thing getThing() {
        return p;
    }
}
