package things.jpa;

import org.hibernate.transform.ResultTransformer;

import java.util.List;

/**
 * Created by markus on 21/05/14.
 */
public class ThingResultTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        return null;
    }

    @Override
    public List transformList(List collection) {
        return null;
    }
}
