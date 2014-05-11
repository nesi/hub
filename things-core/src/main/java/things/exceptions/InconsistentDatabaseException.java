package things.exceptions;

import com.google.common.collect.Lists;
import things.thing.Thing;

import java.util.List;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 4:40 PM
 */
public class InconsistentDatabaseException extends RuntimeException {
    
    private final List<Thing> things;
    
    public InconsistentDatabaseException(String msg) {
        super(msg);
        this.things = Lists.newArrayList();
    }

    public InconsistentDatabaseException(String msg, List<Thing> things) {
        super(msg);
        this.things = things;
    }

    public List<Thing> getThings() {
        return things;
    }
}
