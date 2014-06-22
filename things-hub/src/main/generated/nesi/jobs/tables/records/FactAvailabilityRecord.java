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
public class FactAvailabilityRecord extends org.jooq.impl.UpdatableRecordImpl<nesi.jobs.tables.records.FactAvailabilityRecord> implements org.jooq.Record6<java.lang.Integer, java.sql.Date, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer> {

	private static final long serialVersionUID = 2037572459;

	/**
	 * Setter for <code>public.fact_availability.fact_availability_id</code>.
	 */
	public void setFactAvailabilityId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.fact_availability.fact_availability_id</code>.
	 */
	public java.lang.Integer getFactAvailabilityId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.fact_availability.date_iso</code>.
	 */
	public void setDateIso(java.sql.Date value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.fact_availability.date_iso</code>.
	 */
	public java.sql.Date getDateIso() {
		return (java.sql.Date) getValue(1);
	}

	/**
	 * Setter for <code>public.fact_availability.dim_machine_id</code>.
	 */
	public void setDimMachineId(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.fact_availability.dim_machine_id</code>.
	 */
	public java.lang.Integer getDimMachineId() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>public.fact_availability.core_seconds_total</code>.
	 */
	public void setCoreSecondsTotal(java.lang.Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.fact_availability.core_seconds_total</code>.
	 */
	public java.lang.Integer getCoreSecondsTotal() {
		return (java.lang.Integer) getValue(3);
	}

	/**
	 * Setter for <code>public.fact_availability.unsked</code>.
	 */
	public void setUnsked(java.lang.Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.fact_availability.unsked</code>.
	 */
	public java.lang.Integer getUnsked() {
		return (java.lang.Integer) getValue(4);
	}

	/**
	 * Setter for <code>public.fact_availability.sked</code>.
	 */
	public void setSked(java.lang.Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.fact_availability.sked</code>.
	 */
	public java.lang.Integer getSked() {
		return (java.lang.Integer) getValue(5);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.sql.Date, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.sql.Date, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY.FACT_AVAILABILITY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Date> field2() {
		return nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY.DATE_ISO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY.DIM_MACHINE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field4() {
		return nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY.CORE_SECONDS_TOTAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field5() {
		return nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY.UNSKED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field6() {
		return nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY.SKED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getFactAvailabilityId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Date value2() {
		return getDateIso();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getDimMachineId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value4() {
		return getCoreSecondsTotal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value5() {
		return getUnsked();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value6() {
		return getSked();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FactAvailabilityRecord value1(java.lang.Integer value) {
		setFactAvailabilityId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FactAvailabilityRecord value2(java.sql.Date value) {
		setDateIso(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FactAvailabilityRecord value3(java.lang.Integer value) {
		setDimMachineId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FactAvailabilityRecord value4(java.lang.Integer value) {
		setCoreSecondsTotal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FactAvailabilityRecord value5(java.lang.Integer value) {
		setUnsked(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FactAvailabilityRecord value6(java.lang.Integer value) {
		setSked(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FactAvailabilityRecord values(java.lang.Integer value1, java.sql.Date value2, java.lang.Integer value3, java.lang.Integer value4, java.lang.Integer value5, java.lang.Integer value6) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached FactAvailabilityRecord
	 */
	public FactAvailabilityRecord() {
		super(nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY);
	}

	/**
	 * Create a detached, initialised FactAvailabilityRecord
	 */
	public FactAvailabilityRecord(java.lang.Integer factAvailabilityId, java.sql.Date dateIso, java.lang.Integer dimMachineId, java.lang.Integer coreSecondsTotal, java.lang.Integer unsked, java.lang.Integer sked) {
		super(nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY);

		setValue(0, factAvailabilityId);
		setValue(1, dateIso);
		setValue(2, dimMachineId);
		setValue(3, coreSecondsTotal);
		setValue(4, unsked);
		setValue(5, sked);
	}
}