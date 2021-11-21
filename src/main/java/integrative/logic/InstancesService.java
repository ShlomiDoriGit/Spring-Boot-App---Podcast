package integrative.logic;

import integrative.InstancesAPI.InstanceBoundary;

import java.util.List;

public interface InstancesService {

	InstanceBoundary createInstance(String userDomain, String userEmail, InstanceBoundary instance);

	InstanceBoundary updateInstance(String userDomain, String userEmail, String instanceDomain, String InstanceId, InstanceBoundary update);

	List<InstanceBoundary> getAllInstances(String userDomain, String userEmail);
	
	InstanceBoundary getSpecificInstance(String userDomain, String userEmail, String InstanceDomain, String instanceId);
	
	void deleteAllInstances(String adminDomain, String adminEmail);

}
