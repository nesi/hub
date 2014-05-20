package things.view.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;
import things.types.TypeRegistry;

import java.util.List;

/**
 * Created by markus on 19/05/14.
 */
@RestController
@RequestMapping(value = "/get")
public class ValueRestController {

    @Autowired
    private ThingControl thingControl;
    @Autowired
    private ThingUtils thingUtils;
    @Autowired
    private TypeRegistry typeRegistry;

    @Transactional(readOnly = true)
    @RequestMapping(value = "/every/{type}/{key}/matching/{value}")
    public List<Thing> getThingsOfTypeMatchingKeyAndValue(@PathVariable("type") String type, @PathVariable("key") String keyMatcher, @PathVariable("value") String stringValue) {

        List<Thing> result = thingControl.findThingsMatchingKeyAndValueConvertedFromString(type, keyMatcher, stringValue);
        return result;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/every/{type}/matching/{value}")
    public List<Thing> getThingsOfTypeMatchingValue(@PathVariable("type") String type, @PathVariable("value") String stringValue) {

        List<Thing> result = thingControl.findThingsMatchingKeyAndValueConvertedFromString(type, "*", stringValue);
        return result;
    }


}
