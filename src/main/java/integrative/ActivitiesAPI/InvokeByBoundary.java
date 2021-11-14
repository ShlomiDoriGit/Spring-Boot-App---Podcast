package integrative.ActivitiesAPI;

import integrative.UsersRelatedAPI.UserIdBoundary;

public class InvokeByBoundary {
	private UserIdBoundary userId;

	public InvokeByBoundary() {
		// TODO Auto-generated constructor stub
	}

	public InvokeByBoundary(UserIdBoundary userId) {
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
