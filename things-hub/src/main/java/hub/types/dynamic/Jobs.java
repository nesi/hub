package hub.types.dynamic;

import things.model.types.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 28/03/14
 * Time: 2:32 PM
 */
@Value(typeName = "jobs")
public class Jobs {


    private List<JobStatus> jobs;
    private Instant timestamp;
    private String username;
    private String site;

    public Jobs(List<JobStatus> jobs, String username, String site) {
        this.jobs = jobs;
        timestamp = Instant.now();
        this.username = username;
        this.site = site;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<JobStatus> getJobs() {
        return jobs;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setDate(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setJobs(List<JobStatus> jobs) {
        this.jobs = jobs;
    }
}
