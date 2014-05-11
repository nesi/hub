package rooms.control;

import things.thing.ThingControl;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 13/04/14
 * Time: 4:33 PM
 */
public class LightController  {

    private final ThingControl tc;


    private LightUtil lightUtil;

    public LightController(ThingControl tc) {
        this.tc = tc;
    }


//    public String execute(String command, List<Thing> things, Map<String, String> parameters) {
//
//        switch (command) {
//            case "toggle":
//                lightUtil.filterLights(things).forEach( li -> toggleLight(li.getKey()));
//                break;
//            case "turn_on":
//                lightUtil.filterLights(things).forEach( li -> lightUtil.getLights().get(li.getKey()).setOn(true));
//                break;
//            case "turn_off":
//                lightUtil.filterLights(things).forEach( li -> lightUtil.getLights().get(li.getKey()).setOn(false));
//                break;
//            default:
//                return null;
//        }
//        return null;
//    }
//
//
//
//    private void toggleLight(String lightname) {
//        LightWhiteV2 l = lightUtil.getLights().get(lightname);
//        l.toggle();
//    }





}
