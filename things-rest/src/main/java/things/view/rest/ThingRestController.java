package things.view.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import things.exceptions.NoSuchThingException;
import things.exceptions.ThingException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;

import java.util.Optional;


/**
 *
 */
@RestController
@RequestMapping(value = "/rest/get")
public class ThingRestController {

    @Autowired
    private ThingControl thingControl;

    @Autowired
    private ThingUtils thingUtils;


    @Transactional(readOnly = true)
    @RequestMapping(value = "/{type}/{key}")
    @Timed
    public Thing getUniqueThingForTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key) throws ThingException, NoSuchThingException {
        Optional<Thing> thing = thingControl.findUniqueThingMatchingTypeAndKey(type, key, true);

        if ( !thing.isPresent() ) {
            throw new NoSuchThingException(type, key);
        }

        return thing.get();
    }


}
