/**
 * This class is generated by jOOQ
 */
package nesi.jobs;

/**
 * This class is generated by jOOQ.
 *
 * Convenience access to all sequences in public
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

	/**
	 * The sequence <code>public.dim_allocation_dim_allocation_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> DIM_ALLOCATION_DIM_ALLOCATION_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("dim_allocation_dim_allocation_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.dim_job_dim_job_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> DIM_JOB_DIM_JOB_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("dim_job_dim_job_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.dim_machine_dim_machine_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> DIM_MACHINE_DIM_MACHINE_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("dim_machine_dim_machine_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.dim_project_dim_project_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> DIM_PROJECT_DIM_PROJECT_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("dim_project_dim_project_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.dim_user_dim_user_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> DIM_USER_DIM_USER_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("dim_user_dim_user_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.fact_allocation_daily_fact_allocation_daily_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> FACT_ALLOCATION_DAILY_FACT_ALLOCATION_DAILY_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("fact_allocation_daily_fact_allocation_daily_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.fact_allocation_fact_allocation_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> FACT_ALLOCATION_FACT_ALLOCATION_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("fact_allocation_fact_allocation_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.fact_availability_fact_availability_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> FACT_AVAILABILITY_FACT_AVAILABILITY_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("fact_availability_fact_availability_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.fact_daily_usage_fact_daily_usage_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> FACT_DAILY_USAGE_FACT_DAILY_USAGE_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("fact_daily_usage_fact_daily_usage_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.fact_job_daily_fact_job_daily_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> FACT_JOB_DAILY_FACT_JOB_DAILY_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("fact_job_daily_fact_job_daily_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.fact_job_fact_job_id_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> FACT_JOB_FACT_JOB_ID_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("fact_job_fact_job_id_seq", nesi.jobs.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
