package iob;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import iob.UsersRelatedAPI.NewUser;
import iob.UsersRelatedAPI.UserBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UsersTests {
	private RestTemplate restTemplate;
	private String url;
	private int port;
	private String appName;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		this.url = "http://localhost:" + this.port;
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@AfterEach
	public void teardown() {
		// this.restTemplate.delete(this.url);
	}

	@Test
	public void addUser() {
		this.restTemplate = new RestTemplate();
		url = this.url + "/iob/users";
		NewUser newUser = setNewUserForTesting("mitrlior@gmail.com", "PLAYER", "aaa", "mitrlior");
		UserBoundary user = this.restTemplate.postForObject(url, newUser, UserBoundary.class);
		assertThat(newUser.getEmail().equals(user.getUserId().getEmail()));
		assertThat(user.getUserId().getDomain().equals(appName));
		assertThat(newUser.getAvatar().equals(user.getAvatar()));
		assertThat(newUser.getRole() == user.getRole());
		assertThat(newUser.getUsername().equals(user.getUsername()));
	}

	@Test
	public void testLoginUser() {
		// Create user
		NewUser newUser = setNewUserForTesting("mitrlior@gmail.com", "PLAYER", "aaa", "mitrlior");
		String addUrl = this.url + "/iob/users";
		// Add user
		this.restTemplate.postForObject(addUrl, newUser, UserBoundary.class);
		// Login
		String loginUrl = this.url + "/iob/users/login/" + appName + "/mitrlior@gmail.com";
		UserBoundary userLoggedIn = this.restTemplate.getForObject(loginUrl, UserBoundary.class);
		// Tests
		assertThat(newUser.getEmail().equals(userLoggedIn.getUserId().getEmail()));
		assertThat(userLoggedIn.getUserId().getDomain().equals(appName));
		assertThat(newUser.getAvatar().equals(userLoggedIn.getAvatar()));
		assertThat(newUser.getRole() == userLoggedIn.getRole());
		assertThat(newUser.getUsername().equals(userLoggedIn.getUsername()));
	}

	@Test
	public void contextLoads() throws Exception {
		String st = "x";
		assertThat(st).isEqualTo("x");
	}

	public NewUser setNewUserForTesting(String email, String role, String avatar, String username) {
		NewUser newUser = new NewUser();
		newUser.setEmail(email);
		newUser.setRole(role);
		newUser.setAvatar(avatar);
		newUser.setUsername(username);
		return newUser;
	}

	@Test
	public void updateUser() {
		// Create user
		NewUser newUser = setNewUserForTesting("mitrlior1@gmail.com", "PLAYER", "aaa", "mitrlior1");
		String addUrl = this.url + "/iob/users";
		//UserBoundary userAdded = this.restTemplate.postForObject(addUrl, newUser, UserBoundary.class);
		this.restTemplate.postForObject(addUrl, newUser, UserBoundary.class);
		// Change values
		newUser.setRole("ADMIN");
		newUser.setAvatar("bla");
		newUser.setUsername("mitrlior2");
		// update
		String updateUrl = this.url + "/iob/users/" + appName + "/mitrlior1@gmail.com";
		this.restTemplate.put(updateUrl, newUser);
		// login
		String loginUrl = this.url + "/iob/users/login/" + appName + "/mitrlior1@gmail.com";
		UserBoundary UpdatedUser = this.restTemplate.getForObject(loginUrl, UserBoundary.class); // Re login with new
		// tests
		assertThat(newUser.getEmail().equals(UpdatedUser.getUserId().getEmail()));
		assertThat(UpdatedUser.getUserId().getDomain().equals(appName));
		assertThat(UpdatedUser.getAvatar().equals(newUser.getAvatar()));
		assertThat(UpdatedUser.getRole() == newUser.getRole());
		assertThat(UpdatedUser.getUsername().equals(newUser.getUsername()));
	}
}
