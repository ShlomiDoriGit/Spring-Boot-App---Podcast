package iob.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
/* 
 * USERS table:
 * USER_ID_DOMAIN     |	USER_ID_EMAIL	| ROLE	      |	USER_NAME 	| 	AVATAR	 |
 * VARCHAR(255) <PK>  |	VARCHAR(255)	| VARCHAR(255)|VARCHAR(255) |VARCHAR(255)|
 * 
 * 
 */
@Entity
@Table(name = "USERS")
@Data
public class UserEntity {
	
	@Column(name="USER_ID_DOMAIN")
	private String userIdDomain;
	
	@Id
	@Column(name="USER_ID_EMAIL")
	private String userIdEmail;
	
	@Enumerated(EnumType.STRING) // EnumType.ORDINAL
	private UserRole role;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="USER_AVATAR")
	private String avatar;
}
