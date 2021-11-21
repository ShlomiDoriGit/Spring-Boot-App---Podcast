package integrative.UsersRelatedAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import integrative.logic.UsersService;

@RestController
public class UsersRelatedController {

	private UsersService usersService;

	@Autowired
	public UsersRelatedController(UsersService users) {
		this.usersService = users;
	}

	@RequestMapping(path = "/iob/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createNewUser(@RequestBody NewUser user) {
		UserBoundary ub = new UserBoundary(new UserId("2022a.podcast", user.getEmail()), user.getRole(),
				user.getUsername(), user.getAvatar());
		return usersService.createUser(ub);
	}

	@RequestMapping(path = "/iob/users/login/{userDomain}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginValidUser(@PathVariable("userDomain") String domain,
			@PathVariable("userEmail") String email) {
		UserBoundary ub = new UserBoundary(new UserId(domain, email), "User", "Test", "L");
		return usersService.login(domain, email);
	}

	@RequestMapping(path = "/iob/users/{userDomain}/{userEmail}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@PathVariable("userDomain") String domain, @PathVariable("userEmail") String email,
			@RequestBody UserBoundary user) {
		usersService.updateUser(domain, email, user);
	}

}
