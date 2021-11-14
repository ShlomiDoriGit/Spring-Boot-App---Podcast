package integrative.AdminAPI;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import integrative.ActivitiesAPI.ActivityBoundary;
import integrative.UsersRelatedAPI.UserBoundary;
import integrative.UsersRelatedAPI.UserIdBoundary;

@RestController
public class AdminAPIController {

	@RequestMapping(path = "/iob/admin/users/{userDomain}/{userEmail}", method = RequestMethod.DELETE)
	public void deleteUsersInDomain(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
	}

	@RequestMapping(path = "/iob/admin/instances/{userDomain}/{userEmail}", method = RequestMethod.DELETE)
	public void deleteInstancesInDomain(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
	}

	@RequestMapping(path = "/iob/admin/activities/{userDomain}/{userEmail}", method = RequestMethod.DELETE)
	public void deleteActivitiesInDomain(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
	}

	@RequestMapping(path = "/iob/admin/users/{userDomain}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
		//TODO
		return new UserBoundary[] { new UserBoundary(new UserIdBoundary(domain, email), "User", "Test", "L"),
				new UserBoundary(new UserIdBoundary(domain, email), "User", "Test1", "L") };
	}

	@RequestMapping(path = "/iob/admin/activities/{userDomain}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] exportAllActivities(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
		// TODO
		return null;
	}

}