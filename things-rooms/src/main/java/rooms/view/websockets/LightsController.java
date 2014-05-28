package rooms.view.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import rooms.types.LightState;

/**
 * Created by markus on 27/05/14.
 */
@Controller
public class LightsController {


    @MessageMapping("/hello")
    @SendTo("/topic/light_change")
    public LightState stateChanged(LightState ls) {
        System.out.println("CHANGE: " + ls);
        return ls;
    }
}
