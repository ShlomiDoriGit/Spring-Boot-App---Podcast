package iob.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
/* 
 * USERS table:
 * USER_ID_DOMAIN     |	USER_ID_EMAIL	| ROLE	      |	USER_NAME 	| 	AVATAR	 |
 * VARCHAR(255) <PK>  |	VARCHAR(255)	| VARCHAR(255)|VARCHAR(255) |VARCHAR(255)|
 * 
 * 
 */
@Entity
@Table(name = "USERS")
public class UserEntity {

	private String userIdDomain;
	private String userIdEmail;
	private UserRole role;
	private String userName;
	private String avatar;

	public UserEntity() {
		super();
	}

	@Column(name="USER_ID_DOMAIN")
	public String getUserIdDomain() {
		return userIdDomain;
	}


	public void setUserIdDomain(String userIdDomain) {
		this.userIdDomain = userIdDomain;
	}

	@Column(name="USER_ID_EMAIL")
	@Id
	public String getUserIdEmail() {
		return userIdEmail;
	}

	
	public void setUserIdEmail(String userIdEmail) {
		this.userIdEmail = userIdEmail;
	}

	@Enumerated(EnumType.STRING) // EnumType.ORDINAL
	public UserRole getRole() {
		return role;
	}

	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	@Column(name="USER_NAME")
	public String getUserName() {
		return userName;
	}

	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="USER_AVATAR")
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
