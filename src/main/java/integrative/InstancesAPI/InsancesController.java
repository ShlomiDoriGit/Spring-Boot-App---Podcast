package integrative.InstancesAPI;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import integrative.Boundaries.Location;
import integrative.UsersRelatedAPI.UserId;


@RestController
public class InsancesController {

	@RequestMapping(
			path = "/iob/instances/{userDomain}/{userEmail}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary instance, @PathVariable("userDomain") String domain, @PathVariable("userEmail") String email) {
		instance.getInstanceId().setDomain(domain);
		instance.getInstanceId().setId(email);
		return instance;
	}
	
	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/{instanceDomain}/{instanceId}", 
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody InstanceBoundary instance, 
			@PathVariable("userDomain") String user_domain, 
			@PathVariable("userEmail") String email,
			@PathVariable("instanceDomain") String instance_domain,
			@PathVariable("instanceId") String instanceId) {
		instance.getInstanceId().setDomain(instance_domain);
		instance.getInstanceId().setId(instanceId);
		instance.getCreatedBy().getUserId().setDomain(user_domain);
		instance.getCreatedBy().getUserId().setEmail(email);
	}
	
	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/{instanceDomain}/{instanceId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retrieveInstance(
			@PathVariable("userDomain") String user_domain, 
			@PathVariable("userEmail") String email,
			@PathVariable("instanceDomain") String instance_domain,
			@PathVariable("instanceId") String instanceId) {
		return new InstanceBoundary(
				new InstanceId(instance_domain, instanceId),
				"type",
				"name",
				true,
				new Date(),
				new CreatedBy(new UserId(user_domain, email)),
				new Location(-1d, -1d),
				null
				);
	}
	
	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] retrieveAllInstance(
			@PathVariable("userDomain") String user_domain, 
			@PathVariable("userEmail") String email) {
		return new InstanceBoundary[] {
				new InstanceBoundary(
						new InstanceId("domain0", "Id0"),
						"type",
						"name",
						true,
						new Date(),
						new CreatedBy(new UserId(user_domain, email)),
						new Location(-1d, -1d),
						null
						),
				new InstanceBoundary(
						new InstanceId("domain1", "Id1"),
						"type",
						"name",
						true,
						new Date(),
						new CreatedBy(new UserId(user_domain, email)),
						new Location(-1d, -1d),
						null
						)
		};
	}
}
