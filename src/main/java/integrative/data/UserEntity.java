package integrative.data;

public class UserEntity {

	private String userId_Domain;
	private String userId_email;
	private UserRole role;
	private String username;
	private String avatar;

	public UserEntity() {
		super();
	}

	public String getUserId_Domain() {
		return userId_Domain;
	}

	public void setUserId_Domain(String userId_Domain) {
		this.userId_Domain = userId_Domain;
	}

	public String getUserId_email() {
		return userId_email;
	}

	public void setUserId_email(String userId_email) {
		this.userId_email = userId_email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
