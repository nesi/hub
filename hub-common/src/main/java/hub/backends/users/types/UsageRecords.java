package hub.backends.users.types;

import com.google.common.collect.Sets;
import things.model.types.Value;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by markus on 10/07/14.
 */
@Value(typeName = "usagerecords")
public class UsageRecords {

    private SortedSet<Usage> usageRecords = Sets.newTreeSet();

    private String username;

    public UsageRecords(SortedSet<Usage> records) {
        this.usageRecords = records;
    }

    public void addUsageRecords(UsageRecords jobHistoryForUsernameMonthly) {
        this.usageRecords.addAll(jobHistoryForUsernameMonthly.getUsageRecords());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UsageRecords() {
    }

    public SortedSet<Usage> getUsageRecords() {
        return usageRecords;
    }

    public void setUsageRecords(SortedSet<Usage> usageRecords) {
        this.usageRecords = usageRecords;
    }


    public void addRecord(Usage rec) {
        getUsageRecords().add(rec);
    }
}
