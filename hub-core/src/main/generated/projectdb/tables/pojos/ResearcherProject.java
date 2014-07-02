/**
 * This class is generated by jOOQ
 */
package projectdb.tables.pojos;

/**
 * This class is generated by jOOQ.
 *
 * Maps researchers onto a project
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ResearcherProject implements java.io.Serializable {

	private static final long serialVersionUID = -1676831669;

	private java.lang.Integer researcherid;
	private java.lang.Integer projectid;
	private java.lang.Integer researcherroleid;
	private java.lang.String  notes;

	public ResearcherProject() {}

	public ResearcherProject(
		java.lang.Integer researcherid,
		java.lang.Integer projectid,
		java.lang.Integer researcherroleid,
		java.lang.String  notes
	) {
		this.researcherid = researcherid;
		this.projectid = projectid;
		this.researcherroleid = researcherroleid;
		this.notes = notes;
	}

	public java.lang.Integer getResearcherid() {
		return this.researcherid;
	}

	public void setResearcherid(java.lang.Integer researcherid) {
		this.researcherid = researcherid;
	}

	public java.lang.Integer getProjectid() {
		return this.projectid;
	}

	public void setProjectid(java.lang.Integer projectid) {
		this.projectid = projectid;
	}

	public java.lang.Integer getResearcherroleid() {
		return this.researcherroleid;
	}

	public void setResearcherroleid(java.lang.Integer researcherroleid) {
		this.researcherroleid = researcherroleid;
	}

	public java.lang.String getNotes() {
		return this.notes;
	}

	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}
}
