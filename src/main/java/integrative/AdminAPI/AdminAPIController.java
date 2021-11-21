package integrative.AdminAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import integrative.ActivitiesAPI.ActivityBoundary;
import integrative.UsersRelatedAPI.UserBoundary;
import integrative.logic.ActivitiesService;
import integrative.logic.InstancesService;
import integrative.logic.UsersService;

@RestController
public class AdminAPIController {
	private UsersService usersService;
	private InstancesService instancesSrevice;
	private ActivitiesService activitiesService;

	@Autowired
	public AdminAPIController(UsersService usersService, InstancesService instancesSrevice,
			ActivitiesService activitiesService) {
		this.usersService = usersService;
		this.instancesSrevice = instancesSrevice;
		this.activitiesService = activitiesService;
	}

	@RequestMapping(path = "/iob/admin/users/{userDomain}/{userEmail}", method = RequestMethod.DELETE)
	public void deleteUsersInDomain(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
		usersService.deleteAllUsers(domain, email);
	}

	@RequestMapping(path = "/iob/admin/instances/{userDomain}/{userEmail}", method = RequestMethod.DELETE)
	public void deleteInstancesInDomain(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
		instancesSrevice.deleteAllInstances(domain, email);
	}

	@RequestMapping(path = "/iob/admin/activities/{userDomain}/{userEmail}", method = RequestMethod.DELETE)
	public void deleteActivitiesInDomain(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
		activitiesService.deleteAllActivities(domain, email);
	}

	@RequestMapping(path = "/iob/admin/users/{userDomain}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
		return (UserBoundary[]) usersService.getAllUsers(domain, email).toArray();
	}

	@RequestMapping(path = "/iob/admin/activities/{userDomain}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] exportAllActivities(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
		// I don't like the casting :(
		return (ActivityBoundary[]) activitiesService.getAllActivities(domain, email).toArray();
	}

}