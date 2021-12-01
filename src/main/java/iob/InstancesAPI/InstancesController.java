package iob.InstancesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.InstancesService;

@RestController
public class InstancesController {

	private InstancesService instancesSrevice;

	@Autowired
	public InstancesController(InstancesService instancesSrevice) {
		this.instancesSrevice = instancesSrevice;
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary instance,
			@PathVariable("userDomain") String domain, @PathVariable("userEmail") String email) {
		return instancesSrevice.createInstance(domain, email, instance);
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/{instanceDomain}/{instanceId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody InstanceBoundary instance, @PathVariable("userDomain") String user_domain,
			@PathVariable("userEmail") String email, @PathVariable("instanceDomain") String instance_domain,
			@PathVariable("instanceId") String instanceId) {
		instancesSrevice.updateInstance(instance_domain, email, instance_domain, instanceId, instance);
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/{instanceDomain}/{instanceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retrieveInstance(@PathVariable("userDomain") String user_domain,
			@PathVariable("userEmail") String email, @PathVariable("instanceDomain") String instance_domain,
			@PathVariable("instanceId") String instanceId) {
		return instancesSrevice.getSpecificInstance(instance_domain, email, instance_domain, instanceId);
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] retrieveAllInstance(@PathVariable("userDomain") String user_domain,
			@PathVariable("userEmail") String email) {
		return instancesSrevice.getAllInstances(user_domain, email).stream().toArray(InstanceBoundary[]::new);
	}
}
