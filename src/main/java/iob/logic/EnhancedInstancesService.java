package iob.logic;

import java.util.List;

import iob.InstancesAPI.InstanceBoundary;
import iob.InstancesAPI.InstanceId;

public interface EnhancedInstancesService extends InstancesService {

	public void bindExistingInstanceToExistingChildInstance(InstanceId instanceIdBoundary, String user_domain,
			String email, String instance_domain, String instanceId);

	public List<InstanceBoundary> getAllChildrensOfExistingInstance(InstanceId instanceIdBoundary, String user_domain,
			String email, String instance_domain, String instanceId);

	public List<InstanceBoundary> getInstanceParents(InstanceId instanceIdBoundary, String user_domain, String email,
			String instance_domain, String instanceId);

}
