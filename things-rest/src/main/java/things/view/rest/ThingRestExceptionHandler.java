/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

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

    @ExceptionHandler(Error.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo error(final HttpServletRequest req,
                           final Error ex) {

        myLogger.debug("Exception: " + ex.getLocalizedMessage(), ex);

        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo exception(final HttpServletRequest req,
                               final Exception ex) {

        myLogger.debug("Exception: " + ex.getLocalizedMessage(), ex);

        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

    @ExceptionHandler(NoSuchThingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo noSuchThingException(final HttpServletRequest req,
                                          final NoSuchThingException ex) {

        myLogger.debug("NoSuchEntityException: " + ex.getLocalizedMessage(), ex);

        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
}
