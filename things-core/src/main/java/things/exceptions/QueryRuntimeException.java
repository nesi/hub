package things.exceptions;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 9/04/14
 * Time: 1:52 PM
 */
public class QueryRuntimeException extends RuntimeException {

    public QueryRuntimeException(String msg) {
        super(msg);
    }

    public QueryRuntimeException(String msg, Exception e) {
        super(msg, e);
    }
}
