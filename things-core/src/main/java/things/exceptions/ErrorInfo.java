package things.exceptions;

import com.google.common.base.Throwables;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 25/04/14
 * Time: 12:12 PM
 */
public class ErrorInfo {

    public final String message;
    public final String[] stackTrace;
    public final String url;

    public ErrorInfo(final String url, final Throwable ex) {
        this.url = url;
        message = ex.getLocalizedMessage();
        stackTrace = Throwables.getStackTraceAsString(ex).split("\n");
    }
}
