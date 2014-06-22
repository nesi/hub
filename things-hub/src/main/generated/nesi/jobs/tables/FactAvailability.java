/**
 * This class is generated by jOOQ
 */
package nesi.jobs.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FactAvailability extends org.jooq.impl.TableImpl<nesi.jobs.tables.records.FactAvailabilityRecord> {

	private static final long serialVersionUID = 1118831540;

	/**
	 * The singleton instance of <code>public.fact_availability</code>
	 */
	public static final nesi.jobs.tables.FactAvailability FACT_AVAILABILITY = new nesi.jobs.tables.FactAvailability();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<nesi.jobs.tables.records.FactAvailabilityRecord> getRecordType() {
		return nesi.jobs.tables.records.FactAvailabilityRecord.class;
	}

	/**
	 * The column <code>public.fact_availability.fact_availability_id</code>.
	 */
	public final org.jooq.TableField<nesi.jobs.tables.records.FactAvailabilityRecord, java.lang.Integer> FACT_AVAILABILITY_ID = createField("fact_availability_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.fact_availability.date_iso</code>.
	 */
	public final org.jooq.TableField<nesi.jobs.tables.records.FactAvailabilityRecord, java.sql.Date> DATE_ISO = createField("date_iso", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

	/**
	 * The column <code>public.fact_availability.dim_machine_id</code>.
	 */
	public final org.jooq.TableField<nesi.jobs.tables.records.FactAvailabilityRecord, java.lang.Integer> DIM_MACHINE_ID = createField("dim_machine_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.fact_availability.core_seconds_total</code>.
	 */
	public final org.jooq.TableField<nesi.jobs.tables.records.FactAvailabilityRecord, java.lang.Integer> CORE_SECONDS_TOTAL = createField("core_seconds_total", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.fact_availability.unsked</code>.
	 */
	public final org.jooq.TableField<nesi.jobs.tables.records.FactAvailabilityRecord, java.lang.Integer> UNSKED = createField("unsked", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.fact_availability.sked</code>.
	 */
	public final org.jooq.TableField<nesi.jobs.tables.records.FactAvailabilityRecord, java.lang.Integer> SKED = createField("sked", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>public.fact_availability</code> table reference
	 */
	public FactAvailability() {
		this("fact_availability", null);
	}

	/**
	 * Create an aliased <code>public.fact_availability</code> table reference
	 */
	public FactAvailability(java.lang.String alias) {
		this(alias, nesi.jobs.tables.FactAvailability.FACT_AVAILABILITY);
	}

	private FactAvailability(java.lang.String alias, org.jooq.Table<nesi.jobs.tables.records.FactAvailabilityRecord> aliased) {
		this(alias, aliased, null);
	}

	private FactAvailability(java.lang.String alias, org.jooq.Table<nesi.jobs.tables.records.FactAvailabilityRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, nesi.jobs.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<nesi.jobs.tables.records.FactAvailabilityRecord, java.lang.Integer> getIdentity() {
		return nesi.jobs.Keys.IDENTITY_FACT_AVAILABILITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<nesi.jobs.tables.records.FactAvailabilityRecord> getPrimaryKey() {
		return nesi.jobs.Keys.FACT_AVAILABILITY_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<nesi.jobs.tables.records.FactAvailabilityRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<nesi.jobs.tables.records.FactAvailabilityRecord>>asList(nesi.jobs.Keys.FACT_AVAILABILITY_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public nesi.jobs.tables.FactAvailability as(java.lang.String alias) {
		return new nesi.jobs.tables.FactAvailability(alias, this);
	}

	/**
	 * Rename this table
	 */
	public nesi.jobs.tables.FactAvailability rename(java.lang.String name) {
		return new nesi.jobs.tables.FactAvailability(name, null);
	}
}