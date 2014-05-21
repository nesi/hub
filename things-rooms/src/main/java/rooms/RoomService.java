package rooms;

import org.springframework.boot.SpringApplication;
import rooms.config.jpa.RoomConfigJpa;

/**
 * Project: hub
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/03/14
 * Time: 10:56 AM
 */
public class RoomService {


    public static void main(String[] args) throws Exception {

        //SpringApplication.run(RoomConfigMongo.class);
        SpringApplication.run(RoomConfigJpa.class);

    }


}
