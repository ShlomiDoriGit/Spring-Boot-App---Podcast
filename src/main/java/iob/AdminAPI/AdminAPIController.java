package iob.AdminAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iob.ActivitiesAPI.ActivityBoundary;
import iob.UsersRelatedAPI.UserBoundary;
import iob.logic.EnhancedActivitiesService;
import iob.logic.EnhancedInstancesServiceWithPagging;
import iob.logic.EnhancedUsersService;

@RestController
public class AdminAPIController {
	private EnhancedUsersService usersService;
	private EnhancedInstancesServiceWithPagging instancesSrevice;
	private EnhancedActivitiesService activitiesService;

	@Autowired
	public AdminAPIController(EnhancedUsersService usersService, EnhancedInstancesServiceWithPagging instancesSrevice,
			EnhancedActivitiesService activitiesService) {
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
			@PathVariable("userEmail") String email,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return usersService.getAllUsers(domain, email, page, size).toArray(new UserBoundary[0]);
	}

	@RequestMapping(path = "/iob/admin/activities/{userDomain}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] exportAllActivities(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {

		return activitiesService.getAllActivities(domain, email, page, size).toArray(new ActivityBoundary[0]);
	}

}