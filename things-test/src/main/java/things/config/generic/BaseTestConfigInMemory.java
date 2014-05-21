package things.config.generic;

import com.codahale.metrics.MetricRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import things.config.ThingActions;
import things.config.ThingQueries;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.thing.DefaultActionManager;
import things.thing.ThingControl;
import things.thing.ThingReader;
import things.thing.ThingWriter;
import things.types.AnnotationTypeFactory;
import things.types.ThingType;
import things.types.TypeRegistry;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by markus on 18/05/14.
 */
@Configuration
public class BaseTestConfigInMemory {

    @Bean
    DefaultActionManager actionManager() {
        return new DefaultActionManager();
    }

    @Bean
    MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }


    @Bean
    ThingActions thingActions() throws Exception {
        ThingActions ta = new ThingActions();
        return ta;
    }

    @Bean
    public ThingControl thingControl() throws Exception {
        ThingControl tc = new ThingControl();
        return tc;
    }

    @Bean
    public ThingQueries thingQueries() {
        ThingQueries tq = new ThingQueries();
        return tq;
    }

    @Bean
    @Inject
    public ThingReaders thingReaders(ThingReader reader) throws Exception {

        ThingReaders tr = new ThingReaders();
        tr.addReader("noRestrictionsType/*", reader);
        tr.addReader("uniqueKeyType/*", reader);
        return tr;
    }

    @Bean
    public ThingWriters thingWriters(ThingWriter writer) throws Exception {

        ThingWriters tw = new ThingWriters();
        tw.addWriter("noRestrictionsType/*", writer);
        tw.addWriter("uniqueKeyType/*", writer);
        return tw;
    }

    @Bean
    public TypeRegistry typeRegistry() {
        TypeRegistry tr = new TypeRegistry();
        for ( ThingType tt : AnnotationTypeFactory.getAllTypes() ) {
            tr.addType(tt);
        }
        return tr;
    }

    @Bean(name = "valueValidator")
    public Validator validator() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }
}
