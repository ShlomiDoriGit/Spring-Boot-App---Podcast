package iob.UsersRelatedAPI;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserId implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID_DOMAIN")
	private String domain;
	
	@Column(name="USER_ID_EMAIL")
	private String email;
}
