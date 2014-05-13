package hub.types.dynamic;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import things.model.types.Value;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 28/03/14
 * Time: 2:32 PM
 */
@Value(typeName = "jobs")
public class Jobs implements Serializable {
    
    
    private Instant timestamp;
    private List<JobStatus> jobs;

    public Jobs(List<JobStatus> jobs) {
        this.jobs = jobs;
        timestamp = Instant.now();
    }

    public List<JobStatus> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobStatus> jobs) {
        this.jobs = jobs;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setDate(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
