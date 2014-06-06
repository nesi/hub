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

package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.connectors.xstream.XstreamConnector;
import things.thing.ThingControl;

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
