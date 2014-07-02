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
public class ResearchoutputRecord extends org.jooq.impl.UpdatableRecordImpl<projectdb.tables.records.ResearchoutputRecord> implements org.jooq.Record9<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer> {

	private static final long serialVersionUID = 616281350;

	/**
	 * Setter for <code>projectdb.researchoutput.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>projectdb.researchoutput.projectId</code>.
	 */
	public void setProjectid(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.projectId</code>.
	 */
	public java.lang.Integer getProjectid() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>projectdb.researchoutput.typeId</code>.
	 */
	public void setTypeid(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.typeId</code>.
	 */
	public java.lang.Integer getTypeid() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>projectdb.researchoutput.description</code>.
	 */
	public void setDescription(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>projectdb.researchoutput.link</code>.
	 */
	public void setLink(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.link</code>.
	 */
	public java.lang.String getLink() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>projectdb.researchoutput.DOI</code>.
	 */
	public void setDoi(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.DOI</code>.
	 */
	public java.lang.String getDoi() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>projectdb.researchoutput.date</code>.
	 */
	public void setDate(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.date</code>.
	 */
	public java.lang.String getDate() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>projectdb.researchoutput.adviserId</code>.
	 */
	public void setAdviserid(java.lang.Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.adviserId</code>.
	 */
	public java.lang.Integer getAdviserid() {
		return (java.lang.Integer) getValue(7);
	}

	/**
	 * Setter for <code>projectdb.researchoutput.researcherId</code>.
	 */
	public void setResearcherid(java.lang.Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>projectdb.researchoutput.researcherId</code>.
	 */
	public java.lang.Integer getResearcherid() {
		return (java.lang.Integer) getValue(8);
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
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer> valuesRow() {
		return (org.jooq.Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.PROJECTID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.TYPEID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.LINK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.DOI;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field8() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.ADVISERID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field9() {
		return projectdb.tables.Researchoutput.RESEARCHOUTPUT.RESEARCHERID;
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
		return getProjectid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getTypeid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getLink();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getDoi();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value8() {
		return getAdviserid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value9() {
		return getResearcherid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value2(java.lang.Integer value) {
		setProjectid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value3(java.lang.Integer value) {
		setTypeid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value4(java.lang.String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value5(java.lang.String value) {
		setLink(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value6(java.lang.String value) {
		setDoi(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value7(java.lang.String value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value8(java.lang.Integer value) {
		setAdviserid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord value9(java.lang.Integer value) {
		setResearcherid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResearchoutputRecord values(java.lang.Integer value1, java.lang.Integer value2, java.lang.Integer value3, java.lang.String value4, java.lang.String value5, java.lang.String value6, java.lang.String value7, java.lang.Integer value8, java.lang.Integer value9) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ResearchoutputRecord
	 */
	public ResearchoutputRecord() {
		super(projectdb.tables.Researchoutput.RESEARCHOUTPUT);
	}

	/**
	 * Create a detached, initialised ResearchoutputRecord
	 */
	public ResearchoutputRecord(java.lang.Integer id, java.lang.Integer projectid, java.lang.Integer typeid, java.lang.String description, java.lang.String link, java.lang.String doi, java.lang.String date, java.lang.Integer adviserid, java.lang.Integer researcherid) {
		super(projectdb.tables.Researchoutput.RESEARCHOUTPUT);

		setValue(0, id);
		setValue(1, projectid);
		setValue(2, typeid);
		setValue(3, description);
		setValue(4, link);
		setValue(5, doi);
		setValue(6, date);
		setValue(7, adviserid);
		setValue(8, researcherid);
	}
}