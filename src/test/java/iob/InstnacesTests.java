package iob;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		// All 3 types of users for testing
		createUserForTestingManager();
		createUserForTestingPLAYER();
		createUserForTestingAdmin();
	}

	public void createUserForTestingPLAYER() {
		NewUser newUser = new NewUser();
		newUser.setEmail("mitrliorPlayer@gmail.com");
		newUser.setRole("PLAYER");
		newUser.setAvatar("blabla");
		newUser.setUsername("mitrliorPlayer");
		String createUserUrl = this.url + "/iob/users";
		this.userPlayer = this.restTemplate.postForObject(createUserUrl, newUser, UserBoundary.class);
	}

	public void createUserForTestingAdmin() {
		NewUser newUser = new NewUser();
		newUser.setEmail("mitrliorAdmin@gmail.com");
		newUser.setRole("ADMIN");
		newUser.setAvatar("bla");
		newUser.setUsername("mitrliorAdmin");
		String createUserUrl = this.url + "/iob/users";
		this.userAdmin = this.restTemplate.postForObject(createUserUrl, newUser, UserBoundary.class);
	}

	public void createUserForTestingManager() {
		NewUser newUser = new NewUser();
		newUser.setEmail("mitrliorManager@gmail.com");
		newUser.setRole("MANAGER");
		newUser.setAvatar("bla");
		newUser.setUsername("mitrliorManager");
		String createUserUrl = this.url + "/iob/users";
		this.userManager = this.restTemplate.postForObject(createUserUrl, newUser, UserBoundary.class);
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
		// Create the Player user
		createUserForTestingPLAYER();
		// Create the Instance
		InstanceBoundary newInstance = getInsatnceBoundaryForTesting();
		// Create the post url
		String postUrl = this.url + "/iob/instances/" + appName + "/" + this.userPlayer.getUserId().getEmail();
		// Post the Instance
		InstanceBoundary createdInstance = this.restTemplate.postForObject(postUrl, newInstance,
				InstanceBoundary.class);
		// Check the post values
		assertNotNull(createdInstance);
		assertThat(createdInstance.getInstanceId().getDomain().equals(appName));
		assertThat(createdInstance.getType().equals(newInstance.getType()));
		assertThat(createdInstance.getName().equals(newInstance.getName()));
		assertThat(createdInstance.getActive() == createdInstance.getActive());
		assertThat(createdInstance.getCreatedBy().getUserId().getDomain().equals(appName));
		assertThat(
				createdInstance.getCreatedBy().getUserId().getEmail().equals(this.userPlayer.getUserId().getEmail()));
		// TODO: Add map tests
	}

	@Test
	public void GetInsatnceActivaFalse() {
		// Create the Instance
		InstanceBoundary newInstance = getInsatnceBoundaryForTesting();
		newInstance.setActive(false);
		String postUrl = this.url + "/iob/instances/" + appName + "/" + this.userPlayer.getUserId().getEmail();
		// Create Player user and instance not active
		InstanceBoundary postedInstance = this.restTemplate.postForObject(postUrl, newInstance, InstanceBoundary.class);
		// Player Test
		// Player get Instance URL
		String getInsatncePlyaerURL = this.url + "/iob/instances/" + this.appName + "/"
				+ this.userPlayer.getUserId().getEmail() + "/" + this.userPlayer.getUserId().getDomain() + "/"
				+ postedInstance.getInstanceId().getId();
		Exception exception = assertThrows(RuntimeException.class, () -> {
			// Try to get the Instance, expected exeption
			InstanceBoundary instance = this.restTemplate.getForObject(getInsatncePlyaerURL, InstanceBoundary.class);
		});

		String expectedMessage = "Instance not found " + postedInstance.getInstanceId().getId();
		String actualMessage = exception.getMessage();
		assertThat(actualMessage.equals(expectedMessage));

		// Manager get Instace URL
		String getInsatnceURL = this.url + "/iob/instances/" + this.appName + "/"
				+ this.userManager.getUserId().getEmail() + "/" + this.userManager.getUserId().getDomain() + "/"
				+ postedInstance.getInstanceId().getId();
		InstanceBoundary createdInstance = this.restTemplate.getForObject(getInsatnceURL, InstanceBoundary.class);

		// Manager Test
		assertThat(createdInstance.getInstanceId().equals(newInstance.getInstanceId()));
		assertThat(createdInstance.getActive() == false);

		// For Admin
		// Manager get Instance URL
		getInsatnceURL = this.url + "/iob/instances/" + this.appName + "/" + this.userAdmin.getUserId().getEmail() + "/"
				+ this.userAdmin.getUserId().getDomain() + "/" + postedInstance.getInstanceId().getId();
////		exception = assertThrows(RuntimeException.class, () -> {
//			InstanceBoundary createdInstanceAdmin = this.restTemplate.getForObject(getInsatnceURL, InstanceBoundary.class);
//		});
//		expectedMessage = "Admin does not have permission to get instances";
//		assertThat(actualMessage.equals(expectedMessage));
	}

//	@Test
//	public void deleteAllItems() {
//		// Create the Instance
//		InstanceBoundary newInstance = getInsatnceBoundaryForTesting();
//		newInstance.setActive(false);
//		String postUrl = this.url + "/iob/instances/" + appName + "/" + this.userPlayer.getUserId().getEmail();
//		// Create Player user and instance not active
//		this.restTemplate.postForObject(postUrl, newInstance, InstanceBoundary.class);
//		// Create the Instance
//		newInstance = getInsatnceBoundaryForTesting();
//		newInstance.setActive(false);
//		// Create Player user and instance not active
//		this.restTemplate.postForObject(postUrl, newInstance, InstanceBoundary.class);
//
//
//		// Check that we got 2 instances
//		String getAllInstancesURL = this.url + "/iob/admin/users/" + this.userAdmin.getUserId().getDomain() + "/"
//				+ this.userAdmin.getUserId().getEmail();
//		
//		// Remove all instances
//	}
}
