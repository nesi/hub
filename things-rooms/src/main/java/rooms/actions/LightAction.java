package rooms.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import rooms.model.lights.limitless.whiteV2.LightWhiteV2;
import rooms.types.Light;
import rooms.types.LightState;
import rx.Observable;
import rx.functions.Action1;
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


    @Autowired
    public SimpMessagingTemplate simpMessagingTemplate;

    public LightAction() {

    }

    @Override
    public Observable<? extends Thing<?>> execute(String command, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        Observable<Thing<Light>> lights = tc.filterThingsOfType(Light.class, things);

        Observable<? extends Thing<?>> result = null;

        switch ( command ) {
            case "toggle":
                result = lights.map(l -> toggleLight(l));
                break;
            case "turn_on":
                result = lights.map(l -> switchOnLight(l, true));
                break;
            case "turn_off":
                result = lights.map(l -> switchOnLight(l, false));
                break;
            case "set":
                result = lights.map(l -> setLight(l, parameters));
                break;
            default:
                result = Observable.empty();
        }

        return result.doOnNext(o -> {
            simpMessagingTemplate.convertAndSend("/topic/light_change", o);
        });
    }

    private Thing<LightState> setLight(Thing<Light> light, Map<String, String> params) {
        LightWhiteV2 l = lightUtil.getLights().get(light.getKey());
        l.set(params);
        return lightUtil.createLightStateThing(light);
    }

    @Inject
    public void setLightUtil(LightUtil lu) {
        this.lightUtil = lu;
    }

    @Inject
    public void setThingControl(ThingControl tc) {
        this.tc = tc;
    }

    private Thing<LightState> switchOnLight(Thing<Light> light, boolean on) {
        LightWhiteV2 l = lightUtil.getLights().get(light.getKey());
        l.setOn(on);
        return lightUtil.createLightStateThing(light);
    }

    private Thing<LightState> toggleLight(Thing<Light> light) {
        LightWhiteV2 l = lightUtil.getLights().get(light.getKey());
        l.toggle();
        return lightUtil.createLightStateThing(light);
    }

}
