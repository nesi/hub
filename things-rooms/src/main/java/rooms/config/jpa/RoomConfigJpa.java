package rooms.config.jpa;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import rooms.actions.LightAction;
import rooms.actions.LightUtil;
import rooms.readers.LightStateReader;
import rooms.types.Bridge;
import rooms.types.Light;
import rooms.types.Profile;
import rooms.types.repositories.BridgeRepository;
import rooms.types.repositories.LightRepository;
import rooms.types.repositories.ProfileRepository;
import things.config.ThingActions;
import things.config.ThingQueries;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.config.mongo.MongoConfig;
import things.jpa.JpaConnector;
import things.jpa.ValueRepositories;
import things.jpa.ValueRepository;
import things.mongo.MongoConnector;
import things.thing.ActionManager;
import things.thing.DefaultActionManager;
import things.thing.ThingControl;
import things.types.AnnotationTypeFactory;
import things.types.ThingType;
import things.types.TypeRegistry;
import things.utils.json.ThingsObjectMapper;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 11/05/14
 * Time: 2:16 PM
 */
@Configuration
@ComponentScan({"things.thing", "things.view.rest"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"things.jpa", "rooms.types.repositories"})
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MongoTemplateAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class, MongoAutoConfiguration.class})
public class RoomConfigJpa {

    @Bean
    public ActionManager actionManager() {
        return new DefaultActionManager();
    }


    @Bean
    public LightAction lightAction() throws Exception {
        LightAction lc = new LightAction();
        return lc;
    }

    @Bean
    LightStateReader lightStateReader() {
        return new LightStateReader();
    }

    @Bean
    public LightUtil lightUtil() throws Exception {
        return new LightUtil();
    }

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ThingsObjectMapper tom = new ThingsObjectMapper();
        return tom;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }


    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new JettyEmbeddedServletContainerFactory();
    }

    @Bean
    public JpaConnector defaultConnector() {
        JpaConnector con = new JpaConnector();
        return con;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory());
        return transactionManager;
    }

    @Bean(name = "thingDataSource")
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("things.thing", "rooms.types");
        factory.setDataSource(dataSource());
        factory.setMappingResources("thing.hbm.xml");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    ThingActions thingActions() throws Exception {
        ThingActions ta = new ThingActions();
        ta.addAction("set_light", lightAction());
        ta.addAction("toggle", lightAction());
        ta.addAction("turn_on", lightAction());
        ta.addAction("turn_off", lightAction());
        ta.addAction("set", lightAction());
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
        tr.addReader("bridge/*", defaultConnector());
        tr.addReader("light/*", defaultConnector());
        tr.addReader("profile/*", defaultConnector());
        tr.addReader("lightState/*", lightStateReader());

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

    @Bean
    @Inject
    public ValueRepositories valueRepositories(TypeRegistry tr, BridgeRepository br, LightRepository lr, ProfileRepository pr) {

        ValueRepositories vr = new ValueRepositories();
        vr.addRepository(tr.getType(Bridge.class), br);
        vr.addRepository(tr.getType(Light.class), lr);
        vr.addRepository(tr.getType(Profile.class), pr);

        return vr;
    }
}
