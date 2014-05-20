package things.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import things.connectors.inMemory.InMemoryConnector;
import things.thing.ThingControl;
import things.types.AnnotationTypeFactory;
import things.types.ThingType;
import things.types.TypeRegistry;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by markus on 18/05/14.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan({"things.thing"})
public class TestConfigInMemory {


    @Bean
    InMemoryConnector connector() {

        return new InMemoryConnector();
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
    public ThingReaders thingReaders() throws Exception {

        ThingReaders tr = new ThingReaders();
        tr.addReader("noRestrictionsType/*", connector());
        tr.addReader("uniqueKeyType/*", connector());
        return tr;
    }

    @Bean
    public ThingWriters thingWriters() throws Exception {

        ThingWriters tw = new ThingWriters();
        tw.addWriter("noRestrictionsType/*", connector());
        tw.addWriter("uniqueKeyType/*", connector());
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
