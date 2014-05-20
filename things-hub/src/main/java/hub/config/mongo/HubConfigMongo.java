package hub.config.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import hub.actions.UserUtils;
import hub.queries.users.PanAuditQuery;
import hub.queries.users.UserQuery;
import hub.queries.jobs.JobsQuery;
import hub.readers.UserReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import things.config.ThingQueries;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.config.mongo.MongoConfig;
import things.mongo.MongoConnector;
import things.thing.ThingControl;
import things.types.AnnotationTypeFactory;
import things.types.ThingType;
import things.types.TypeRegistry;
import things.utils.json.ThingsObjectMapper;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 11/05/14
 * Time: 2:16 PM
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@PropertySource("classpath:pan_audit.properties")
@PropertySource("classpath:projectdb.properties")
@PropertySource("classpath:sshJobLister.properties")
@PropertySource(value = "file:/etc/hub/hub.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${HOME}/.hub/hub.properties", ignoreResourceNotFound = true)
@ComponentScan({"hub.config.connectors", "things.thing", "things.view.rest", "things.config.jetm"})
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class HubConfigMongo extends MongoConfig {


    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return "research-hub";
    }

    @Bean
    public MongoConnector mongoConnector() throws Exception {
        MongoConnector mc = new MongoConnector(mongoTemplate());
        return mc;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ThingsObjectMapper tom = new ThingsObjectMapper();
        return tom;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new JettyEmbeddedServletContainerFactory();
    }

    @Bean
    public ThingControl thingControl() throws Exception {
        ThingControl tc = new ThingControl();
        return tc;
    }

    @Bean
    public TypeRegistry typeRegistry() {
        TypeRegistry tr = new TypeRegistry();
        for ( ThingType tt : AnnotationTypeFactory.getAllTypes() ) {
            tr.addType(tt);
        }
        return tr;
    }



    @Bean
    public ThingReaders thingReaders() throws Exception {

        ThingReaders tr = new ThingReaders();
        tr.addReader("person/*", mongoConnector());
        tr.addReader("typeClass/*", mongoConnector());
        tr.addReader("role/*", mongoConnector());
        tr.addReader("username/*", mongoConnector());
        tr.addReader("user/*", userReader());
        return tr;
    }

    @Bean
    public ThingWriters thingWriters() throws Exception {

        ThingWriters tw = new ThingWriters();
        tw.addWriter("person/*", mongoConnector());
        tw.addWriter("typeClass/*", mongoConnector());
        tw.addWriter("role/*", mongoConnector());
        tw.addWriter("username/*", mongoConnector());
        return tw;
    }

    @Bean
    public UserReader userReader() {
        return new UserReader();
    }

    @Bean
    public UserUtils userUtils() {
        return new UserUtils();
    }

    @Bean(name = "valueValidator")
    public Validator validator() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }
}
