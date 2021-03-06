/**
 * This class is generated by jOOQ
 */
package nesi.jobs.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DailyRecord implements java.io.Serializable {

	private static final long serialVersionUID = 1807234468;

	private java.lang.String   username;
	private java.lang.String   projectcode;
	private java.sql.Timestamp day;
	private java.lang.Long     numCpusRunning;
	private java.lang.Long     numCpusWaiting;
	private java.lang.Long     numJobsRunning;
	private java.lang.Long     numJobsWaiting;
	private java.lang.Long     usageRunning;
	private java.lang.Long     usageWaiting;

	public DailyRecord() {}

	public DailyRecord(
		java.lang.String   username,
		java.lang.String   projectcode,
		java.sql.Timestamp day,
		java.lang.Long     numCpusRunning,
		java.lang.Long     numCpusWaiting,
		java.lang.Long     numJobsRunning,
		java.lang.Long     numJobsWaiting,
		java.lang.Long     usageRunning,
		java.lang.Long     usageWaiting
	) {
		this.username = username;
		this.projectcode = projectcode;
		this.day = day;
		this.numCpusRunning = numCpusRunning;
		this.numCpusWaiting = numCpusWaiting;
		this.numJobsRunning = numJobsRunning;
		this.numJobsWaiting = numJobsWaiting;
		this.usageRunning = usageRunning;
		this.usageWaiting = usageWaiting;
	}

	public java.lang.String getUsername() {
		return this.username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	public java.lang.String getProjectcode() {
		return this.projectcode;
	}

	public void setProjectcode(java.lang.String projectcode) {
		this.projectcode = projectcode;
	}

	public java.sql.Timestamp getDay() {
		return this.day;
	}

	public void setDay(java.sql.Timestamp day) {
		this.day = day;
	}

	public java.lang.Long getNumCpusRunning() {
		return this.numCpusRunning;
	}

	public void setNumCpusRunning(java.lang.Long numCpusRunning) {
		this.numCpusRunning = numCpusRunning;
	}

	public java.lang.Long getNumCpusWaiting() {
		return this.numCpusWaiting;
	}

	public void setNumCpusWaiting(java.lang.Long numCpusWaiting) {
		this.numCpusWaiting = numCpusWaiting;
	}

	public java.lang.Long getNumJobsRunning() {
		return this.numJobsRunning;
	}

	public void setNumJobsRunning(java.lang.Long numJobsRunning) {
		this.numJobsRunning = numJobsRunning;
	}

	public java.lang.Long getNumJobsWaiting() {
		return this.numJobsWaiting;
	}

	public void setNumJobsWaiting(java.lang.Long numJobsWaiting) {
		this.numJobsWaiting = numJobsWaiting;
	}

	public java.lang.Long getUsageRunning() {
		return this.usageRunning;
	}

	public void setUsageRunning(java.lang.Long usageRunning) {
		this.usageRunning = usageRunning;
	}

	public java.lang.Long getUsageWaiting() {
		return this.usageWaiting;
	}

	public void setUsageWaiting(java.lang.Long usageWaiting) {
		this.usageWaiting = usageWaiting;
	}
}
