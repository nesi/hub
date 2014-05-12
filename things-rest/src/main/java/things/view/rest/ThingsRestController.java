package things.view.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import things.exceptions.ThingException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;

import java.util.List;

/**
 * @author: Markus Binsteiner
 */
@RestController
@RequestMapping(value = "/get/every")
public class ThingsRestController {
    
    @Autowired
    private ThingControl thingControl;

    @Autowired
    private ThingUtils thingUtils;

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{type}")
    public List<Thing> getAllThingsOfType(@PathVariable("type") String type) {
        List<Thing> things = thingControl.findThingsMatchingType(type);
        return things;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{type}/{key}")
    public List<Thing> getAllThingsOfTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key) {
        List<Thing> things = thingControl.findThingsMatchingTypeAndKey(type, key);
        return things;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{type}/{key}/others")
    public List<Thing> getChildrenForThing(@PathVariable("type") String type, @PathVariable("key") String key) {

        Observable<Thing> t = thingControl.observeThingsMatchingTypeAndKey(type, key, false);
        List<Thing> things = thingControl.getChildren(t);
        return things;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{queryType}/of/every/{type}/{key}")
    public List<Thing> getChildrenOfTypeForTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("queryType") String queryType) {

        return getChildrenOfTypeForTypeAndKey(type, key, queryType, "*");
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{queryType}/for/{queryKey}/of/every/{type}/{key}")
    public List<Thing> getChildrenOfTypeForTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("queryType") String queryType, @PathVariable("queryKey") String queryKey) {

        Observable<Thing> t = thingControl.observeThingsMatchingTypeAndKey(type, key, false);
        List<Thing> things = thingControl.getChildsMatchingTypeAndKey(t, queryType, queryKey);

        return things;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{queryType}/of/{type}/{key}")
    public List<Thing> getChildrenOfTypeForUniqueTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("queryType") String queryType) throws ThingException {

        Observable<Thing> t = thingControl.observeUniqueThingMatchingTypeAndKey(type, key, false);

        List<Thing> things = thingControl.getChildsMatchingTypeAndKey(t, queryType, "*");
        return things;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{queryType}/{queryKey}/of/{type}/{key}")
    public List<Thing> getChildrenOfTypeForUniqueTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("queryType") String queryType, @PathVariable("queryKey") String queryKey) throws ThingException {

        Observable<Thing> t = thingControl.observeUniqueThingMatchingTypeAndKey(type, key, false);

        List<Thing> things = thingControl.getChildsMatchingTypeAndKey(t, queryType, queryKey);
        return things;
    }
}
