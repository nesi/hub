package rooms.readers;

import rooms.actions.LightUtil;
import rooms.types.Light;
import rx.Observable;
import things.thing.AbstractSimpleThingReader;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingReader;

import javax.inject.Inject;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 13/05/14
 * Time: 7:40 PM
 */
public class LightStateReader extends AbstractSimpleThingReader implements ThingReader {

    private LightUtil lightUtil;
    private ThingControl tc;

    public LightStateReader() {
    }


    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        return tc.observeThingsForType(Light.class, false).map(l -> lightUtil.createLightStateThing(l));
    }


    @Override
    public <V> V readValue(Thing<V> light) {

        return (V) lightUtil.createState(light.getKey());

    }

    @Inject
    public void setLightUtil(LightUtil lightUtil) {
        this.lightUtil = lightUtil;
    }

    @Inject
    public void setThingControl(ThingControl tc) {
        this.tc = tc;
    }
}
