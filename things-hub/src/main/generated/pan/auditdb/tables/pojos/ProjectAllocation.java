/**
 * This class is generated by jOOQ
 */
package pan.auditdb.tables.pojos;

/**
 * This class is generated by jOOQ.
 *
 * InnoDB free: 8458240 kB
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProjectAllocation implements java.io.Serializable {

	private static final long serialVersionUID = -1321281944;

	private java.lang.String  projectId;
	private java.lang.Integer projectStartDate;
	private java.lang.Integer projectFinishDate;
	private java.lang.Long    allocationHours;
	private java.lang.Long    allocationUsed;
	private java.lang.String  allocationType;

	public ProjectAllocation() {}

	public ProjectAllocation(
		java.lang.String  projectId,
		java.lang.Integer projectStartDate,
		java.lang.Integer projectFinishDate,
		java.lang.Long    allocationHours,
		java.lang.Long    allocationUsed,
		java.lang.String  allocationType
	) {
		this.projectId = projectId;
		this.projectStartDate = projectStartDate;
		this.projectFinishDate = projectFinishDate;
		this.allocationHours = allocationHours;
		this.allocationUsed = allocationUsed;
		this.allocationType = allocationType;
	}

	public java.lang.String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(java.lang.String projectId) {
		this.projectId = projectId;
	}

	public java.lang.Integer getProjectStartDate() {
		return this.projectStartDate;
	}

	public void setProjectStartDate(java.lang.Integer projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public java.lang.Integer getProjectFinishDate() {
		return this.projectFinishDate;
	}

	public void setProjectFinishDate(java.lang.Integer projectFinishDate) {
		this.projectFinishDate = projectFinishDate;
	}

	public java.lang.Long getAllocationHours() {
		return this.allocationHours;
	}

	public void setAllocationHours(java.lang.Long allocationHours) {
		this.allocationHours = allocationHours;
	}

	public java.lang.Long getAllocationUsed() {
		return this.allocationUsed;
	}

	public void setAllocationUsed(java.lang.Long allocationUsed) {
		this.allocationUsed = allocationUsed;
	}

	public java.lang.String getAllocationType() {
		return this.allocationType;
	}

	public void setAllocationType(java.lang.String allocationType) {
		this.allocationType = allocationType;
	}
}
