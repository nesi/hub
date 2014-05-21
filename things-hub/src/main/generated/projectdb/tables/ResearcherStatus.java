/**
 * This class is generated by jOOQ
 */
package projectdb.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ResearcherStatus extends org.jooq.impl.TableImpl<projectdb.tables.records.ResearcherStatusRecord> {

	private static final long serialVersionUID = -101845268;

	/**
	 * The singleton instance of <code>projectdb.researcher_status</code>
	 */
	public static final projectdb.tables.ResearcherStatus RESEARCHER_STATUS = new projectdb.tables.ResearcherStatus();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<projectdb.tables.records.ResearcherStatusRecord> getRecordType() {
		return projectdb.tables.records.ResearcherStatusRecord.class;
	}

	/**
	 * The column <code>projectdb.researcher_status.id</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.ResearcherStatusRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>projectdb.researcher_status.name</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.ResearcherStatusRecord, java.lang.String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * Create a <code>projectdb.researcher_status</code> table reference
	 */
	public ResearcherStatus() {
		this("researcher_status", null);
	}

	/**
	 * Create an aliased <code>projectdb.researcher_status</code> table reference
	 */
	public ResearcherStatus(java.lang.String alias) {
		this(alias, projectdb.tables.ResearcherStatus.RESEARCHER_STATUS);
	}

	private ResearcherStatus(java.lang.String alias, org.jooq.Table<projectdb.tables.records.ResearcherStatusRecord> aliased) {
		this(alias, aliased, null);
	}

	private ResearcherStatus(java.lang.String alias, org.jooq.Table<projectdb.tables.records.ResearcherStatusRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, projectdb.Projectdb.PROJECTDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<projectdb.tables.records.ResearcherStatusRecord, java.lang.Integer> getIdentity() {
		return projectdb.Keys.IDENTITY_RESEARCHER_STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<projectdb.tables.records.ResearcherStatusRecord> getPrimaryKey() {
		return projectdb.Keys.KEY_RESEARCHER_STATUS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<projectdb.tables.records.ResearcherStatusRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<projectdb.tables.records.ResearcherStatusRecord>>asList(projectdb.Keys.KEY_RESEARCHER_STATUS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public projectdb.tables.ResearcherStatus as(java.lang.String alias) {
		return new projectdb.tables.ResearcherStatus(alias, this);
	}

	/**
	 * Rename this table
	 */
	public projectdb.tables.ResearcherStatus rename(java.lang.String name) {
		return new projectdb.tables.ResearcherStatus(name, null);
	}
}
