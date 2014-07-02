/**
 * This class is generated by jOOQ
 */
package nesi.jobs.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MonthlyRecordRecord extends org.jooq.impl.UpdatableRecordImpl<nesi.jobs.tables.records.MonthlyRecordRecord> implements org.jooq.Record9<java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> {

	private static final long serialVersionUID = 1476994633;

	/**
	 * Setter for <code>public.monthly_record.username</code>.
	 */
	public void setUsername(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.monthly_record.username</code>.
	 */
	public java.lang.String getUsername() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>public.monthly_record.projectCode</code>.
	 */
	public void setProjectcode(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.monthly_record.projectCode</code>.
	 */
	public java.lang.String getProjectcode() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>public.monthly_record.month</code>.
	 */
	public void setMonth(java.sql.Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.monthly_record.month</code>.
	 */
	public java.sql.Timestamp getMonth() {
		return (java.sql.Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>public.monthly_record.num_cpus_running</code>.
	 */
	public void setNumCpusRunning(java.lang.Long value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.monthly_record.num_cpus_running</code>.
	 */
	public java.lang.Long getNumCpusRunning() {
		return (java.lang.Long) getValue(3);
	}

	/**
	 * Setter for <code>public.monthly_record.num_cpus_waiting</code>.
	 */
	public void setNumCpusWaiting(java.lang.Long value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.monthly_record.num_cpus_waiting</code>.
	 */
	public java.lang.Long getNumCpusWaiting() {
		return (java.lang.Long) getValue(4);
	}

	/**
	 * Setter for <code>public.monthly_record.num_jobs_running</code>.
	 */
	public void setNumJobsRunning(java.lang.Long value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.monthly_record.num_jobs_running</code>.
	 */
	public java.lang.Long getNumJobsRunning() {
		return (java.lang.Long) getValue(5);
	}

	/**
	 * Setter for <code>public.monthly_record.num_jobs_waiting</code>.
	 */
	public void setNumJobsWaiting(java.lang.Long value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>public.monthly_record.num_jobs_waiting</code>.
	 */
	public java.lang.Long getNumJobsWaiting() {
		return (java.lang.Long) getValue(6);
	}

	/**
	 * Setter for <code>public.monthly_record.usage_running</code>.
	 */
	public void setUsageRunning(java.lang.Long value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>public.monthly_record.usage_running</code>.
	 */
	public java.lang.Long getUsageRunning() {
		return (java.lang.Long) getValue(7);
	}

	/**
	 * Setter for <code>public.monthly_record.usage_waiting</code>.
	 */
	public void setUsageWaiting(java.lang.Long value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>public.monthly_record.usage_waiting</code>.
	 */
	public java.lang.Long getUsageWaiting() {
		return (java.lang.Long) getValue(8);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record3<java.lang.String, java.lang.String, java.sql.Timestamp> key() {
		return (org.jooq.Record3) super.key();
	}

	// -------------------------------------------------------------------------
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> fieldsRow() {
		return (org.jooq.Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> valuesRow() {
		return (org.jooq.Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.PROJECTCODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.MONTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field4() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.NUM_CPUS_RUNNING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field5() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.NUM_CPUS_WAITING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field6() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.NUM_JOBS_RUNNING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field7() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.NUM_JOBS_WAITING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field8() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.USAGE_RUNNING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field9() {
		return nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD.USAGE_WAITING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getProjectcode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value3() {
		return getMonth();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value4() {
		return getNumCpusRunning();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value5() {
		return getNumCpusWaiting();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value6() {
		return getNumJobsRunning();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value7() {
		return getNumJobsWaiting();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value8() {
		return getUsageRunning();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value9() {
		return getUsageWaiting();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value1(java.lang.String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value2(java.lang.String value) {
		setProjectcode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value3(java.sql.Timestamp value) {
		setMonth(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value4(java.lang.Long value) {
		setNumCpusRunning(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value5(java.lang.Long value) {
		setNumCpusWaiting(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value6(java.lang.Long value) {
		setNumJobsRunning(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value7(java.lang.Long value) {
		setNumJobsWaiting(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value8(java.lang.Long value) {
		setUsageRunning(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord value9(java.lang.Long value) {
		setUsageWaiting(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MonthlyRecordRecord values(java.lang.String value1, java.lang.String value2, java.sql.Timestamp value3, java.lang.Long value4, java.lang.Long value5, java.lang.Long value6, java.lang.Long value7, java.lang.Long value8, java.lang.Long value9) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached MonthlyRecordRecord
	 */
	public MonthlyRecordRecord() {
		super(nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD);
	}

	/**
	 * Create a detached, initialised MonthlyRecordRecord
	 */
	public MonthlyRecordRecord(java.lang.String username, java.lang.String projectcode, java.sql.Timestamp month, java.lang.Long numCpusRunning, java.lang.Long numCpusWaiting, java.lang.Long numJobsRunning, java.lang.Long numJobsWaiting, java.lang.Long usageRunning, java.lang.Long usageWaiting) {
		super(nesi.jobs.tables.MonthlyRecord.MONTHLY_RECORD);

		setValue(0, username);
		setValue(1, projectcode);
		setValue(2, month);
		setValue(3, numCpusRunning);
		setValue(4, numCpusWaiting);
		setValue(5, numJobsRunning);
		setValue(6, numJobsWaiting);
		setValue(7, usageRunning);
		setValue(8, usageWaiting);
	}
}
