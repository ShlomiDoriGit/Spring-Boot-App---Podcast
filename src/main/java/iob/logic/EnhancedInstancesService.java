package iob.logic;

import java.util.List;
import java.util.Optional;

import iob.InstancesAPI.InstanceBoundary;
import iob.InstancesAPI.InstanceId;

public interface EnhancedInstancesService extends InstancesService {

	public void bindExistingInstanceToExistingChildInstance(InstanceId instanceIdBoundary, String user_domain,
			String email, String instance_domain, String instanceId);

	public List<InstanceBoundary> getAllChildrensOfExistingInstance(String user_domain,
			String email, String instance_domain, String instanceId);

	public List<InstanceBoundary> getInstanceParents(String user_domain, String email,
			String instance_domain, String instanceId);

	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail, int page, int size);
	
	

}
