package things.connectors.xstream;

import com.google.common.base.Strings;
import com.thoughtworks.xstream.XStream;
import rx.Observable;
import rx.Subscriber;
import things.exceptions.ThingRuntimeException;
import things.thing.AbstractThingReader;
import things.thing.Thing;
import things.thing.ThingReader;
import things.thing.ThingWriter;
import things.types.TypeRegistry;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Markus Binsteiner
 */
public class XstreamConnector extends AbstractThingReader implements ThingReader, ThingWriter {

    private final XStream xstream = new XStream();
    private final File thingsFolder;
    private final File valuesFolder;

    @Inject
    public XstreamConnector(@Named("thingsFolder") File thingsFolder, @Named("valuesFolder") File valuesFolder) {
        this.thingsFolder = thingsFolder;
        this.thingsFolder.mkdirs();
        this.valuesFolder = valuesFolder;
        this.valuesFolder.mkdirs();
        this.typeRegistry = typeRegistry;
        xstream.processAnnotations(Thing.class);
    }


    private File getTypeFolder(String type) {
        File typeFolder = new File(valuesFolder, type);
        return typeFolder;
    }

    private File getValueFile(Object value, String id) {

        String type = typeRegistry.getType(value);
        File folder = getTypeFolder(type);

        return new File(folder, id+".value");
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

    private Path getPath(String type, String key, String id) {
        Path path = FileSystems.getDefault().getPath(thingsFolder.getAbsolutePath(), type, key + "_id_" + id + ".thing");
        return path;
    }

    private Path getPath(Thing t) {
        return getPath(t.getThingType(), t.getKey(), t.getId());
    }

    @Override
    public Observable<? extends Thing<?>> findAllThings() {

        Observable<? extends Thing<?>> obs = Observable.create((Subscriber<? super Thing<?>> subscriber) -> {
            new Thread( () -> {
                try {
                    Files.walk(Paths.get(thingsFolder.toURI()))
                            .filter((path) -> path.toString().endsWith(".thing"))
                            .map(path -> assembleThing(path))
                            .forEach(t -> subscriber.onNext(t));

                    subscriber.onCompleted();

                } catch (IOException e) {
                    throw new ThingRuntimeException("Could not read thing files.", e);
                }
            } ).start();
        });

        return obs;
    }

    public <V> V readValue(Thing<V> t) {
        String type = t.getThingType();
        Object valueId = t.getValue();
        File typeFolder = getTypeFolder(type);
        File file = new File(typeFolder, valueId+".value");
        V v = (V) xstream.fromXML(file);
        return v;
    }


    private Thing<?> assembleThing(Path path) {
        return assembleThing(path.toFile());
    }

    private Thing<?> assembleThing(File file) {
        Thing t = (Thing) xstream.fromXML(file);
        return t;
    }
}
