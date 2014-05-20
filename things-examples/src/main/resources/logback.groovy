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
