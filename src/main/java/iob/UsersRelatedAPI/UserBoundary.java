package iob.UsersRelatedAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@AllArgsConstructor
@Getter
@Setter
public class UserBoundary {
	private UserId userId;
	private String role;
	private String username;
	private String avatar;


	public UserBoundary(NewUser newUser) {
		this(new UserId("demo", newUser.getEmail()), newUser.getRole(), newUser.getUsername(), newUser.getAvatar());
	}
}
