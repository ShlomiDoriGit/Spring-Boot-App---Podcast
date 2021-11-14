package integrative.InstancesAPI;

import integrative.UsersRelatedAPI.UserIdBoundary;

public class CreatedByBoundary {
	private UserIdBoundary userId;

	public CreatedByBoundary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreatedByBoundary(UserIdBoundary userId) {
		this();
		this.userId = userId;
	}

	public UserIdBoundary getUserId() {
		return userId;
	}

	public void setUserId(UserIdBoundary userId) {
		this.userId = userId;
	}

}
