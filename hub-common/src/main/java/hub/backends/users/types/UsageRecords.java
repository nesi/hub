package hub.backends.users.types;

import com.google.common.collect.Sets;
import things.model.types.Value;

import java.util.SortedSet;

/**
 * Created by markus on 10/07/14.
 */
@Value(typeName = "usagerecords")
public class UsageRecords {

    private SortedSet<UsageRecord> usageRecordRecords = Sets.newTreeSet();

    private String username;

    public UsageRecords(SortedSet<UsageRecord> records) {
        this.usageRecordRecords = records;
    }

    public void addUsageRecords(UsageRecords jobHistoryForUsernameMonthly) {
        this.usageRecordRecords.addAll(jobHistoryForUsernameMonthly.getUsageRecordRecords());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UsageRecords() {
    }

    public SortedSet<UsageRecord> getUsageRecordRecords() {
        return usageRecordRecords;
    }

    public void setUsageRecordRecords(SortedSet<UsageRecord> usageRecordRecords) {
        this.usageRecordRecords = usageRecordRecords;
    }


    public void addRecord(UsageRecord rec) {
        getUsageRecordRecords().add(rec);
    }
}
