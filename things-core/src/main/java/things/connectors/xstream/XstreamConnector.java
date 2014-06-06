/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package things.connectors.xstream;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.thoughtworks.xstream.XStream;
import rx.Observable;
import rx.Subscriber;
import things.exceptions.ThingRuntimeException;
import things.thing.AbstractSimpleThingReader;
import things.thing.Thing;
import things.thing.ThingReader;
import things.thing.ThingWriter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author: Markus Binsteiner
 */
public class XstreamConnector extends AbstractSimpleThingReader implements ThingReader, ThingWriter {

    private Map<String, Multimap<String, Thing<?>>> allThingsCache = null;
    private final File thingsFolder;
    private final File valuesFolder;

    private final XStream xstream = new XStream();

    @Inject
    public XstreamConnector(@Named("thingsFolder") File thingsFolder, @Named("valuesFolder") File valuesFolder) {
        this.thingsFolder = thingsFolder;
        this.thingsFolder.mkdirs();
        this.valuesFolder = valuesFolder;
        this.valuesFolder.mkdirs();
        xstream.processAnnotations(Thing.class);
    }

    @Override
    public <V> Thing<V> addChild(Thing<?> parent, Thing<V> child) {
        child.getParents().add(parent.getId());
        return saveThing(child);
    }

    private void addElement(Thing<?> t) {

        if ( allThingsCache.get(t.getThingType()) == null ) {
            allThingsCache.put(t.getThingType(), HashMultimap.create());
        }
        allThingsCache.get(t.getThingType()).put(t.getKey(), t);

    }

    private Thing<?> assembleThing(Path path) {
        return assembleThing(path.toFile());
    }

    private Thing<?> assembleThing(File file) {
        Thing t = (Thing) xstream.fromXML(file);
        return t;
    }

    @Override
    public boolean deleteThing(String id, Optional<String> thingType, Optional<String> key) {
        List<File> typeSet = null;

        if ( thingType.isPresent() ) {
            typeSet = Lists.newArrayList(getTypeFolder(thingType.get()));
        } else {
            File[] folders = thingsFolder.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });
            typeSet = Arrays.asList(folders);
        }

        for ( File type : typeSet ) {
            List<File> keySet = null;
            if ( key.isPresent() ) {
                keySet = Lists.newArrayList(new File(type, key.get()));
            } else {
                File[] files = thingsFolder.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isFile() && pathname.getName().endsWith(".thing");
                    }
                });
                keySet = Arrays.asList(files);
            }
            for ( File k : keySet ) {
                if ( k.getName().contains(id) ) {
                    return k.delete();
                }
            }
        }
        return false;
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {

        if ( allThingsCache == null ) {

            allThingsCache = Maps.newConcurrentMap();

            Observable<? extends Thing<?>> obs = Observable.create((Subscriber<? super Thing<?>> subscriber) -> {
                new Thread(() -> {
                    try {
                        Files.walk(Paths.get(thingsFolder.toURI()))
                                .filter((path) -> path.toString().endsWith(".thing"))
                                .map(path -> assembleThing(path))
                                .peek(t -> addElement(t))
                                .forEach(t -> subscriber.onNext(t));

                        subscriber.onCompleted();

                    } catch (IOException e) {
                        throw new ThingRuntimeException("Could not read thing files.", e);
                    }
                }).start();
            });
            return obs;
        } else {
            Observable<? extends Thing<?>> obs = Observable.create((Subscriber<? super Thing<?>> subscriber) -> {
                for ( String type : allThingsCache.keySet() ) {
                    for ( String key : allThingsCache.get(type).keySet() ) {
                        for ( Thing<?> thing : allThingsCache.get(type).get(key) ) {
                            subscriber.onNext(thing);
                        }
                    }
                }
                subscriber.onCompleted();
            });

            return obs;
        }
    }

    private Path getPath(String type, String key, String id) {
        Path path = FileSystems.getDefault().getPath(thingsFolder.getAbsolutePath(), type, key + "_id_" + id + ".thing");
        return path;
    }

    private Path getPath(Thing t) {
        return getPath(t.getThingType(), t.getKey(), t.getId());
    }

    private File getTypeFolder(String type) {
        File typeFolder = new File(valuesFolder, type);
        return typeFolder;
    }

    private File getValueFile(Object value, String id) {

        String type = typeRegistry.getType(value);
        File folder = getTypeFolder(type);

        return new File(folder, id + ".value");
    }

    public <V> V readValue(Thing<V> t) {
        String type = t.getThingType();
        Object valueId = t.getValue();
        File typeFolder = getTypeFolder(type);
        File file = new File(typeFolder, valueId + ".value");
        V v = (V) xstream.fromXML(file);
        return v;
    }

    @Override
    public <V> Thing<V> saveThing(Thing<V> t) {

        if ( Strings.isNullOrEmpty(t.getId()) ) {
            t.setId(UUID.randomUUID().toString());
        }

        File thingFile = getPath(t).toFile();
        thingFile.getParentFile().mkdirs();
        FileWriter writer = null;
        try {
            writer = new FileWriter(thingFile);
        } catch (IOException e) {
            throw new ThingRuntimeException("Could not create writer for file: " + thingFile.getAbsolutePath(), e);
        }
        xstream.toXML(t, writer);

        allThingsCache = null;

        return t;
    }

    public Object saveValue(Optional valueId, Object value) {

        FileWriter writer = null;

        Object vId = null;

        if ( valueId.isPresent() ) {
            vId = valueId.get();
        } else {
            vId = UUID.randomUUID().toString();
        }
        File file = getValueFile(value, (String) vId);
        file.getParentFile().mkdirs();

        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            throw new ThingRuntimeException("Could not write value.", e);
        }
        xstream.toXML(value, writer);

        return vId;
    }
}
