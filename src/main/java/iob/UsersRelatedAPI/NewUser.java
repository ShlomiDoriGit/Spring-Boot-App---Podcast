package iob.UsersRelatedAPI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
{
	"email":"user@demo.com",
	"role":"PLAYER",
	"username":"Demo User",
	"avatar":"J"
}
*/

@NoArgsConstructor
@Getter
@Setter
public class NewUser {
	private String email;
	private String role;
	private String username;
	private String avatar;
	
	
	public NewUser(String email, String role, String username, String avatar) {
		this();
		this.email = email;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}
	
}
