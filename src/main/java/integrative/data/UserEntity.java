package integrative.data;

public class UserEntity {

	private String userIdDomain;
	private String userIdEmail;
	private UserRole role;
	private String userName;
	private String avatar;

	public UserEntity() {
		super();
	}

	public String getUserIdDomain() {
		return userIdDomain;
	}

	public void setUserIdDomain(String userIdDomain) {
		this.userIdDomain = userIdDomain;
	}

	public String getUserIdEmail() {
		return userIdEmail;
	}

	public void setUserIdEmail(String userIdEmail) {
		this.userIdEmail = userIdEmail;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "UserEntity [userIdDomain=" + userIdDomain + ", userIdEmail=" + userIdEmail + ", role=" + role
				+ ", username=" + userName + ", avatar=" + avatar + "]";
	}

}
