package rooms.readers;

import rooms.actions.LightUtil;
import rooms.model.lights.limitless.whiteV2.LightWhiteV2;
import rooms.types.Light;
import rooms.types.LightState;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingReader;
import things.thing.TypeRegistry;

import javax.inject.Inject;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 13/05/14
 * Time: 7:40 PM
 */
public class LightStateReader implements ThingReader {

    private ThingControl tc;
    private LightUtil lightUtil;

    @Inject
    public void setLightUtil(LightUtil lightUtil) {
        this.lightUtil = lightUtil;
    }

    @Inject
    public void setThingControl(ThingControl tc) {
        this.tc = tc;
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {
        tc.observeThingsForType(Light.class, false).map(l -> getState(l)).toBlockingObservable().forEach(System.out::println);
        return tc.observeThingsForType(Light.class, false).map(l -> getState(l));
    }

    private Thing<LightState> getState(Thing<Light> light) {

        LightState ls = createState(light.getKey());

        Thing<LightState> lightState = new Thing();
        lightState.setId("light:"+light.getId());
        lightState.setThingType(TypeRegistry.getType(LightState.class));
        lightState.setValueIsLink(false);
        lightState.setValue(ls);
        lightState.setKey(light.getKey());
        return lightState;
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
    public <V> V readValue(Thing<V> light) {

        return (V) createState(light.getKey());

    }
}
