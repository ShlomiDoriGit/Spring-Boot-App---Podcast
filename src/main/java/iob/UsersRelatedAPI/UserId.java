package iob.UsersRelatedAPI;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Getter
@Setter
public class UserId {
	private String domain;
	private String email;
}
