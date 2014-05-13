package hub.actions;

import com.google.common.collect.Maps;
import hub.model.lights.limitless.LimitlessLEDControllerV2;
import hub.model.lights.limitless.whiteV2.LightWhiteV2;
import hub.types.Bridge;
import hub.types.Light;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.TypeRegistry;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/05/14
 * Time: 10:44 PM
 */
public class LightUtil {

    private ThingControl tc;

    private List<Thing<Bridge>> bridges;
    private Map<String, LightWhiteV2> lights;

    public LightUtil() {
    }

    @Inject
    public void setThingControl(ThingControl tc) {
        this.tc = tc;
    }

    public synchronized List<Thing<Bridge>> getBridges() {
        if ( bridges == null ) {
            bridges = tc.findThingsForType(Bridge.class);
        }
        return bridges;
    }

    public synchronized Map<String, LightWhiteV2> getLights() {
        if ( lights == null ) {
            lights = Maps.newHashMap();
            for ( Thing<Bridge> bridgeThing : getBridges() ) {

                Bridge bridge = tc.getValue(bridgeThing);
                LimitlessLEDControllerV2 c = new LimitlessLEDControllerV2(bridge.getHost(), bridge.getPort());

                List<Thing<Light>> lightThings = tc.getChildrenForType(Observable.just(bridgeThing), Light.class);
                for ( Thing<Light> tempLight : lightThings ) {
                    Light ll = tc.getValue(tempLight);
                    LightWhiteV2 white = new LightWhiteV2(tempLight.getKey(), c, ll.getGroup());
                    lights.put(white.getName(), white);
                }

            }
        }
        return lights;
    }

    public static Stream<Thing<Light>> filterLights(List<Thing> lights) {

        return lights.stream()
                .filter(t -> TypeRegistry.equalsType(t.getThingType(), Light.class))
                .map(t -> (Thing<Light>)t);

    }
}
