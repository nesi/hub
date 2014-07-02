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
public class DimMachineRecord extends org.jooq.impl.UpdatableRecordImpl<nesi.jobs.tables.records.DimMachineRecord> implements org.jooq.Record7<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer> {

	private static final long serialVersionUID = -650920223;

	/**
	 * Setter for <code>public.dim_machine.dim_machine_id</code>.
	 */
	public void setDimMachineId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.dim_machine.dim_machine_id</code>.
	 */
	public java.lang.Integer getDimMachineId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.dim_machine.architecture</code>.
	 */
	public void setArchitecture(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.dim_machine.architecture</code>.
	 */
	public java.lang.String getArchitecture() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>public.dim_machine.os</code>.
	 */
	public void setOs(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.dim_machine.os</code>.
	 */
	public java.lang.String getOs() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>public.dim_machine.name</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.dim_machine.name</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>public.dim_machine.site</code>.
	 */
	public void setSite(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.dim_machine.site</code>.
	 */
	public java.lang.String getSite() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>public.dim_machine.machine_factor</code>.
	 */
	public void setMachineFactor(java.lang.Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.dim_machine.machine_factor</code>.
	 */
	public java.lang.Integer getMachineFactor() {
		return (java.lang.Integer) getValue(5);
	}

	/**
	 * Setter for <code>public.dim_machine.cores_per_node</code>.
	 */
	public void setCoresPerNode(java.lang.Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>public.dim_machine.cores_per_node</code>.
	 */
	public java.lang.Integer getCoresPerNode() {
		return (java.lang.Integer) getValue(6);
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
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer> valuesRow() {
		return (org.jooq.Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return nesi.jobs.tables.DimMachine.DIM_MACHINE.DIM_MACHINE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return nesi.jobs.tables.DimMachine.DIM_MACHINE.ARCHITECTURE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return nesi.jobs.tables.DimMachine.DIM_MACHINE.OS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return nesi.jobs.tables.DimMachine.DIM_MACHINE.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return nesi.jobs.tables.DimMachine.DIM_MACHINE.SITE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field6() {
		return nesi.jobs.tables.DimMachine.DIM_MACHINE.MACHINE_FACTOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field7() {
		return nesi.jobs.tables.DimMachine.DIM_MACHINE.CORES_PER_NODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getDimMachineId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getArchitecture();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getOs();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getSite();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value6() {
		return getMachineFactor();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value7() {
		return getCoresPerNode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DimMachineRecord value1(java.lang.Integer value) {
		setDimMachineId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DimMachineRecord value2(java.lang.String value) {
		setArchitecture(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DimMachineRecord value3(java.lang.String value) {
		setOs(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DimMachineRecord value4(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DimMachineRecord value5(java.lang.String value) {
		setSite(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DimMachineRecord value6(java.lang.Integer value) {
		setMachineFactor(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DimMachineRecord value7(java.lang.Integer value) {
		setCoresPerNode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DimMachineRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.String value4, java.lang.String value5, java.lang.Integer value6, java.lang.Integer value7) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DimMachineRecord
	 */
	public DimMachineRecord() {
		super(nesi.jobs.tables.DimMachine.DIM_MACHINE);
	}

	/**
	 * Create a detached, initialised DimMachineRecord
	 */
	public DimMachineRecord(java.lang.Integer dimMachineId, java.lang.String architecture, java.lang.String os, java.lang.String name, java.lang.String site, java.lang.Integer machineFactor, java.lang.Integer coresPerNode) {
		super(nesi.jobs.tables.DimMachine.DIM_MACHINE);

		setValue(0, dimMachineId);
		setValue(1, architecture);
		setValue(2, os);
		setValue(3, name);
		setValue(4, site);
		setValue(5, machineFactor);
		setValue(6, coresPerNode);
	}
}
