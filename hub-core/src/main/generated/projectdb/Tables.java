/**
 * This class is generated by jOOQ
 */
package projectdb;

/**
 * This class is generated by jOOQ.
 *
 * Convenience access to all tables in projectdb
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

	/**
	 * NeSI and CeR staff
	 */
	public static final projectdb.tables.Adviser ADVISER = projectdb.tables.Adviser.ADVISER;

	/**
	 * The table projectdb.adviseraction
	 */
	public static final projectdb.tables.Adviseraction ADVISERACTION = projectdb.tables.Adviseraction.ADVISERACTION;

	/**
	 * The table projectdb.adviserrole
	 */
	public static final projectdb.tables.Adviserrole ADVISERROLE = projectdb.tables.Adviserrole.ADVISERROLE;

	/**
	 * Maps advisers into project
	 */
	public static final projectdb.tables.AdviserProject ADVISER_PROJECT = projectdb.tables.AdviserProject.ADVISER_PROJECT;

	/**
	 * The table projectdb.adviser_properties
	 */
	public static final projectdb.tables.AdviserProperties ADVISER_PROPERTIES = projectdb.tables.AdviserProperties.ADVISER_PROPERTIES;

	/**
	 * The table projectdb.attachment
	 */
	public static final projectdb.tables.Attachment ATTACHMENT = projectdb.tables.Attachment.ATTACHMENT;

	/**
	 * The table projectdb.changelog
	 */
	public static final projectdb.tables.Changelog CHANGELOG = projectdb.tables.Changelog.CHANGELOG;

	/**
	 * The table projectdb.department
	 */
	public static final projectdb.tables.Department DEPARTMENT = projectdb.tables.Department.DEPARTMENT;

	/**
	 * The table projectdb.division
	 */
	public static final projectdb.tables.Division DIVISION = projectdb.tables.Division.DIVISION;

	/**
	 * The table projectdb.facility
	 */
	public static final projectdb.tables.Facility FACILITY = projectdb.tables.Facility.FACILITY;

	/**
	 * The table projectdb.institution
	 */
	public static final projectdb.tables.Institution INSTITUTION = projectdb.tables.Institution.INSTITUTION;

	/**
	 * The table projectdb.institutionalrole
	 */
	public static final projectdb.tables.Institutionalrole INSTITUTIONALROLE = projectdb.tables.Institutionalrole.INSTITUTIONALROLE;

	/**
	 * Key Performance Indicators (for us).
	 */
	public static final projectdb.tables.Kpi KPI = projectdb.tables.Kpi.KPI;

	/**
	 * For KPI NESI-9, these are it's sub categories
	 */
	public static final projectdb.tables.Kpicode KPICODE = projectdb.tables.Kpicode.KPICODE;

	/**
	 * The table projectdb.missingalloc
	 */
	public static final projectdb.tables.Missingalloc MISSINGALLOC = projectdb.tables.Missingalloc.MISSINGALLOC;

	/**
	 * Holds the main information about projects
	 */
	public static final projectdb.tables.Project PROJECT = projectdb.tables.Project.PROJECT;

	/**
	 * The table projectdb.projectallocation
	 */
	public static final projectdb.tables.Projectallocation PROJECTALLOCATION = projectdb.tables.Projectallocation.PROJECTALLOCATION;

	/**
	 * The table projectdb.projectfollowup
	 */
	public static final projectdb.tables.Projectfollowup PROJECTFOLLOWUP = projectdb.tables.Projectfollowup.PROJECTFOLLOWUP;

	/**
	 * The table projectdb.projectreview
	 */
	public static final projectdb.tables.Projectreview PROJECTREVIEW = projectdb.tables.Projectreview.PROJECTREVIEW;

	/**
	 * The table projectdb.projecttype
	 */
	public static final projectdb.tables.Projecttype PROJECTTYPE = projectdb.tables.Projecttype.PROJECTTYPE;

	/**
	 * The table projectdb.project_facility
	 */
	public static final projectdb.tables.ProjectFacility PROJECT_FACILITY = projectdb.tables.ProjectFacility.PROJECT_FACILITY;

	/**
	 * The table projectdb.project_kpi
	 */
	public static final projectdb.tables.ProjectKpi PROJECT_KPI = projectdb.tables.ProjectKpi.PROJECT_KPI;

	/**
	 * The table projectdb.project_properties
	 */
	public static final projectdb.tables.ProjectProperties PROJECT_PROPERTIES = projectdb.tables.ProjectProperties.PROJECT_PROPERTIES;

	/**
	 * The table projectdb.project_status
	 */
	public static final projectdb.tables.ProjectStatus PROJECT_STATUS = projectdb.tables.ProjectStatus.PROJECT_STATUS;

	/**
	 * The table projectdb.researcher
	 */
	public static final projectdb.tables.Researcher RESEARCHER = projectdb.tables.Researcher.RESEARCHER;

	/**
	 * What role the researcher has on a project. Known as a RPLink
	 */
	public static final projectdb.tables.Researcherrole RESEARCHERROLE = projectdb.tables.Researcherrole.RESEARCHERROLE;

	/**
	 * Maps researchers onto a project
	 */
	public static final projectdb.tables.ResearcherProject RESEARCHER_PROJECT = projectdb.tables.ResearcherProject.RESEARCHER_PROJECT;

	/**
	 * The table projectdb.researcher_properties
	 */
	public static final projectdb.tables.ResearcherProperties RESEARCHER_PROPERTIES = projectdb.tables.ResearcherProperties.RESEARCHER_PROPERTIES;

	/**
	 * The table projectdb.researcher_status
	 */
	public static final projectdb.tables.ResearcherStatus RESEARCHER_STATUS = projectdb.tables.ResearcherStatus.RESEARCHER_STATUS;

	/**
	 * The table projectdb.researchoutput
	 */
	public static final projectdb.tables.Researchoutput RESEARCHOUTPUT = projectdb.tables.Researchoutput.RESEARCHOUTPUT;

	/**
	 * The table projectdb.researchoutputtype
	 */
	public static final projectdb.tables.Researchoutputtype RESEARCHOUTPUTTYPE = projectdb.tables.Researchoutputtype.RESEARCHOUTPUTTYPE;

	/**
	 * The table projectdb.site
	 */
	public static final projectdb.tables.Site SITE = projectdb.tables.Site.SITE;

	/**
	 * The table projectdb.tempalloc
	 */
	public static final projectdb.tables.Tempalloc TEMPALLOC = projectdb.tables.Tempalloc.TEMPALLOC;

	/**
	 * The table projectdb.tempproject
	 */
	public static final projectdb.tables.Tempproject TEMPPROJECT = projectdb.tables.Tempproject.TEMPPROJECT;
}
