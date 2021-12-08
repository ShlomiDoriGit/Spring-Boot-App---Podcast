package iob.UsersRelatedAPI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
{
	"domain":"2022a.demo",
	"email":"user@demo.com"
}
 */

@NoArgsConstructor
@Getter
@Setter
public class UserId {
	private String domain;
	private String email;

	public UserId(String domain, String email) {
		this();
		this.domain = domain;
		this.email = email;
	}
}
