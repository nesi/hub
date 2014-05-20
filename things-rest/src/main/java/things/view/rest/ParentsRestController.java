package things.view.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
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
public class ParentsRestController {

    @Autowired
    private ThingControl thingControl;

    @Autowired
    private TypeRegistry typeRegistry;

    @Autowired
    private ThingUtils thingUtils;


    @Transactional(readOnly = true)
    @RequestMapping(value = "/parents/of/every/{type}/{key}/matching/{value}")
    public List<Thing> getParentsOfThingsMatchingTypeAndKeyAndValue(@PathVariable("type") String type, @PathVariable("key") String keyMatcher, @PathVariable("value") String stringValue) {

        Observable<? extends Thing<?>> result = thingControl.observeThingsMatchingKeyAndValueConvertedFromString(type, keyMatcher, stringValue);

        List<Thing> parents = thingControl.findParents(result);

        return parents;

    }


}
