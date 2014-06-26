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

    public String message;
    public final String[] stackTrace;
    public final String url;

    public ErrorInfo(final String url, final Throwable ex) {
        this.url = url;
        message = ex.getLocalizedMessage();
        stackTrace = Throwables.getStackTraceAsString(ex).split("\n");
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
