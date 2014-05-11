package things.config.mongo;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 11/05/14
 * Time: 4:59 PM
 */
@Configuration
public abstract class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public Mongo mongo() throws Exception {
        return new Mongo("localhost");
    }

    @Bean
    @Override
    public MongoDbFactory mongoDbFactory() throws Exception {
        //UserCredentials userCredentials = new UserCredentials("joe", "secret");
        return new SimpleMongoDbFactory(mongo(), getDatabaseName());
    }

}
