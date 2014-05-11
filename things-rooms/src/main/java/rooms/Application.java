package rooms;

import rooms.types.Bridge;
import rooms.types.Group;
import rooms.types.Light;
import things.exceptions.ThingException;
import things.exceptions.ValueException;
import things.thing.Thing;
import things.thing.ThingControl;

/**
 * Project: hub
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 12/03/14
 * Time: 10:56 AM
 */
public class Application {
    

	public static void main(String[] args) throws ValueException, ThingException, InterruptedException {
        
//        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RoomConfig.class);

//        MongoOperations mo = (MongoOperations) context.getBean("mongoTemplate");
//        final ThingControl tc = (ThingControl) context.getBean("thingControl");

//        for (String s : context.getBeanDefinitionNames() ) {
//            System.out.println(s);
//        }

        ThingControl tc = null;
//        mo.dropCollection(Thing.class);
//        mo.dropCollection(Bridge.class);
//        mo.dropCollection(Light.class);

        Bridge b = new Bridge("10.0.0.40");
        Thing tb = tc.createThing("bridge", b);

        Light l = new Light(Group.GROUP_1, "type");
        Thing tl = tc.createThing("bedroom_ceiling", l);

        Light l2 = new Light(Group.GROUP_2, "type");
        Thing tl2 = tc.createThing("bedroom_bed", l2);

        tc.addChildThing(tb, tl);
        tc.addChildThing(tb, tl2);


//        List<Thing<Bridge>> bridges = tc.findThingsByType(Bridge.class);
//
//        Map<String, LightWhiteV2> lights = Maps.newHashMap();
//
//        for ( Thing<Bridge> bridgeThing : bridges ) {
//
//            Bridge bridge = tc.getValue(bridgeThing);
//            LimitlessLEDControllerV2 c = new LimitlessLEDControllerV2(b.getHost(), b.getPort());
//
//            List<Thing<Light>> lightThings = tc.getOtherThingsByType(bridgeThing, Light.class);
//            for ( Thing<Light> tempLight : lightThings ) {
//                Light ll = tc.getValue(tempLight);
//                LightWhiteV2 white = new LightWhiteV2(tempLight.getKey(), c, l.getGroup());
//                lights.put(white.getName(), white);
//            }
//
//        }
//
//        lights.get("bedroom_ceiling").setOn(true);
//        lights.get("bedroom_bed").setOn(true);
//        Thread.sleep(2000);
//        lights.get("bedroom_ceiling").setOn(false);
//        lights.get("bedroom_bed").setOn(false);

	}




}
