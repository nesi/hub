package rooms.readers;

import rooms.actions.LightUtil;
import rooms.model.lights.limitless.whiteV2.LightWhiteV2;
import rooms.types.Light;
import rooms.types.LightState;
import rx.Observable;
import things.thing.AbstractThingReader;
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
public class LightStateReader extends AbstractThingReader implements ThingReader {

    private LightUtil lightUtil;
    private ThingControl tc;

    public LightStateReader() {
    }

    private LightState createState(String lightName) {
        LightWhiteV2 l = lightUtil.getLights().get(lightName);
        LightState ls = new LightState();
        ls.setBrightness(l.getBrightness());
        ls.setOn(l.isOn());
        ls.setWarmth(l.getWarmth());
        return ls;
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        tc.observeThingsForType(Light.class, false).map(l -> getState(l)).toBlockingObservable().forEach(System.out::println);
        return tc.observeThingsForType(Light.class, false).map(l -> getState(l));
    }

    private Thing<LightState> getState(Thing<Light> light) {

        LightState ls = createState(light.getKey());

        Thing<LightState> lightState = new Thing();
        lightState.setId("light:" + light.getId());
        lightState.setThingType(typeRegistry.getType(LightState.class));
        lightState.setValueIsLink(false);
        lightState.setValue(ls);
        lightState.setKey(light.getKey());
        return lightState;
    }

    @Override
    public <V> V readValue(Thing<V> light) {

        return (V) createState(light.getKey());

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
