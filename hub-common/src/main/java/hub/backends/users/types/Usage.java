package hub.backends.users.types;

import com.google.common.collect.ComparisonChain;
import things.model.types.Value;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by markus on 10/07/14.
 */
@Value(typeName = "usage")
public class Usage implements Comparable<Usage> {

    private String username;
    private String projectCode;
    private Instant startHour;
    private String timeUnit;

    private Long cpusRunning;
    private Long cpusWaiting;
    private Long jobsRunning;
    private Long jobsWaiting;
    private Long usageRunning;
    private Long usageWaiting;

    public Usage() {
    }

    @Override
    public int compareTo(Usage o) {
        return ComparisonChain.start()
                .compare(getUsername(), o.getUsername())
                .compare(getProjectCode(), o.getProjectCode())
                .compare(getTimeUnit(), o.getTimeUnit())
                .compare(getStartHour(), o.getStartHour())
                .result();
    }

    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Usage other = (Usage) obj;
            return Objects.equals(getTimeUnit(), other.getTimeUnit())
                    && Objects.equals(getUsername(), other.getUsername())
                    && Objects.equals(getProjectCode(), other.getProjectCode())
                    && Objects.equals(getStartHour(), other.getStartHour());
        } else {
            return false;
        }
    }


    public int hashCode() {
        return Objects.hash(getTimeUnit(), getUsername(), getProjectCode(), getStartHour());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Instant getStartHour() {
        return startHour;
    }

    public void setStartHour(Instant startHour) {
        this.startHour = startHour;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Long getCpusRunning() {
        return cpusRunning;
    }

    public void setCpusRunning(Long cpusRunning) {
        this.cpusRunning = cpusRunning;
    }

    public Long getCpusWaiting() {
        return cpusWaiting;
    }

    public void setCpusWaiting(Long cpusWaiting) {
        this.cpusWaiting = cpusWaiting;
    }

    public Long getJobsRunning() {
        return jobsRunning;
    }

    public void setJobsRunning(Long jobsRunning) {
        this.jobsRunning = jobsRunning;
    }

    public Long getJobsWaiting() {
        return jobsWaiting;
    }

    public void setJobsWaiting(Long jobsWaiting) {
        this.jobsWaiting = jobsWaiting;
    }

    public Long getUsageRunning() {
        return usageRunning;
    }

    public void setUsageRunning(Long usageRunning) {
        this.usageRunning = usageRunning;
    }

    public Long getUsageWaiting() {
        return usageWaiting;
    }

    public void setUsageWaiting(Long usageWaiting) {
        this.usageWaiting = usageWaiting;
    }
}
