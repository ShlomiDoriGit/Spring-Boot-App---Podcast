package iob.ActivitiesAPI;

import iob.UsersRelatedAPI.UserId;

public class InvokeBy {
	private UserId userId;

	public InvokeBy() {
		// TODO Auto-generated constructor stub
	}

	public InvokeBy(UserId userId) {
		this();
		this.userId = userId;
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}
}
