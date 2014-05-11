package rooms.queries;

import rooms.control.LightUtil;
import rooms.types.LightState;
import things.exceptions.QueryException;
import things.thing.Thing;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 2/05/14
 * Time: 7:46 PM
 */
public class LightStateQuery {

    private LightUtil lightUtil;

    public List<Thing<LightState>> query(List<Thing> things, Map<String, String> queryParams) throws QueryException {

        List<Thing<LightState>> states = things.stream().map(t -> getState(t)).collect(Collectors.toList());
        return states;
    }

    private Thing<LightState> getState(Thing light) {

//        LightWhiteV2 l = lightUtil.getLights().get(light.getKey());
//        LightState ls = new LightState();
//        ls.setBrightness(l.getBrightness());
//        ls.setOn(l.isOn());
//        ls.setWarmth(l.getWarmth());

//        Thing t = new Thing(light.getKey(), ls);
        return null;
    }
}
