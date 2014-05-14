package things.exceptions;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/05/14
 * Time: 9:54 PM
 */
public class NoSuchTypeException extends RuntimeException {
    public NoSuchTypeException(String s) {
        super(s);
    }
}
