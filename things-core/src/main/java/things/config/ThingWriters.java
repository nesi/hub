package things.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import things.exceptions.TypeRuntimeException;
import things.thing.ThingControl;
import things.thing.ThingWriter;

import java.util.List;
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

    public List<ThingWriter> get(String queryType, String queryKey) {

        String match_key = queryType + "/" + queryKey;

        Set<String> keys = thingWriters.keySet();

        List<ThingWriter> connectors = Lists.newArrayList();
        for (String key : keys) {

            if (ThingControl.keyMatcheskey(match_key, key) || ThingControl.keyMatcheskey(key, match_key)) {
                connectors.addAll(thingWriters.get(key));
            }
        }

        if (connectors.size() == 0) {
            throw new TypeRuntimeException("No connectors configured for: "
                    + match_key, queryType);
        }

        return connectors;
    }

    public ThingWriter getUnique(String queryType, String queryKey) {
        List<ThingWriter> c = get(queryType, queryKey);
        if (c.size() == 0) {
            throw new TypeRuntimeException("No connector configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        } else if (c.size() > 1) {
            throw new TypeRuntimeException("More than one connector configured for type '" + queryType + "' and key '" + queryKey + "'", queryType);
        }

        return c.get(0);
    }
}
