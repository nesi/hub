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
public class FactJobDaily implements java.io.Serializable {

	private static final long serialVersionUID = 1537049576;

	private java.lang.Integer  factJobDailyId;
	private java.lang.Integer  dimJobId;
	private java.lang.Integer  dimProjectId;
	private java.lang.Integer  dimMachineId;
	private java.lang.Integer  dimUserId;
	private java.sql.Timestamp reportDay;
	private java.lang.Double   periodCharge;

	public FactJobDaily() {}

	public FactJobDaily(
		java.lang.Integer  factJobDailyId,
		java.lang.Integer  dimJobId,
		java.lang.Integer  dimProjectId,
		java.lang.Integer  dimMachineId,
		java.lang.Integer  dimUserId,
		java.sql.Timestamp reportDay,
		java.lang.Double   periodCharge
	) {
		this.factJobDailyId = factJobDailyId;
		this.dimJobId = dimJobId;
		this.dimProjectId = dimProjectId;
		this.dimMachineId = dimMachineId;
		this.dimUserId = dimUserId;
		this.reportDay = reportDay;
		this.periodCharge = periodCharge;
	}

	public java.lang.Integer getFactJobDailyId() {
		return this.factJobDailyId;
	}

	public void setFactJobDailyId(java.lang.Integer factJobDailyId) {
		this.factJobDailyId = factJobDailyId;
	}

	public java.lang.Integer getDimJobId() {
		return this.dimJobId;
	}

	public void setDimJobId(java.lang.Integer dimJobId) {
		this.dimJobId = dimJobId;
	}

	public java.lang.Integer getDimProjectId() {
		return this.dimProjectId;
	}

	public void setDimProjectId(java.lang.Integer dimProjectId) {
		this.dimProjectId = dimProjectId;
	}

	public java.lang.Integer getDimMachineId() {
		return this.dimMachineId;
	}

	public void setDimMachineId(java.lang.Integer dimMachineId) {
		this.dimMachineId = dimMachineId;
	}

	public java.lang.Integer getDimUserId() {
		return this.dimUserId;
	}

	public void setDimUserId(java.lang.Integer dimUserId) {
		this.dimUserId = dimUserId;
	}

	public java.sql.Timestamp getReportDay() {
		return this.reportDay;
	}

	public void setReportDay(java.sql.Timestamp reportDay) {
		this.reportDay = reportDay;
	}

	public java.lang.Double getPeriodCharge() {
		return this.periodCharge;
	}

	public void setPeriodCharge(java.lang.Double periodCharge) {
		this.periodCharge = periodCharge;
	}
}
