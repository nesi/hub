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
public class Institutionalrole extends org.jooq.impl.TableImpl<projectdb.tables.records.InstitutionalroleRecord> {

	private static final long serialVersionUID = -644350806;

	/**
	 * The singleton instance of <code>projectdb.institutionalrole</code>
	 */
	public static final projectdb.tables.Institutionalrole INSTITUTIONALROLE = new projectdb.tables.Institutionalrole();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<projectdb.tables.records.InstitutionalroleRecord> getRecordType() {
		return projectdb.tables.records.InstitutionalroleRecord.class;
	}

	/**
	 * The column <code>projectdb.institutionalrole.id</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.InstitutionalroleRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>projectdb.institutionalrole.name</code>.
	 */
	public final org.jooq.TableField<projectdb.tables.records.InstitutionalroleRecord, java.lang.String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * Create a <code>projectdb.institutionalrole</code> table reference
	 */
	public Institutionalrole() {
		this("institutionalrole", null);
	}

	/**
	 * Create an aliased <code>projectdb.institutionalrole</code> table reference
	 */
	public Institutionalrole(java.lang.String alias) {
		this(alias, projectdb.tables.Institutionalrole.INSTITUTIONALROLE);
	}

	private Institutionalrole(java.lang.String alias, org.jooq.Table<projectdb.tables.records.InstitutionalroleRecord> aliased) {
		this(alias, aliased, null);
	}

	private Institutionalrole(java.lang.String alias, org.jooq.Table<projectdb.tables.records.InstitutionalroleRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, projectdb.Projectdb.PROJECTDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public projectdb.tables.Institutionalrole as(java.lang.String alias) {
		return new projectdb.tables.Institutionalrole(alias, this);
	}

	/**
	 * Rename this table
	 */
	public projectdb.tables.Institutionalrole rename(java.lang.String name) {
		return new projectdb.tables.Institutionalrole(name, null);
	}
}
