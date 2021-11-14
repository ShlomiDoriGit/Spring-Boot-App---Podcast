package integrative.InstancesAPI;


public class CreatedByBoundary {
	private CreatedByBoundary userId;

	public CreatedByBoundary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreatedByBoundary(CreatedByBoundary userId) {
		this();
		this.userId = userId;
	}

	public CreatedByBoundary getUserId() {
		return userId;
	}

	public void setUserId(CreatedByBoundary userId) {
		this.userId = userId;
	}

}
