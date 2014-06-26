package things.thing;

import things.exceptions.NoSuchThingException;
import things.exceptions.ThingRuntimeException;

import java.util.List;

/**
 * Wrapper class to support generic return type for rest.
 */
public class ThingList {

    private List<Thing> things;

    public ThingList() {

    }

    public ThingList(List<Thing> things) {
        this.things = things;
    }

    public List<Thing> getThings() {
        return things;
    }

    public void setThings(List<Thing> things) {
        this.things = things;
    }

    public Thing single() {
        if ( things.size() == 0 ) {
            throw new NoSuchThingException("No element in this list");
        } else if ( things.size() > 1 ) {
            throw new ThingRuntimeException("More than one element in this list");
        }

        return things.get(0);
    }


}
