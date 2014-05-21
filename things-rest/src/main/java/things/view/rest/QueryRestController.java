package things.view.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingUtils;
import things.types.TypeRegistry;

import java.util.List;
import java.util.Map;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 1/05/14
 * Time: 6:51 PM
 */
@RestController
@RequestMapping(value = "/query")
public class QueryRestController {

    @Autowired
    private ThingControl thingControl;

    @Autowired
    private ThingUtils thingUtils;

    @Autowired
    private TypeRegistry typeRegistry;


    @Transactional(readOnly = true)
    @RequestMapping(value = "/{query}/for/every/{type}", method = RequestMethod.GET)
    @Timed
    public List<Thing> queryAllThingsOfType(@PathVariable("type") String type, @PathVariable("query") String query, @RequestParam Map<String, String> queryParam) {

        Observable<? extends Thing<?>> things = thingControl.observeThingsMatchingType(type, false);

        Observable<? extends Thing<?>> result = thingControl.executeQuery(query, things, queryParam);
        return Lists.newArrayList(result.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{query}/for/every/{type}/{key}/matching/{value}", method = RequestMethod.GET)
    @Timed
    public List<Thing> queryLookupSingleThingWithKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("value") String value, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) {
        Observable<? extends Thing<?>> things = thingControl.observeThingsMatchingKeyAndValueConvertedFromString(type, key, value);

        Observable<? extends Thing<?>> result = thingControl.executeQuery(query, things, queryParams);
        return Lists.newArrayList(result.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{query}/for/every/{type}/matching/{value}", method = RequestMethod.GET)
    @Timed
    public List<Thing> queryLookupThing(@PathVariable("type") String type, @PathVariable("value") String value, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) {

        Observable<? extends Thing<?>> things = thingControl.observeThingsMatchingKeyAndValueConvertedFromString(type, "*", value);

        Observable<? extends Thing<?>> result = thingControl.executeQuery(query, things, queryParams);
        return Lists.newArrayList(result.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{query}/for/{type}/{key}/matching/{value}", method = RequestMethod.GET)
    @Timed
    public List<Thing> queryLookupThingWithKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("value") String value, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) {
        Observable<? extends Thing<?>> things = thingControl.observeThingsMatchingKeyAndValueConvertedFromString(type, key, value).single();

        Observable<? extends Thing<?>> result = thingControl.executeQuery(query, things, queryParams);
        return Lists.newArrayList(result.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{query}/for/every/{type}/{key}")
    @Timed
    public List<Thing> queryThingsOfTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) {
        Observable<? extends Thing<?>> things = thingControl.observeThingsMatchingTypeAndKey(type, key, false);

        Observable<? extends Thing<?>> result = thingControl.executeQuery(query, things, queryParams);
        return Lists.newArrayList(result.toBlockingObservable().toIterable());
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{query}/for/{type}/matching/{value}", method = RequestMethod.GET)
    @Timed
    public List<Thing> queryUniqueLookupThing(@PathVariable("type") String type, @PathVariable("value") String value, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) {

        Observable<? extends Thing<?>> things = thingControl.observeThingsMatchingKeyAndValueConvertedFromString(type, "*", value).single();

        Observable<? extends Thing<?>> result = thingControl.executeQuery(query, things, queryParams);
        return Lists.newArrayList(result.toBlockingObservable().toIterable());

    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "{query}/for/{type}/{key}")
    @Timed
    public List<Thing> queryUniqueThingWithTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("query") String query, @RequestParam Map<String, String> allRequestParams) {

        Observable<? extends Thing<?>> things = thingControl.observeThingsMatchingTypeAndKey(type, key, false).single();

        Observable<? extends Thing<?>> result = thingControl.executeQuery(query, things, allRequestParams);
        return Lists.newArrayList(result.toBlockingObservable().toIterable());

    }


}
