package things.thing;

import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import things.config.mongo.MongoTestConfig;
import things.mongo.MongoConnector;
import things.types.NoRestrictionsType;
import things.types.UniqueKeyType;

import javax.inject.Inject;

/**
 * Created by markus on 21/05/14.
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( loader = AnnotationConfigContextLoader.class,
        classes = {MongoTestConfig.class} )
public class MongoThingControlTest extends BaseThingControlTest {

    @Inject
    private MongoConnector connector;


    @Override
    public void deleteAllThings() {
        MongoTemplate mt = connector.getMongoTemplate();
        mt.dropCollection(Thing.class);
        mt.dropCollection(NoRestrictionsType.class);
        mt.dropCollection(UniqueKeyType.class);
    }
}
