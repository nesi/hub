package hub;

//import hub.actions.UserImporter;
import hub.config.jpa.HubConfigJpa;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import things.exceptions.ThingException;
import things.exceptions.ValueException;

/**
 * Project: hub
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/03/14
 * Time: 10:56 AM
 */
public class PsqlApplication {


    public static void main2(String[] args) throws ValueException, ThingException, InterruptedException {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(HubConfigJpa.class);


//        JobHistoryQuery query = (JobHistoryQuery) context.getBean("jobHistoryQuery");

//        query.getJobs();


//        UserImporter ui = (UserImporter) context.getBean("userImporter");
//
//        ui.execute(null, null, null);

    }


}
