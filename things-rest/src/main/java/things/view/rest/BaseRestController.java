package things.view.rest;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import things.exceptions.ThingException;
import things.exceptions.ValueException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: Markus Binsteiner
 */
@RestController
@RequestMapping(value = "/")
public class BaseRestController {

    @Autowired
    private ThingControl thingControl;

    @Autowired
    private ThingUtils thingUtils;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Thing createThing(@RequestBody Thing thing) throws ThingException, ValueException {

        if (!Strings.isNullOrEmpty(thing.getId())) {
            throw new ThingException(thing, "Thing to create can't have an id set.");
        }

        Object value = thing.getValue();

        Thing savedThing = thingControl.createThing(thing.getKey(), value);

        return savedThing;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Thing> getAllThings() {

        List<Thing> things = thingControl.findAllThings();
        return things;
    }

    @Transactional
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public Map<String, Map<String, String>> getAllTypes() {
        return thingUtils.getRegisteredTypeProperties();
    }


//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/query/{queryName}", method = RequestMethod.POST)
//    public List<Thing> queryForThings(@PathVariable("queryName") String queryName, @RequestParam(required = false) Map<String, String> queryParameters) throws QueryException, QueryException {
//
//        List<Thing> result = thingControl.queryForThings(queryName, null, queryParameters);
//        return result;
//
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.PUT)
//    public void updateThing(@RequestBody Thing thing) throws ThingException, ValueException {
//
//        thingControl.updateThingUsingId(thing);
//
//
//    }
//
//
//    @Transactional(readOnly = false)
//    @RequestMapping(value = "/{type}/{key}/value/update", method = RequestMethod.POST)
//    public void updateValueProperty(@PathVariable("type") String type, @PathVariable("key") String key, @RequestParam Map<String, String> updatedValueProperties) throws ThingException, ValueException, TypeException {
//
//        Optional<Thing> t = thingControl.findUniqueThingByTypeAndKey(type, key);
//        thingControl.updatePersistentValue(t.get(), updatedValueProperties);
//
//    }

}
