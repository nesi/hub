package things.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import things.exceptions.TypeRuntimeException;
import things.thing.ThingWriter;
import things.utils.MatcherUtils;

import java.util.Set;

/**
 * @author: Markus Binsteiner
 */
public class ThingWriters {

    private final Multimap<String, ThingWriter> thingWriters = HashMultimap.create();

    public ThingWriters() {
    }

    public void addWriter(String matcher, ThingWriter reader) {

        thingWriters.put(matcher, reader);

    }

    public Set<ThingWriter> get(String queryType, String queryKey) {

        String match_key = queryType + "/" + queryKey;

        Set<String> keys = thingWriters.keySet();

        Set<ThingWriter> connectors = Sets.newHashSet();
        for ( String key : keys ) {

            if ( MatcherUtils.keyMatcheskey(match_key, key) || MatcherUtils.keyMatcheskey(key, match_key) ) {
                connectors.addAll(thingWriters.get(key));
            }
        }

        if ( connectors.size() == 0 ) {
            throw new TypeRuntimeException("No writer configured for: "
                    + match_key, queryType);
        }

        return connectors;
    }

    public ThingWriter getUnique(String queryType, String queryKey) {
        Set<ThingWriter> c = get(queryType, queryKey);
        if ( c.size() == 0 ) {
            throw new TypeRuntimeException("No writer configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        } else if ( c.size() > 1 ) {
            throw new TypeRuntimeException("More than one writer configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        }

        return c.iterator().next();
    }
}
