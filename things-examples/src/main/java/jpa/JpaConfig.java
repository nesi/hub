package jpa;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import things.config.ThingActions;
import things.config.ThingQueries;
import things.config.ThingReaders;
import things.config.ThingWriters;
import things.jpa.JpaConnector;
import things.jpa.ValueRepositories;
import things.thing.ThingControl;
import things.types.AnnotationTypeFactory;
import things.types.ThingType;
import things.types.TypeRegistry;
import types.Address;
import types.Person;
import types.Role;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by markus on 20/05/14.
 */
@Configuration
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class, MongoTemplateAutoConfiguration.class})
@ComponentScan( {"things.thing"} )
@EnableJpaRepositories( basePackages = {"jpa", "things.jpa"} )
public class JpaConfig {

    @Bean
    public TypeRegistry typeRegistry() {
        TypeRegistry tr = new TypeRegistry();
        for ( ThingType tt : AnnotationTypeFactory.getAllTypes() ) {
            tr.addType(tt);
        }
        return tr;
    }

    @Bean
    JpaConnector defaultConnector() {
        JpaConnector con = new JpaConnector();
        return con;
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
        tr.addReader("person/*", defaultConnector());
        tr.addReader("role/*", defaultConnector());
        tr.addReader("address/*", defaultConnector());

        return tr;
    }

    @Bean
    public ThingWriters thingWriters() throws Exception {

        ThingWriters tw = new ThingWriters();
        tw.addWriter("person/*", defaultConnector());
        tw.addWriter("role/*", defaultConnector());
        tw.addWriter("address/*", defaultConnector());

        return tw;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("things.thing", "types");
        factory.setDataSource(dataSource());
        factory.setMappingResources("thing.hbm.xml");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    @Inject
    public ValueRepositories valueRepositories(TypeRegistry tr, PersonRepository pr, AddressRepository ar) {
        ValueRepositories vr = new ValueRepositories();
        vr.addRepository(tr.getType(Person.class), pr);
        vr.addRepository(tr.getType(Address.class), ar);
        return vr;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator(){
      return new HibernateExceptionTranslator();
    }

    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean(name = "valueValidator")
    public Validator validator() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }
}
