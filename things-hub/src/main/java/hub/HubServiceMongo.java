package hub;

import hub.config.mongo.HubConfigMongo;
import org.springframework.boot.SpringApplication;

/**
 * Project: hub
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/03/14
 * Time: 10:56 AM
 */
public class HubServiceMongo {


    public static void main(String[] args) throws Exception {

        SpringApplication.run(HubConfigMongo.class);

    }


}
