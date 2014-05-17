package things.view.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @Autowired
//    private ThingControl thingControl;
//
//    @Autowired
//    private ThingUtils thingUtils;


//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/{query}/for/every/{type}", method = RequestMethod.GET)
//    public List<Thing> queryAllThingsOfType(@PathVariable("type") String type, @PathVariable("query") String query, @RequestParam Map<String, String> queryParam) throws QueryException {
//        List<Thing> things = thingControl.findThingsForType(type);
//
//        List<Thing> result = thingControl.queryForThings(query, things, queryParam);
//        return result;
//    }
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/{query}/for/every/{type}/{key}")
//    public List<Thing> queryThingsOfTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) throws QueryException {
//        List<Thing> things = thingControl.findThingsByTypeAndKey(type, key);
//
//        List<Thing> result = thingControl.queryForThings(query, things, queryParams);
//        return result;
//    }
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "{query}/for/unique/{type}/{key}")
//    public List<Thing> queryUniqueThingWithTypeAndKey(@PathVariable("type") String type, @PathVariable("key") String key, @PathVariable("query") String query, @RequestParam Map<String,String> allRequestParams) throws ThingException, NoSuchThingException, QueryException {
//        Optional<Thing> thing = thingControl.findUniqueThingByTypeAndKey(type, key);
//
//        if (! thing.isPresent() ) {
//            throw new NoSuchThingException(type, key);
//        }
//
//        List<Thing> result = thingControl.queryForThings(query, Lists.newArrayList(thing.get()), allRequestParams);
//
//        return result;
//    }
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/{query}/for/single/{type}/matching/{value}", method = RequestMethod.GET)
//    public List<Thing> queryUniqueLookupThing(@PathVariable("type")String type, @PathVariable("value") String value, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) throws QueryException, ThingException {
//
//        List<Thing> things = thingControl.findThingsOfTypeMatchingString(type, value);
//
//        if (things.size() == 0 ) {
//            throw new NoSuchThingException("No item found for type "+type+" matching "+value, type, null, null);
//        } else if ( things.size() > 1 ) {
//            throw new ThingException("More than one item found for "+type+" matching "+value);
//        }
//        List<Thing> result = thingControl.queryForThings(query, things, queryParams);
//        return result;
//    }
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/{query}/for/every/{type}/matching/{value}", method = RequestMethod.GET)
//    public List<Thing> queryLookupThing(@PathVariable("type")String type, @PathVariable("value") String value, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) throws QueryException, ThingException {
//
//        List<Thing> things = thingControl.findThingsOfTypeMatchingString(type, value);
//
//        if (things.size() == 0 ) {
//            return things;
//        }
//        List<Thing> result = thingControl.queryForThings(query, things, queryParams);
//        return result;
//    }
//
//    @Transactional(readOnly = true)
//    @RequestMapping(value = "/{query}/for/every/{type}/{key}/matching/{value}", method = RequestMethod.GET)
//    public List<Thing> queryLookupThingWithKey(@PathVariable("type")String type, @PathVariable("key") String key, @PathVariable("value") String value, @PathVariable("query") String query, @RequestParam Map<String, String> queryParams) throws QueryException, ThingException {
//
//        List<Thing> things = thingControl.findThingsOfTypeAndKeyMatchingString(type, key, value);
//
//        if (things.size() == 0 ) {
//            return things;
//        }
//
//        List<Thing> result = thingControl.queryForThings(query, things, queryParams);
//
//        return result;
//    }


}
