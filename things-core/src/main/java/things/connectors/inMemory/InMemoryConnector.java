package things.connectors.inMemory;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import rx.Observable;
import rx.Subscriber;
import things.thing.AbstractSimpleThingReader;
import things.thing.Thing;
import things.thing.ThingWriter;

import java.util.*;

/**
 * Created by markus on 18/05/14.
 */
public class InMemoryConnector extends AbstractSimpleThingReader implements ThingWriter {

    private final Map<String, Multimap<String, Thing<?>>> allThings = Maps.newConcurrentMap();

    public void deleteAllThings() {
        allThings.clear();
    }

    @Override
    public boolean deleteThing(String id, Optional<String> thingType, Optional<String> key) {

        Set<String> typeSet = null;

        if ( thingType.isPresent() ) {
            typeSet = Sets.newHashSet(thingType.get());
        } else {
            typeSet = allThings.keySet();
        }

        for ( String type : typeSet ) {
            Set<String> keySet = null;
            if ( key.isPresent() ) {
                keySet = Sets.newHashSet(key.get());
            } else {
                keySet = allThings.get(type).keySet();
            }
            for ( String k : keySet ) {
                Collection<Thing<?>> t = allThings.get(type).get(k);
                Iterator<Thing<?>> i = t.iterator();
                while ( i.hasNext() ) {
                    if ( i.next().getId().equals(id) ) {
                        i.remove();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {

        return Observable.create((Subscriber<? super Thing<?>> subscriber) -> {
            for ( String type : allThings.keySet() ) {
                for ( String key : allThings.get(type).keySet() ) {
                    for ( Thing<?> t : allThings.get(type).get(key) ) {
                        subscriber.onNext(t);
                    }
                }
            }
            subscriber.onCompleted();
        });
    }

    @Override
    public <V> V readValue(Thing<V> thing) {
        return thing.getValue();
    }

    @Override
    public <V> Thing<V> saveThing(Thing<V> t) {
        if ( !Strings.isNullOrEmpty(t.getId()) ) {
            deleteThing(t.getId(), Optional.of(t.getThingType()), Optional.of(t.getKey()));
        } else {
            String id = UUID.randomUUID().toString();
            t.setId(id);
        }
        if ( allThings.get(t.getThingType()) == null ) {
            allThings.put(t.getThingType(), HashMultimap.create());
        }
        allThings.get(t.getThingType()).put(t.getKey(), t);
        return t;
    }

    @Override
    public <V> Thing<V> addChild(Thing<?> parent, Thing<V> child) {
        child.getParents().add(parent.getId());
        return saveThing(child);
    }

}
