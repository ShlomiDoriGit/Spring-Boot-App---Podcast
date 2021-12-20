package iob;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import iob.Boundaries.Location;
import iob.InstancesAPI.CreatedBy;
import iob.InstancesAPI.InstanceBoundary;
import iob.UsersRelatedAPI.NewUser;
import iob.UsersRelatedAPI.UserBoundary;
import iob.UsersRelatedAPI.UserId;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InstnacesTests {

	private RestTemplate restTemplate;
	private String url;
	private int port;
	private String appName;
	private UserBoundary userPlayer;
	private UserBoundary userAdmin;
	private UserBoundary userManager;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		this.url = "http://localhost:" + this.port;
	}

	public void createUserForTestingPLAYER() {
		NewUser newUser = new NewUser();
		newUser.setEmail("mitrlior@gmail.com");
		newUser.setRole("PLAYER");
		newUser.setAvatar("blabla");
		newUser.setUsername("mitrlior");
		String createUserUrl = this.url + "/iob/users";
		this.userPlayer = this.restTemplate.postForObject(createUserUrl, newUser, UserBoundary.class);
	}

	public void createUserForTestingAdmin() {
		NewUser newUser = new NewUser();
		newUser.setEmail("mitrlior1@gmail.com");
		newUser.setRole("ADMIN");
		newUser.setAvatar("bla");
		newUser.setUsername("mitrlior");
		String createUserUrl = this.url + "/iob/users";
		this.userPlayer = this.restTemplate.postForObject(createUserUrl, newUser, UserBoundary.class);
	}

	public void createUserForTestingManager() {
		NewUser newUser = new NewUser();
		newUser.setEmail("mitrlior1@gmail.com");
		newUser.setRole("ADMIN");
		newUser.setAvatar("bla");
		newUser.setUsername("mitrlior");
		String createUserUrl = this.url + "/iob/users";
		this.userPlayer = this.restTemplate.postForObject(createUserUrl, newUser, UserBoundary.class);
	}

	public InstanceBoundary getInsatnceBoundaryForTesting() {
		InstanceBoundary newInstance = new InstanceBoundary();
		newInstance.setType("new type");
		newInstance.setName("new podcast");
		newInstance.setActive(true);
		newInstance.setCreatedBy(new CreatedBy(
				new UserId(this.userPlayer.getUserId().getDomain(), this.userPlayer.getUserId().getEmail())));
		newInstance.setType("Furniture");
		newInstance.setName("Chair");
		newInstance.setLocation(new Location(10.0, 12.5));
		return newInstance;
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@AfterEach
	public void teardown() {
//		 this.restTemplate.delete(this.url);
	}

	@Test
	public void addInstanceTest() {
		// Create the Instance
		InstanceBoundary newInstance = getInsatnceBoundaryForTesting();
		// Create the post url
		String postUrl = this.url + "/iob/instances/" + appName + "/" + this.userPlayer.getUserId().getEmail();
		// Post the Instance
		InstanceBoundary createdInstance = this.restTemplate.postForObject(postUrl, newInstance,
				InstanceBoundary.class);
		// Check the post values
		assertThat("x".equals("x"));
		assertNotNull(createdInstance);
		assertThat(createdInstance.getInstanceId().getDomain().equals(appName));
		assertThat(createdInstance.getType().equals(newInstance.getType()));
		assertThat(createdInstance.getName().equals(newInstance.getName()));
		assertThat(createdInstance.getActive() == createdInstance.getActive());
		assertThat(createdInstance.getCreatedBy().getUserId().getDomain().equals(appName));
		assertThat(createdInstance.getCreatedBy().getUserId().getEmail().equals(this.userPlayer.getUserId().getEmail()));
		// TODO: Add map tests
	}

	public void tryGetInstanceFromActiveFalse() {
		InstanceBoundary newInstance = getInsatnceBoundaryForTesting();

	}
}
