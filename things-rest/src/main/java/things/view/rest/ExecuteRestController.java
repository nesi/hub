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
@RequestMapping(value = "/")

public class ExecuteRestController {
    @Autowired
    private ThingControl thingControl;
    
    @Autowired
    private ThingUtils thingUtils;
    
//    @Transactional(readOnly = false)
//    @RequestMapping(value = "/{actionName}/{type}/{key}")
//    public String getUniqueThingForTypeAndKey(@PathVariable("actionName") String action, @PathVariable("type") String type, @PathVariable("key") String key, @RequestParam Map<String, String> actionParam) throws ThingException, NoSuchThingException, ActionException {
//        Optional<Thing> thing = thingControl.findUniqueThingByTypeAndKey(type, key);
//
//        if ( ! thing.isPresent() ) {
//            throw new NoSuchThingException(type, key);
//        }
//
//        return thingControl.executeAction(action, Lists.newArrayList(thing.get()), actionParam);
//    }
//
//    @Transactional(readOnly = false)
//    @RequestMapping(value = "/{actionName}/every/{type}", method = RequestMethod.POST)
//    public String executeAllThingsOfType(@PathVariable("actionName") String action, @PathVariable("type") String type,  @RequestParam Map<String, String> actionParams) throws ActionException {
//
//        List<Thing> things = thingControl.findThingsForType(type);
//
//        String handle = thingControl.executeAction(action, things, actionParams);
//        return handle;
//    }
//
//    @Transactional(readOnly = false)
//    @RequestMapping(value = "/{actionName}/everything", method = RequestMethod.POST)
//    public String executeAllThings(@PathVariable("actionName") String action, @RequestParam Map<String, String> actionParams) throws ActionException {
//
//        List<Thing> things = null;//thingControl.findAllThings();
//
//        String handle = thingControl.executeAction(action, things, actionParams);
//        return handle;
//    }
//
//    @Transactional(readOnly = false)
//    @RequestMapping(value = "/{actionName}", method = RequestMethod.POST)
//    public String executeGetAction(@PathVariable("actionName") String actionName, @RequestParam Map<String,String> allRequestParams) throws ActionException {
//
//        String handle = thingControl.executeAction(actionName, null, allRequestParams);
//        return handle;
//    }

}
