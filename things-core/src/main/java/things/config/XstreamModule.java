package things.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import things.connectors.xstream.XstreamConnector;
import things.thing.ThingControl;
import things.thing.ThingReaders;
import things.thing.ThingWriters;

import java.io.File;

/**
 * @author: Markus Binsteiner
 */
public class XstreamModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ThingControl.class);
        bind(File.class).annotatedWith(Names.named("thingsFolder")).toInstance(new File("/home/markus/things"));
        bind(File.class).annotatedWith(Names.named("valuesFolder")).toInstance(new File("/home/markus/values"));
        bind(String.class).annotatedWith(Names.named("readerName")).toInstance("defaultReader");
        bind(XstreamConnector.class);
    }

    @Provides
    ThingReaders thingReaders(XstreamConnector xstreamConnector) {
        ThingReaders tr = new ThingReaders();
        tr.addReader("person/*", xstreamConnector);
        tr.addReader("role/*", xstreamConnector);
        tr.addReader("address/*", xstreamConnector);
        return tr;
    }

    @Provides
    ThingWriters thingWriters(XstreamConnector xstreamConnector) {
        ThingWriters tw = new ThingWriters();
        tw.addWriter("person/*", xstreamConnector);
        tw.addWriter("role/*", xstreamConnector);
        tw.addWriter("address/*", xstreamConnector);
        return tw;
    }
    
}
