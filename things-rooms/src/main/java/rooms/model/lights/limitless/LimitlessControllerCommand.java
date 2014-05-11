package rooms.model.lights.limitless;

import java.util.List;
import java.util.Map;

/**
 * Project: rooms
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 6/02/14
 * Time: 9:24 PM
 */
public interface LimitlessControllerCommand {

    public List<byte[]> getCommand(Map<String, String> options);
}
