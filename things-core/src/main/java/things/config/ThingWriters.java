package things.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import things.exceptions.TypeRuntimeException;
import things.thing.ThingWriter;
import things.utils.MatcherUtils;

import java.util.Set;

/**
 * Collection class for all {@link things.thing.ThingWriter}s that are used in this application.
 *
 * Writers are registered using a matcher-string in the format:
 *
 *     [type-matcher]/[key-matcher]
 *
 * That means, for example if you want to add a writer for all things of type 'person', you'd register the writer using:
 *
 *     person/*
 *
 * If you only have one writer for your whole application, you can just add it via:
 *
 *     * / * (without white-spaces)
 *
 * If you want to use a writer for all things of type 'person', where the person key ends with 'a', you could do:
 *
 *     person/*a
 *
 * And so on...
 *
 * Only one writer can ever be returned for a registered type/key combination, so make sure there is no overlap.
 */
public class ThingWriters {

    private final Multimap<String, ThingWriter> thingWriters = HashMultimap.create();

    public ThingWriters() {
    }

    /**
     * Add a writer and register it against a type/key-matcher.
     *
     * @param matcher the matcher (e.g. 'person/*'), if the 2nd part of the matcher is omitted (e.g. 'person'), '/*' will be added (resulting in: 'person/*')
     * @param writer  the writer object
     */
    public void addWriter(String matcher, ThingWriter writer) {

        thingWriters.put(matcher, writer);

    }


    private Set<ThingWriter> get(String queryType, String queryKey) {

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

    /**
     * Get the single writer that matches the specfied type/key combination.
     * <p>
     * Both type and key in put can be a glob, but don't need to be. Whether or not a reader is returned depends on the outcome of a
     * two-way match of registered matcher-token of the reader and the specified type/key combination.
     *
     * @param queryType the query type or type matcher
     * @param queryKey  the query key or key matcher
     * @return the unqiue writer that matches the specfied type/key combination
     * @throws things.exceptions.TypeRuntimeException if no, or multiple writers are found for the specified type/key combination
     */
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
