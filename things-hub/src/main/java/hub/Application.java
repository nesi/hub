package hub;

import hub.config.HubConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import things.exceptions.ThingException;
import things.exceptions.ValueException;
import things.thing.ThingControl;

/**
 * Project: hub
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/03/14
 * Time: 10:56 AM
 */
public class Application {


    public static void main(String[] args) throws ValueException, ThingException, InterruptedException {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(HubConfig.class);

        MongoOperations mo = (MongoOperations) context.getBean("mongoTemplate");
        final ThingControl tc = (ThingControl) context.getBean("thingControl");

        for (String s : context.getBeanDefinitionNames()) {
            System.out.println(s);
        }


    }


}
