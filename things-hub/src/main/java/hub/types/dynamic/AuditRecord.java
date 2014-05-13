package hub.types.dynamic;

import com.google.common.collect.Maps;
import things.model.types.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author: Markus Binsteiner
 */
@Value(typeName = "auditrecord")
public class AuditRecord implements Serializable {

    private final Map<String, BigDecimal> coreHours = Maps.newConcurrentMap();

    public AuditRecord() {
    }

    public Map<String, BigDecimal> getCoreHours() {
        return coreHours;
    }

    public void addJob(String name, BigDecimal corehours) {
        this.coreHours.put(name, corehours);
    }
}
