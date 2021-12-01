package iob.InstancesAPI;

import iob.UsersRelatedAPI.UserId;

public class CreatedBy {
	private UserId userId;

	public CreatedBy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreatedBy(UserId userId) {
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
