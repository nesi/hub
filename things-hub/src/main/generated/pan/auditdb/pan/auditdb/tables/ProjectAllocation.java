/**
 * This class is generated by jOOQ
 */
package pan.auditdb.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.1" },
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProjectAllocation extends org.jooq.impl.TableImpl<pan.auditdb.tables.records.ProjectAllocationRecord> {

	private static final long serialVersionUID = -608447079;

	/**
	 * The singleton instance of <code>pandora_audit.project_allocation</code>
	 */
	public static final pan.auditdb.tables.ProjectAllocation PROJECT_ALLOCATION = new pan.auditdb.tables.ProjectAllocation();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<pan.auditdb.tables.records.ProjectAllocationRecord> getRecordType() {
		return pan.auditdb.tables.records.ProjectAllocationRecord.class;
	}

	/**
	 * The column <code>pandora_audit.project_allocation.project_id</code>.
	 */
	public final org.jooq.TableField<pan.auditdb.tables.records.ProjectAllocationRecord, String> PROJECT_ID = createField("project_id", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>pandora_audit.project_allocation.project_start_date</code>.
	 */
	public final org.jooq.TableField<pan.auditdb.tables.records.ProjectAllocationRecord, Integer> PROJECT_START_DATE = createField("project_start_date", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>pandora_audit.project_allocation.project_finish_date</code>.
	 */
	public final org.jooq.TableField<pan.auditdb.tables.records.ProjectAllocationRecord, Integer> PROJECT_FINISH_DATE = createField("project_finish_date", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>pandora_audit.project_allocation.allocation_hours</code>.
	 */
	public final org.jooq.TableField<pan.auditdb.tables.records.ProjectAllocationRecord, Long> ALLOCATION_HOURS = createField("allocation_hours", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>pandora_audit.project_allocation.allocation_used</code>.
	 */
	public final org.jooq.TableField<pan.auditdb.tables.records.ProjectAllocationRecord, Long> ALLOCATION_USED = createField("allocation_used", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>pandora_audit.project_allocation.allocation_type</code>.
	 */
	public final org.jooq.TableField<pan.auditdb.tables.records.ProjectAllocationRecord, String> ALLOCATION_TYPE = createField("allocation_type", org.jooq.impl.SQLDataType.VARCHAR.length(40).defaulted(true), this, "");

	/**
	 * Create a <code>pandora_audit.project_allocation</code> table reference
	 */
	public ProjectAllocation() {
		this("project_allocation", null);
	}

	/**
	 * Create an aliased <code>pandora_audit.project_allocation</code> table reference
	 */
	public ProjectAllocation(String alias) {
		this(alias, pan.auditdb.tables.ProjectAllocation.PROJECT_ALLOCATION);
	}

	private ProjectAllocation(String alias, org.jooq.Table<pan.auditdb.tables.records.ProjectAllocationRecord> aliased) {
		this(alias, aliased, null);
	}

	private ProjectAllocation(String alias, org.jooq.Table<pan.auditdb.tables.records.ProjectAllocationRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, pan.auditdb.PandoraAudit.PANDORA_AUDIT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<pan.auditdb.tables.records.ProjectAllocationRecord> getPrimaryKey() {
		return pan.auditdb.Keys.KEY_PROJECT_ALLOCATION_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<pan.auditdb.tables.records.ProjectAllocationRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<pan.auditdb.tables.records.ProjectAllocationRecord>>asList(pan.auditdb.Keys.KEY_PROJECT_ALLOCATION_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public pan.auditdb.tables.ProjectAllocation as(String alias) {
		return new pan.auditdb.tables.ProjectAllocation(alias, this);
	}

	/**
	 * Rename this table
	 */
	public pan.auditdb.tables.ProjectAllocation rename(String name) {
		return new pan.auditdb.tables.ProjectAllocation(name, null);
	}
}