package things.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import things.exceptions.TypeRuntimeException;
import things.thing.Thing;
import things.thing.ThingReader;
import things.utils.MatcherUtils;

import java.util.Set;

/**
 * Collection class for all {@link things.thing.ThingReader}s that are used in this application.
 *
 * Readers are registered using a matcher-string in the format:
 *
 *     [type-matcher]/[key-matcher]
 *
 * That means, for example if you want to add a reader for all things of type 'person', you'd register the reader using:
 *
 *     person/*
 *
 * If you only have one reader for your whole application, you can just add it via:
 *
 *     * / * (without white-spaces)
 *
 * If you want to use a reader for all things of type 'person', where the person key ends with 'a', you could do:
 *
 *     person/*a
 *
 * And so on...
 */
public class ThingReaders {

    private final Multimap<String, ThingReader> thingReaders = HashMultimap.create();

    public ThingReaders() {
    }

    /**
     * Add a reader and register it against a type/key-matcher.
     *
     * @param matcher the matcher (e.g. 'person/*'), if the 2nd part of the matcher is omitted (e.g. 'person'), '/*' will be added (resulting in: 'person/*')
     * @param reader  the reader object
     */
    public void addReader(String matcher, ThingReader reader) {

        int i = matcher.indexOf("/");
        if ( i <= 0 ) {
            matcher = matcher + "/*";
        }

        thingReaders.put(matcher, reader);

    }

    /**
     * Get all readers that match the specfied type/key combination.
     *
     * Both type and key in put can be a glob, but don't need to be. Whether or not a reader is returned depends on the outcome of a
     * two-way match of registered matcher-token of the reader and the specified type/key combination.
     *
     * @param queryType the query type or type matcher
     * @param queryKey  the query key or key matcher
     * @return a set of all readers that are a match
     */
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

    /**
     * Returns all registered readers.
     *
     * Used to get all things, application-wide.
     */
    public Set<ThingReader> getAll() {
        return Sets.newHashSet(thingReaders.values());
    }

    /**
     * Convenience method to get all readers that match the specified key.
     */
    public Set<ThingReader> getThingReadersMatchingKey(String queryKey) {
        return get("*", queryKey);
    }

    /**
     * Convenience method to get all readers that match the specified type.
     */
    public Set<ThingReader> getThingReadersMatchingType(String queryType) {
        return get(queryType, "*");
    }

    /**
     * Get the single reader that matches the specfied type/key combination.
     *
     * Both type and key in put can be a glob, but don't need to be. Whether or not a reader is returned depends on the outcome of a
     * two-way match of registered matcher-token of the reader and the specified type/key combination.
     *
     * @param queryType the query type or type matcher
     * @param queryKey  the query key or key matcher
     * @return the unqiue reader that matches the specfied type/key combination
     * @throws things.exceptions.TypeRuntimeException if no, or multiple readers are found for the specified type/key combination
     */
    public ThingReader getUnique(String queryType, String queryKey) {
        Set<ThingReader> c = get(queryType, queryKey);
        if ( c.size() == 0 ) {
            throw new TypeRuntimeException("No reader configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        } else if ( c.size() > 1 ) {
            throw new TypeRuntimeException("More than one reader configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        }

        return c.iterator().next();
    }

    /**
     * Convenience method to get the unqiue reader that can be used to read the specified Thing.
     *
     * Uses {@link #getUnique(things.thing.Thing)}.
     */
    public ThingReader getUnique(Thing child) {
        ThingReader r = getUnique(child.getThingType(), child.getKey());
        return r;
    }

}
