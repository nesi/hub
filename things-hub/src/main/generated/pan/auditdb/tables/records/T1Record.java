/**
 * This class is generated by jOOQ
 */
package pan.auditdb.tables.records;

/**
 * This class is generated by jOOQ.
 *
 * InnoDB free: 8458240 kB
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class T1Record extends org.jooq.impl.TableRecordImpl<pan.auditdb.tables.records.T1Record> implements org.jooq.Record2<java.lang.String, java.lang.String> {

	private static final long serialVersionUID = -38745933;

	/**
	 * Setter for <code>pandora_audit.t1.user</code>.
	 */
	public void setUser(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>pandora_audit.t1.user</code>.
	 */
	public java.lang.String getUser() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>pandora_audit.t1.account</code>.
	 */
	public void setAccount(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>pandora_audit.t1.account</code>.
	 */
	public java.lang.String getAccount() {
		return (java.lang.String) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return pan.auditdb.tables.T1.T1.USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return pan.auditdb.tables.T1.T1.ACCOUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getAccount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T1Record value1(java.lang.String value) {
		setUser(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T1Record value2(java.lang.String value) {
		setAccount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T1Record values(java.lang.String value1, java.lang.String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached T1Record
	 */
	public T1Record() {
		super(pan.auditdb.tables.T1.T1);
	}

	/**
	 * Create a detached, initialised T1Record
	 */
	public T1Record(java.lang.String user, java.lang.String account) {
		super(pan.auditdb.tables.T1.T1);

		setValue(0, user);
		setValue(1, account);
	}
}
