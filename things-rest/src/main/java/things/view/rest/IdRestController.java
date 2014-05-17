package things.view.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Markus Binsteiner
 */
@RestController
@RequestMapping(value = "/id")
public class IdRestController {


//    @Autowired
//    private ThingControl thingControl;
//
//    @Autowired
//    private ThingUtils thingUtils;
//
//
//    @RequestMapping(value = "/{id}/add/{id_other}", method = RequestMethod.POST)
//    public void addThingToThing(@PathVariable("id") String id, @PathVariable("id_other") String id_other) throws ThingException {
//
////        thingControl.addThingToThing(id, id_other);
//
//    }
//
//    @RequestMapping(value = "/{id}/add", method = RequestMethod.POST)
//    public void addNewThingToThing(@PathVariable("id") String id, @RequestBody Thing newThing) throws ThingException, ValueException {
//
//        if ( ! Strings.isNullOrEmpty(newThing.getId()) ) {
//            throw new ThingException(newThing, "Thing to create can't have an id set.");
//        }
//
//        PersistentValue v = (PersistentValue) thingControl.getUntypedValue(newThing);
//
//        Thing savedThing = thingControl.createThing(newThing.getKey(), v);
//        addThingToThing(id, savedThing.getId());
//
//    }
//
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Thing getThing(@PathVariable("id") String id) throws NoSuchThingException {
//
////        Thing thing = thingControl.observeThingById(id);
////        return thing;
//        return null;
//
//    }
}
