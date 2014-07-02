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
public class Tempalloc extends org.jooq.impl.TableImpl<projectdb.tables.records.TempallocRecord> {

	private static final long serialVersionUID = 399037857;

	/**
	 * The singleton instance of <code>projectdb.tempalloc</code>
	 */
	public static final projectdb.tables.Tempalloc TEMPALLOC = new projectdb.tables.Tempalloc();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<projectdb.tables.records.TempallocRecord> getRecordType() {
		return projectdb.tables.records.TempallocRecord.class;
	}

	/**
	 * The column <code>projectdb.tempalloc.id</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.TempallocRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>projectdb.tempalloc.g_modification_time</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.TempallocRecord, java.lang.Integer> G_MODIFICATION_TIME = createField("g_modification_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>projectdb.tempalloc.g_creation_time</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.TempallocRecord, java.lang.Integer> G_CREATION_TIME = createField("g_creation_time", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>projectdb.tempalloc.g_account</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.TempallocRecord, java.lang.Integer> G_ACCOUNT = createField("g_account", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>projectdb.tempalloc.g_credit_limit</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.TempallocRecord, java.lang.String> G_CREDIT_LIMIT = createField("g_credit_limit", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>projectdb.tempalloc.g_project</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.TempallocRecord, java.lang.String> G_PROJECT = createField("g_project", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>projectdb.tempalloc.g_active</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.TempallocRecord, java.lang.String> G_ACTIVE = createField("g_active", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>projectdb.tempalloc.g_facility</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.TempallocRecord, java.lang.String> G_FACILITY = createField("g_facility", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>projectdb.tempalloc</code> table reference
	 */
	public Tempalloc() {
		this("tempalloc", null);
	}

	/**
	 * Create an aliased <code>projectdb.tempalloc</code> table reference
	 */
	public Tempalloc(java.lang.String alias) {
		this(alias, projectdb.tables.Tempalloc.TEMPALLOC);
	}

	private Tempalloc(java.lang.String alias, org.jooq.Table<projectdb.tables.records.TempallocRecord> aliased) {
		this(alias, aliased, null);
	}

	private Tempalloc(java.lang.String alias, org.jooq.Table<projectdb.tables.records.TempallocRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, projectdb.Projectdb.PROJECTDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<projectdb.tables.records.TempallocRecord, java.lang.Integer> getIdentity() {
		return projectdb.Keys.IDENTITY_TEMPALLOC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<projectdb.tables.records.TempallocRecord> getPrimaryKey() {
		return projectdb.Keys.KEY_TEMPALLOC_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<projectdb.tables.records.TempallocRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<projectdb.tables.records.TempallocRecord>>asList(projectdb.Keys.KEY_TEMPALLOC_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public projectdb.tables.Tempalloc as(java.lang.String alias) {
		return new projectdb.tables.Tempalloc(alias, this);
	}

	/**
	 * Rename this table
	 */
	public projectdb.tables.Tempalloc rename(java.lang.String name) {
		return new projectdb.tables.Tempalloc(name, null);
	}
}
