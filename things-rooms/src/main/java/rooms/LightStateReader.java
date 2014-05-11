package rooms;


/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 4/05/14
 * Time: 9:56 PM
 */
public class LightStateReader  {
//    public class LightStateReader implements ThingReader, ThingQuery<LightState> {

//    @Autowired
//    private ThingControl tc;
//
//    @Autowired
//    private LightUtil lightUtil;
//
//    @Override
//    public List<Thing<LightState>> query(List<Thing> things, Map<String, String> queryParams) throws QueryException {
//
//        List<Thing<LightState>> states = things.stream().map(t -> getState(t)).collect(Collectors.toList());
//        return states;
//    }
//
//    private LightState getLightState(Thing light) {
//
//        LightWhiteV2 l = lightUtil.getLights().get(light.getKey());
//        LightState ls = new LightState();
//        ls.setBrightness(l.getBrightness());
//        ls.setOn(l.isOn());
//        ls.setWarmth(l.getWarmth());
//
//        return ls;
//    }
//
//    private Thing<LightState> getState(Thing light) {
//
//        LightState ls = getLightState(light);
//
//        Thing t = new Thing(light.getKey(), ls);
//        return t;
//    }
//
//    @Override
//    public Serializable readValue(Thing t) {
//
//
//        Optional<Thing> light = null;
//        try {
//            light = tc.findUniqueThingByTypeAndKey(Light.class, t.getKey());
//            if ( ! light.isPresent() ) {
//                throw new NoSuchValueException("Can't find value for thing", t.getThingType(), t.getKey(), t.getValueId());
//            }
//            LightState state = getLightState(light.get());
//            return state;
//        } catch (ThingException e) {
//            throw new NoSuchValueException(e.getLocalizedMessage(), t.getThingType(), t.getKey(), t.getValueId());
//        }
//
//    }
}
