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
public class AuditAdminRecord extends org.jooq.impl.TableRecordImpl<pan.auditdb.tables.records.AuditAdminRecord> implements org.jooq.Record1<java.lang.String> {

	private static final long serialVersionUID = 1769647479;

	/**
	 * Setter for <code>pandora_audit.audit_admin.tuakiriUniqueId</code>.
	 */
	public void setTuakiriuniqueid(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>pandora_audit.audit_admin.tuakiriUniqueId</code>.
	 */
	public java.lang.String getTuakiriuniqueid() {
		return (java.lang.String) getValue(0);
	}

	// -------------------------------------------------------------------------
	// Record1 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<java.lang.String> fieldsRow() {
		return (org.jooq.Row1) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<java.lang.String> valuesRow() {
		return (org.jooq.Row1) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return pan.auditdb.tables.AuditAdmin.AUDIT_ADMIN.TUAKIRIUNIQUEID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getTuakiriuniqueid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuditAdminRecord value1(java.lang.String value) {
		setTuakiriuniqueid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuditAdminRecord values(java.lang.String value1) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AuditAdminRecord
	 */
	public AuditAdminRecord() {
		super(pan.auditdb.tables.AuditAdmin.AUDIT_ADMIN);
	}

	/**
	 * Create a detached, initialised AuditAdminRecord
	 */
	public AuditAdminRecord(java.lang.String tuakiriuniqueid) {
		super(pan.auditdb.tables.AuditAdmin.AUDIT_ADMIN);

		setValue(0, tuakiriuniqueid);
	}
}
