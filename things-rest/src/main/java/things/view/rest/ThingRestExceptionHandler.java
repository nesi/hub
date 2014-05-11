package things.view.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import things.exceptions.ErrorInfo;
import things.exceptions.NoSuchThingException;

import javax.servlet.http.HttpServletRequest;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 25/04/14
 * Time: 12:10 PM
 */
@ControllerAdvice
public class ThingRestExceptionHandler {

    private static final Logger myLogger = LoggerFactory.getLogger(ThingRestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
	public ErrorInfo exception(final HttpServletRequest req,
                            final Exception ex) {

        myLogger.debug("Exception: " + ex.getLocalizedMessage());

        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

    @ExceptionHandler(NoSuchThingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
	public ErrorInfo noSuchThingException(final HttpServletRequest req,
                            final NoSuchThingException ex) {

        myLogger.debug("NoSuchEntityException: " + ex.getLocalizedMessage());

        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
}
