package iob.UsersRelatedAPI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
{
	"userId":{
		"domain":"2022a.demo",
		"email":"user@demo.com"
	},
	"role":"PLAYER",
	"username":"Demo User",
	"avatar":"J"

}
*/

@NoArgsConstructor
@Getter
@Setter
public class UserBoundary {
	private UserId userId;
	private String role;
	private String username;
	private String avatar;

	public UserBoundary(UserId userId, String role, String username, String avatar) {
		this();
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}

	public UserBoundary(NewUser newUser) {
		this(new UserId("demo", newUser.getEmail()), newUser.getRole(), newUser.getUsername(), newUser.getAvatar());
	}
}
