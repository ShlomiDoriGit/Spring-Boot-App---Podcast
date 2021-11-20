package integrative.UsersRelatedAPI;
/*
{
	"email":"user@demo.com",
	"role":"PLAYER",
	"username":"Demo User",
	"avatar":"J"
}
*/


public class NewUser {
	private String email;
	private String role;
	private String username;
	private String avatar;
	
	
	public NewUser() {
	}
	
	public NewUser(String email, String role, String username, String avatar) {
		this();
		this.email = email;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
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