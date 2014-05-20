package things.view.rest;

import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rx.Observable;
import things.exceptions.ActionException;
import things.exceptions.NoSuchThingException;
import things.exceptions.ThingException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * @author: Markus Binsteiner
 */
@RestController
@RequestMapping(value = "/")

public class ExecuteRestController {

    private ThingControl thingControl;
    private ThingUtils thingUtils;

    @Inject
    public ExecuteRestController(ThingControl tc, ThingUtils tu) {
        this.thingControl = tc;
        this.thingUtils = tu;
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/everything", method = RequestMethod.POST)
    public List<Thing> executeAllThings(@PathVariable("actionName") String action, @RequestParam Map<String, String> actionParams) throws ActionException {

        Observable<? extends Thing<?>> things = thingControl.observeAllThings(false);

        Observable<? extends Thing<?>> handle = thingControl.executeAction(action, things, actionParams);

        return Lists.newArrayList(handle.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/every/{type}", method = RequestMethod.POST)
    public List<Thing> executeAllThingsOfType(@PathVariable("actionName") String action, @PathVariable("type") String type, @RequestParam Map<String, String> actionParams) throws ActionException {

        Observable<? extends Thing<?>> things = thingControl.observeThingsForType(type, false);

        Observable<? extends Thing<?>> handle = thingControl.executeAction(action, things, actionParams);

        return Lists.newArrayList(handle.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}", method = RequestMethod.POST)
    public List<Thing> executeGetAction(@PathVariable("actionName") String actionName, @RequestParam Map<String, String> allRequestParams) throws ActionException {

        Observable<? extends Thing<?>> handle = thingControl.executeAction(actionName, Observable.empty(), allRequestParams);

        return Lists.newArrayList(handle.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/{actionName}/{type}/{key}")
    public List<Thing> getUniqueThingForTypeAndKey(@PathVariable("actionName") String action, @PathVariable("type") String type, @PathVariable("key") String key, @RequestParam Map<String, String> actionParam) throws ThingException, NoSuchThingException, ActionException {

        Observable<? extends Thing<?>> thing = thingControl.observeUniqueThingMatchingTypeAndKey(type, key, false);

        Observable actionResult = thingControl.executeAction(action, thing, actionParam);

        return Lists.newArrayList(actionResult.toBlockingObservable().toIterable());
    }

}
