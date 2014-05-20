package things.view.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import things.thing.ThingControl;
import things.thing.ThingUtils;

/**
 * @author: Markus Binsteiner
 */
@RestController
@RequestMapping(value = "/find")
public class LookupRestController {

    @Autowired
    private ThingControl thingControl;

    @Autowired
    private ThingUtils thingUtils;

//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/every/{type}/matching/{value}", method = RequestMethod.GET)
//    public List<Thing> lookupThing(@PathVariable("type")String type, @PathVariable("value") String value) throws ThingException {
//
//        List<Thing> things = thingControl.findThingsOfTypeMatchingString(type, value);
//
//        return things;
//    }
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/every/{type}/{key}/matching/{value}", method = RequestMethod.GET)
//    public List<Thing> lookupThingWithKey(@PathVariable("type")String type, @PathVariable("key") String key, @PathVariable("value") String value) throws ThingException {
//        List<Thing> things = thingControl.findThingsTypeAndKeyMatchingString(type, key, value);
//        return things;
//    }
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/parents/of/every/{type}/matching/{value}", method = RequestMethod.GET)
//    public List<Thing> lookupParentsForLookupThingOfType(@PathVariable("type")String type, @PathVariable("value") String value) throws QueryRuntimeException, ThingException {
//
//        List<Thing> things = lookupThing(type, value);
//        List<Thing> result = thingControl.findThingsByOtherThing(things);
//
//        return result;
//    }
//
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/parents/of/every/{type}/{key}/matching/{value}", method = RequestMethod.GET)
//    public List<Thing> lookupParentsForLookupThingOfTypeWithKey(@PathVariable("type")String type, @PathVariable("key") String key, @PathVariable("value") String value) throws QueryRuntimeException, ThingException {
//
//        List<Thing> things = lookupThingWithKey(type, key, value);
//        List<Thing> result = thingControl.findThingsByOtherThing(things);
//
//        return result;
//    }
}
