package rooms.control;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 2/05/14
 * Time: 7:49 PM
 */
public class LightUtil {

//    private ThingControl tc;
//
//    private List<Thing<Bridge>> bridges;
//    private Map<String, LightWhiteV2> lights;
//
//    public synchronized List<Thing<Bridge>> getBridges() {
//        if ( bridges == null ) {
//            bridges = tc.findThingsByType(Bridge.class);
//        }
//        return bridges;
//    }
//
//    public synchronized Map<String, LightWhiteV2> getLights() {
//        if ( lights == null ) {
//            lights = Maps.newHashMap();
//            for ( Thing<Bridge> bridgeThing : getBridges() ) {
//
//                Bridge bridge = tc.getValue(bridgeThing);
//                LimitlessLEDControllerV2 c = new LimitlessLEDControllerV2(bridge.getHost(), bridge.getPort());
//
//                List<Thing<Light>> lightThings = tc.getOtherThingsByType(bridgeThing, Light.class);
//                for ( Thing<Light> tempLight : lightThings ) {
//                    Light ll = tc.getValue(tempLight);
//                    LightWhiteV2 white = new LightWhiteV2(tempLight.getKey(), c, ll.getGroup());
//                    lights.put(white.getName(), white);
//                }
//
//            }
//        }
//        return lights;
//    }
//
//    public static Stream<Thing<Light>> filterLights(List<Thing> lights) {
//
//        return lights.stream()
//                .filter(t -> TypeRegistry.equalsType(t.getThingType(), Light.class))
//                .map(t -> (Thing<Light>)t);
//
//    }
}
