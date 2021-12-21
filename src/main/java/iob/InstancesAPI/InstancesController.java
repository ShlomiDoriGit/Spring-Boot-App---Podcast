package iob.InstancesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.EnhancedInstancesServiceWithPagination;

@RestController
public class InstancesController {

	private EnhancedInstancesServiceWithPagination instancesSrevice;

	@Autowired
	public InstancesController(EnhancedInstancesServiceWithPagination instancesSrevice) {
		this.instancesSrevice = instancesSrevice;
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary instance,
			@PathVariable("userDomain") String domain, @PathVariable("userEmail") String email) {
		return instancesSrevice.createInstance(domain, email, instance);
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/{instanceDomain}/{instanceId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateInstance(@RequestBody InstanceBoundary instance, @PathVariable("userDomain") String user_domain,
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
			@PathVariable("userEmail") String email,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		return instancesSrevice.getAllInstances(user_domain, email, page, size).stream()
				.toArray(InstanceBoundary[]::new);
	}

	// Sprint 4

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/{instanceDomain}/{instanceId}/children", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void bindExistingInstanceToExistingChildInstance(@RequestBody InstanceId instanceIdBoundary,
			@PathVariable("userDomain") String user_domain, @PathVariable("userEmail") String email,
			@PathVariable("instanceDomain") String instance_domain, @PathVariable("instanceId") String instanceId) {
		instancesSrevice.bindExistingInstanceToExistingChildInstance(instanceIdBoundary, user_domain, email,
				instance_domain, instanceId);
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/{instanceDomain}/{instanceId}/children?size={size}&page={page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllChildrensOfExistingInstance(@PathVariable("userDomain") String user_domain,
			@PathVariable("userEmail") String email, @PathVariable("instanceDomain") String instance_domain,
			@PathVariable("instanceId") String instanceId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return instancesSrevice
				.getAllChildrensOfExistingInstance(user_domain, email, instance_domain, instanceId, size, page)
				.toArray(new InstanceBoundary[0]);
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/{instanceDomain}/{instanceId}/parents?size={size}&page={page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getInstanceParents(@PathVariable("userDomain") String user_domain,
			@PathVariable("userEmail") String email, @PathVariable("instanceDomain") String instance_domain,
			@PathVariable("instanceId") String instanceId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {

		return instancesSrevice.getInstanceParents(user_domain, email, instance_domain, instanceId, size, page)
				.toArray(new InstanceBoundary[0]);
	}

	// END Sprint 4

}
