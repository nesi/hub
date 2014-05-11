package rooms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import things.config.mongo.MongoConfig;
import things.mongo.MongoConnector;
import things.thing.ThingControl;
import things.thing.ThingReaders;
import things.thing.ThingUtils;
import things.thing.ThingWriters;
import things.utils.json.ThingsObjectMapper;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 11/05/14
 * Time: 2:16 PM
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("things.view.rest")
public class RoomConfig extends MongoConfig {



    @Override
    protected String getDatabaseName() {
        return "rooms";
    }



    @Bean
    public MongoConnector defaultConnector() throws Exception {
        MongoConnector mc = new MongoConnector("defaultReader", mongoTemplate());
        return mc;
    }

    @Bean
    public ThingReaders thingReaders() throws Exception {

        ThingReaders tr = new ThingReaders();
        tr.addReader("bridge/*", defaultConnector());
        tr.addReader("light/*", defaultConnector());
        tr.addReader("profile/*", defaultConnector());

        return tr;
    }

    @Bean
    public ThingWriters thingWriters() throws Exception {

        ThingWriters tw = new ThingWriters();
        tw.addWriter("bridge/*", defaultConnector());
        tw.addWriter("light/*", defaultConnector());
        tw.addWriter("profile/*", defaultConnector());

        return tw;
    }

    @Bean(name = "valueValidator")
    public Validator validator() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }


    @Bean
    public ThingControl thingControl() throws Exception {
        ThingControl tc = new ThingControl(thingReaders(), thingWriters(), validator());
        return tc;
    }

    @Bean
    ThingUtils thingUtils() throws Exception {
        ThingUtils tu = new ThingUtils(objectMapper());
        return tu;
    }

    @Bean
    public ObjectMapper objectMapper() throws Exception {
        ThingsObjectMapper tom = new ThingsObjectMapper(thingControl());
        return tom;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new JettyEmbeddedServletContainerFactory();
    }
}

