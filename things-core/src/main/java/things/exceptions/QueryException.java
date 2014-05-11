package things.exceptions;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 9/04/14
 * Time: 1:52 PM
 */
public class QueryException extends Exception {

    public QueryException(String msg) {
        super(msg);
    }

    public QueryException(String msg, Exception e) {
        super(msg, e);
    }
}
