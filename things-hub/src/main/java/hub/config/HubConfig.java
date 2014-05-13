package hub.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import things.config.mongo.MongoConfig;
import things.mongo.MongoConnector;
import things.thing.ThingActions;
import things.thing.ThingControl;
import things.thing.ThingReaders;
import things.thing.ThingWriters;

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
@ComponentScan({"things.thing", "things.view.rest"})
public class HubConfig extends MongoConfig {

    @Override
    protected String getDatabaseName() {
        return "research-hub";
    }

    @Bean
    public MongoConnector defaultConnector() throws Exception {
        MongoConnector mc = new MongoConnector(mongoTemplate());
        return mc;
    }

    @Bean
    public ThingReaders thingReaders() throws Exception {

        ThingReaders tr = new ThingReaders();

        return tr;
    }

    @Bean
    public ThingWriters thingWriters() throws Exception {

        ThingWriters tw = new ThingWriters();

        return tw;
    }


    @Bean
    ThingActions thingActions() throws Exception {
        ThingActions ta = new ThingActions();
        return ta;
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
        ThingControl tc = new ThingControl();
        return tc;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new JettyEmbeddedServletContainerFactory();
    }
}
