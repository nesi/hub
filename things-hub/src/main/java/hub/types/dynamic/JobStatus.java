package hub.types.dynamic;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;
import things.model.types.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 28/03/14
 * Time: 12:49 PM
 */
@Value(typeName = "jobstatus")
public class JobStatus implements Comparable<JobStatus>, Serializable {

    public enum STATE {
        cancelled("CA"),
        checkpointing("CK"),
        completed("C"),
        complete_pending("CP"),
        deferred("D"),
        idle("I"),
        not_queued("NQ"),
        not_run("NR"),
        pending("P"),
        preempted("E"),
        preempt_pending("EP"),
        rejected("X"),
        reject_pending("XP"),
        removed("RM"),
        remove_pending("RP"),
        resume_pending("MP"),
        running("R"),
        starting("ST"),
        system_hold("S"),
        terminated("TX"),
        user_system_hold("HS"),
        user_hold("H"),
        vacated("V"),
        vacate_pending("VP"),
        unknown("");

        public static STATE get(String abrev) {
            for ( STATE state : STATE.values() ) {
                if ( state.abrevs.contains(abrev) ) {
                    return state;
                }
            }
            return unknown;
        }


        private Set<String> abrevs = Sets.newHashSet();

        STATE(String abrevs) {
            this.abrevs = Sets.newHashSet(abrevs.split(","));
        }
    }

    private String dispatchDate;
    private String host;
    private String jobid;
    private String jobname;
    private STATE jobstatus;
    private String queueDate;
    private String username;

    public int compareTo(JobStatus other) {

        return ComparisonChain.start().compare(getUsername(), other.getUsername())
                .compare(getJobname(), other.getJobname())
                .compare(getJobid(), other.getJobid()).result();

    }

    public String getDispatchDate() {
        return dispatchDate;
    }

    public String getHost() {
        return host;
    }

    public String getJobid() {
        return jobid;
    }

    public String getJobname() {
        return jobname;
    }

    public STATE getJobstatus() {
        return jobstatus;
    }

    public String getQueueDate() {
        return queueDate;
    }

    public String getUsername() {
        return username;
    }

    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public void setJobstatus(STATE jobstatus) {
        this.jobstatus = jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = STATE.get(jobstatus);
    }

    public void setQueueDate(String queueDate) {
        this.queueDate = queueDate;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
