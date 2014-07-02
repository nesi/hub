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

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender

import static ch.qos.logback.classic.Level.DEBUG

/*
 * see http://mrhaki.blogspot.in/2010/09/grassroots-groovy-configure-logback.html
 * and http://logback.qos.ch/manual/groovy.html
 * for logback.groovy examples
 */

appender("FILE", FileAppender)
        {
            def ts = timestamp("yyyy-MM-dd'_'HH-mm-ss")

            file = "things.log"
            append = true
            encoder(PatternLayoutEncoder)
                    {
                        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
                    }
        }


appender("CONSOLE", ConsoleAppender)
        {
            //append=true
            encoder(PatternLayoutEncoder)
                    {
                        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
                    }
        }

logger("testThings", DEBUG)
logger("things", DEBUG)
logger("hub", DEBUG)
logger("org.springframework", ERROR)
logger("org.springframework.boot.actuate.autoconfigure.ShellProperties", INFO)
root(ERROR, ["FILE", "CONSOLE"])
