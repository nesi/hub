package things.view.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rx.Observable;
import things.exceptions.ActionException;
import things.exceptions.NoSuchThingException;
import things.exceptions.ThingException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;

import java.util.Map;

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
    
    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/{type}/{key}")
    public String getUniqueThingForTypeAndKey(@PathVariable("actionName") String action, @PathVariable("type") String type, @PathVariable("key") String key, @RequestParam Map<String, String> actionParam) throws ThingException, NoSuchThingException, ActionException {

        Observable<Thing> thing = thingControl.observeUniqueThingMatchingTypeAndKey(type, key, false);

        return thingControl.executeAction(action, thing, actionParam);

    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/every/{type}", method = RequestMethod.POST)
    public String executeAllThingsOfType(@PathVariable("actionName") String action, @PathVariable("type") String type,  @RequestParam Map<String, String> actionParams) throws ActionException {

        Observable<Thing> things = thingControl.observeThingsForType(type, false);

        String handle = thingControl.executeAction(action, things, actionParams);
        return handle;
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/everything", method = RequestMethod.POST)
    public String executeAllThings(@PathVariable("actionName") String action, @RequestParam Map<String, String> actionParams) throws ActionException {

        Observable<Thing> things = thingControl.observeAllThings(false);

        String handle = thingControl.executeAction(action, things, actionParams);
        return handle;
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}", method = RequestMethod.POST)
    public String executeGetAction(@PathVariable("actionName") String actionName, @RequestParam Map<String,String> allRequestParams) throws ActionException {

        String handle = thingControl.executeAction(actionName, Observable.empty(), allRequestParams);
        return handle;
    }

}
