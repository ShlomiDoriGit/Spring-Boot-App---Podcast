package iob.logic;

import java.util.List;

import iob.InstancesAPI.InstanceBoundary;
import iob.InstancesAPI.InstanceId;
import iob.data.InstanceEntity;

public interface EnhancedInstancesService extends InstancesService {

	public void bindExistingInstanceToExistingChildInstance(InstanceId instanceIdBoundary, String user_domain,
			String email, String instance_domain, String instanceId);

	public List<InstanceBoundary> getAllChildrensOfExistingInstance(String user_domain,
			String email, String instance_domain, String instanceId,int page, int size);

	public List<InstanceBoundary> getInstanceParents(String user_domain, String email,
			String instance_domain, String instanceId,int page, int size);

	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail, int page, int size);
	
	public InstanceEntity[] searchByName(String name,int size ,int page);
}
