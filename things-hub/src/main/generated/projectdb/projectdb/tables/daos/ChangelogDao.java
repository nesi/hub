/**
 * This class is generated by jOOQ
 */
package projectdb.tables.daos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChangelogDao extends org.jooq.impl.DAOImpl<projectdb.tables.records.ChangelogRecord, projectdb.tables.pojos.Changelog, Integer> {

	/**
	 * Create a new ChangelogDao without any panAuditConfiguration
	 */
	public ChangelogDao() {
		super(projectdb.tables.Changelog.CHANGELOG, projectdb.tables.pojos.Changelog.class);
	}

	/**
	 * Create a new ChangelogDao with an attached panAuditConfiguration
	 */
	public ChangelogDao(org.jooq.Configuration configuration) {
		super(projectdb.tables.Changelog.CHANGELOG, projectdb.tables.pojos.Changelog.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(projectdb.tables.pojos.Changelog object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public java.util.List<projectdb.tables.pojos.Changelog> fetchById(Integer... values) {
		return fetch(projectdb.tables.Changelog.CHANGELOG.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public projectdb.tables.pojos.Changelog fetchOneById(Integer value) {
		return fetchOne(projectdb.tables.Changelog.CHANGELOG.ID, value);
	}

	/**
	 * Fetch records that have <code>tbl IN (values)</code>
	 */
	public java.util.List<projectdb.tables.pojos.Changelog> fetchByTbl(String... values) {
		return fetch(projectdb.tables.Changelog.CHANGELOG.TBL, values);
	}

	/**
	 * Fetch records that have <code>tbl_id IN (values)</code>
	 */
	public java.util.List<projectdb.tables.pojos.Changelog> fetchByTblId(Integer... values) {
		return fetch(projectdb.tables.Changelog.CHANGELOG.TBL_ID, values);
	}

	/**
	 * Fetch records that have <code>field IN (values)</code>
	 */
	public java.util.List<projectdb.tables.pojos.Changelog> fetchByField(String... values) {
		return fetch(projectdb.tables.Changelog.CHANGELOG.FIELD, values);
	}

	/**
	 * Fetch records that have <code>old_val IN (values)</code>
	 */
	public java.util.List<projectdb.tables.pojos.Changelog> fetchByOldVal(String... values) {
		return fetch(projectdb.tables.Changelog.CHANGELOG.OLD_VAL, values);
	}

	/**
	 * Fetch records that have <code>new_val IN (values)</code>
	 */
	public java.util.List<projectdb.tables.pojos.Changelog> fetchByNewVal(String... values) {
		return fetch(projectdb.tables.Changelog.CHANGELOG.NEW_VAL, values);
	}

	/**
	 * Fetch records that have <code>timestamp IN (values)</code>
	 */
	public java.util.List<projectdb.tables.pojos.Changelog> fetchByTimestamp(java.sql.Timestamp... values) {
		return fetch(projectdb.tables.Changelog.CHANGELOG.TIMESTAMP, values);
	}

	/**
	 * Fetch records that have <code>adviserId IN (values)</code>
	 */
	public java.util.List<projectdb.tables.pojos.Changelog> fetchByAdviserid(Integer... values) {
		return fetch(projectdb.tables.Changelog.CHANGELOG.ADVISERID, values);
	}
}