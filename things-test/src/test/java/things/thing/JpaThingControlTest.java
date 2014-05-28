package things.thing;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import things.config.jpa.JpaTestConfig;
import things.jpa.JpaConnector;

import javax.inject.Inject;

/**
 * Created by markus on 21/05/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = {JpaTestConfig.class})
public class JpaThingControlTest extends BaseThingControlTest {

    @Inject
    private JpaConnector connector;


    @Override
    public void deleteAllThings() {
        connector.deleteAll();
    }
}
