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

        int i = matcher.indexOf("/");
        if ( i <= 0 ) {
            matcher = matcher+"/*";
        }

        thingReaders.put(matcher, reader);

    }

    public Set<ThingReader> get(String queryType, String queryKey) {

        String match_key = queryType + "/" + queryKey;

        Set<String> keys = thingReaders.keySet();

        Set<ThingReader> connectors = Sets.newHashSet();
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

    public Set<ThingReader> getThingReadersMatchingKey(String queryKey) {
        return get("*", queryKey);
    }

    public Set<ThingReader> getThingReadersMatchingType(String queryType) {
        return get(queryType, "*");
    }

    public ThingReader getUnique(String queryType, String queryKey) {
        Set<ThingReader> c = get(queryType, queryKey);
        if ( c.size() == 0 ) {
            throw new TypeRuntimeException("No connector configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        } else if ( c.size() > 1 ) {
            throw new TypeRuntimeException("More than one connector configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        }

        return c.iterator().next();
    }

    public ThingReader getUnique(Thing child) {
        ThingReader r = getUnique(child.getThingType(), child.getKey());
        return r;
    }

}
