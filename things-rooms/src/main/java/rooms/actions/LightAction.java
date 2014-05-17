package rooms.actions;

import rooms.model.lights.limitless.whiteV2.LightWhiteV2;
import rooms.types.Light;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingAction;
import things.thing.ThingControl;

import javax.inject.Inject;
import java.util.Map;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/05/14
 * Time: 10:42 PM
 */
public class LightAction implements ThingAction {

    private LightUtil lightUtil;
    private ThingControl tc;

    public LightAction() {

    }

    @Override
    public String execute(String command, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        Observable<Thing<Light>> lights = tc.filterThingsOfType(Light.class, things);

        switch (command) {
            case "toggle":
                lights.toBlockingObservable().forEach(l -> toggleLight(l.getKey()));
                break;
            case "turn_on":
                lights.toBlockingObservable().forEach(l -> lightUtil.getLights().get(l.getKey()).setOn(true));
                break;
            case "turn_off":
                lights.toBlockingObservable().forEach(l -> lightUtil.getLights().get(l.getKey()).setOn(false));
                break;
            case "set":
                lights.toBlockingObservable().forEach(li -> lightUtil.getLights().get(li.getKey()).set(parameters));
            default:
                return null;
        }
        return null;
    }

    @Inject
    public void setLightUtil(LightUtil lu) {
        this.lightUtil = lu;
    }

    @Inject
    public void setThingControl(ThingControl tc) {
        this.tc = tc;
    }

    private void toggleLight(String lightname) {
        LightWhiteV2 l = lightUtil.getLights().get(lightname);
        l.toggle();
    }
}
