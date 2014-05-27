package rooms;

import com.google.common.collect.Maps;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import rooms.config.RoomConfig;
import rooms.model.lights.limitless.LimitlessLEDControllerV2;
import rooms.model.lights.limitless.whiteV2.LightWhiteV2;
import rooms.types.Bridge;
import rooms.types.Group;
import rooms.types.Light;
import rx.Observable;
import things.exceptions.ThingException;
import things.exceptions.ValueException;
import things.thing.Thing;
import things.thing.ThingControl;

import java.util.List;
import java.util.Map;

/**
 * Project: hub
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/03/14
 * Time: 10:56 AM
 */
public class Application {


    public static void main(String[] args) throws ValueException, ThingException, InterruptedException {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RoomConfig.class);
        //AbstractApplicationContext context = new AnnotationConfigApplicationContext(RoomConfigMongo.class);

//        MongoOperations mo = (MongoOperations) context.getBean("mongoTemplate");
        final ThingControl tc = (ThingControl) context.getBean("thingControl");

        for ( String s : context.getBeanDefinitionNames() ) {
            System.out.println(s);
        }


        System.out.println("THINGSSSS");
        for ( Thing t : tc.findAllThings() ) {
            System.out.println("THING: "+t);
        }

//        mo.dropCollection(Thing.class);
//        mo.dropCollection(Bridge.class);
//        mo.dropCollection(Light.class);


        List<Thing> t = tc.findThingsForType("bridge");

        Bridge b = new Bridge("10.0.0.40");
        Thing tb = tc.createThing("bridge", b);

        Light l = new Light(Group.GROUP_1, "white");
        Thing tl = tc.createThing("bedroom_ceiling", l);

        Light l2 = new Light(Group.GROUP_2, "white");
        Thing tl2 = tc.createThing("bedroom_bed", l2);

        Light l3 = new Light(Group.GROUP_3, "white");
        Thing tl3 = tc.createThing("bedroom_desk", l3);

        tc.addChildThing(tb, tl);
        tc.addChildThing(tb, tl2);
        tc.addChildThing(tb, tl3);


        List<Thing<Bridge>> bridges = tc.findThingsForType(Bridge.class);

        Map<String, LightWhiteV2> lights = Maps.newHashMap();

        for ( Thing<Bridge> bridgeThing : bridges ) {

            Bridge bridge = tc.getValue(bridgeThing);
            LimitlessLEDControllerV2 c = new LimitlessLEDControllerV2(b.getHost(), b.getPort());

            List<Thing<Light>> lightThings = tc.getChildrenForType(Observable.just(bridgeThing), Light.class, true);
            for ( Thing<Light> tempLight : lightThings ) {
                Light ll = tc.getValue(tempLight);
                LightWhiteV2 white = new LightWhiteV2(tempLight.getKey(), c, ll.getLightGroup());
                lights.put(white.getName(), white);
                System.out.println("LIGHT: " + white.getName());
            }
        }

//        lights.get("bedroom_ceiling").setOn(true);
        lights.get("bedroom_bed").setOn(true);
        Thread.sleep(2000);
//        lights.get("bedroom_ceiling").setOn(false);
        lights.get("bedroom_bed").setOn(false);
        Thread.sleep(2000);

    }


}
