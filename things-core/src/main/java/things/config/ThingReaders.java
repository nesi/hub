package things.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import things.exceptions.TypeRuntimeException;
import things.thing.Thing;
import things.thing.ThingReader;
import things.utils.MatcherUtils;

import java.util.List;
import java.util.Set;

/**
 * @author: Markus Binsteiner
 */
public class ThingReaders {

    private final Multimap<String, ThingReader> thingReaders = HashMultimap.create();

    public ThingReaders() {
    }

    public void addReader(String matcher, ThingReader reader) {

        thingReaders.put(matcher, reader);

    }

    public List<ThingReader> get(String queryType, String queryKey) {

        String match_key = queryType + "/" + queryKey;

        Set<String> keys = thingReaders.keySet();

        List<ThingReader> connectors = Lists.newArrayList();
        for ( String key : keys ) {

            if ( MatcherUtils.keyMatcheskey(match_key, key) || MatcherUtils.keyMatcheskey(key, match_key) ) {
                connectors.addAll(thingReaders.get(key));
            }
        }

        if ( connectors.size() == 0 ) {
            throw new TypeRuntimeException("No connectors configured for: "
                    + match_key, queryType);
        }

        return connectors;
    }

    public Set<ThingReader> getAll() {
        return Sets.newHashSet(thingReaders.values());
    }

    public List<ThingReader> getThingReadersMatchingKey(String queryKey) {
        return get("*", queryKey);
    }

    public List<ThingReader> getThingReadersMatchingType(String queryType) {
        return get(queryType, "*");
    }

    public ThingReader getUnique(String queryType, String queryKey) {
        List<ThingReader> c = get(queryType, queryKey);
        if ( c.size() == 0 ) {
            throw new TypeRuntimeException("No connector configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        } else if ( c.size() > 1 ) {
            throw new TypeRuntimeException("More than one connector configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        }

        return c.get(0);
    }

    public ThingReader getUnique(Thing child) {
        ThingReader r = getUnique(child.getThingType(), child.getKey());
        return r;
    }

}
