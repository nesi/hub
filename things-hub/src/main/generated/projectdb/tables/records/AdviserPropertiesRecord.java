/**
 * This class is generated by jOOQ
 */
package projectdb.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AdviserPropertiesRecord extends org.jooq.impl.UpdatableRecordImpl<projectdb.tables.records.AdviserPropertiesRecord> implements org.jooq.Record6<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.sql.Timestamp> {

	private static final long serialVersionUID = 1442918658;

	/**
	 * Setter for <code>projectdb.adviser_properties.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>projectdb.adviser_properties.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>projectdb.adviser_properties.adviserId</code>.
	 */
	public void setAdviserid(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>projectdb.adviser_properties.adviserId</code>.
	 */
	public java.lang.Integer getAdviserid() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>projectdb.adviser_properties.siteId</code>.
	 */
	public void setSiteid(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>projectdb.adviser_properties.siteId</code>.
	 */
	public java.lang.Integer getSiteid() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>projectdb.adviser_properties.propname</code>.
	 */
	public void setPropname(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>projectdb.adviser_properties.propname</code>.
	 */
	public java.lang.String getPropname() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>projectdb.adviser_properties.propvalue</code>.
	 */
	public void setPropvalue(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>projectdb.adviser_properties.propvalue</code>.
	 */
	public java.lang.String getPropvalue() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>projectdb.adviser_properties.timestamp</code>.
	 */
	public void setTimestamp(java.sql.Timestamp value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>projectdb.adviser_properties.timestamp</code>.
	 */
	public java.sql.Timestamp getTimestamp() {
		return (java.sql.Timestamp) getValue(5);
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
	public org.jooq.Row6<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.sql.Timestamp> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.sql.Timestamp> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return projectdb.tables.AdviserProperties.ADVISER_PROPERTIES.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return projectdb.tables.AdviserProperties.ADVISER_PROPERTIES.ADVISERID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return projectdb.tables.AdviserProperties.ADVISER_PROPERTIES.SITEID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return projectdb.tables.AdviserProperties.ADVISER_PROPERTIES.PROPNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return projectdb.tables.AdviserProperties.ADVISER_PROPERTIES.PROPVALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field6() {
		return projectdb.tables.AdviserProperties.ADVISER_PROPERTIES.TIMESTAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value2() {
		return getAdviserid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getSiteid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getPropname();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getPropvalue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value6() {
		return getTimestamp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdviserPropertiesRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdviserPropertiesRecord value2(java.lang.Integer value) {
		setAdviserid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdviserPropertiesRecord value3(java.lang.Integer value) {
		setSiteid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdviserPropertiesRecord value4(java.lang.String value) {
		setPropname(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdviserPropertiesRecord value5(java.lang.String value) {
		setPropvalue(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdviserPropertiesRecord value6(java.sql.Timestamp value) {
		setTimestamp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdviserPropertiesRecord values(java.lang.Integer value1, java.lang.Integer value2, java.lang.Integer value3, java.lang.String value4, java.lang.String value5, java.sql.Timestamp value6) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AdviserPropertiesRecord
	 */
	public AdviserPropertiesRecord() {
		super(projectdb.tables.AdviserProperties.ADVISER_PROPERTIES);
	}

	/**
	 * Create a detached, initialised AdviserPropertiesRecord
	 */
	public AdviserPropertiesRecord(java.lang.Integer id, java.lang.Integer adviserid, java.lang.Integer siteid, java.lang.String propname, java.lang.String propvalue, java.sql.Timestamp timestamp) {
		super(projectdb.tables.AdviserProperties.ADVISER_PROPERTIES);

		setValue(0, id);
		setValue(1, adviserid);
		setValue(2, siteid);
		setValue(3, propname);
		setValue(4, propvalue);
		setValue(5, timestamp);
	}
}
