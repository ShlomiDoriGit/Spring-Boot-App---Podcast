package integrative.servicesMockup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import integrative.InstancesAPI.InstanceBoundary;
import integrative.converters.InstanceConverter;
import integrative.data.InstanceEntity;
import integrative.logic.InstancesService;

public class InstacesServiceMockup implements InstancesService {
	private Map<String, InstanceEntity> instances;
	private InstanceConverter instanceConverter;

	//TODO check if needed appName like UserBoundary
	//TODO check how we put the id for searching and putting a new Instance

	// Constructor
	public InstacesServiceMockup(InstanceConverter converter) {
		super();
		this.instances = Collections.synchronizedMap(new HashMap<>());
	}

	@Autowired
	public void setConverter(InstanceConverter converter) {
		this.instanceConverter = converter;
	}

	@Override
	public InstanceBoundary createInstance(String userDomain, String userEmail, InstanceBoundary instance) {
		if (userDomain == null || userDomain == "")
			throw new RuntimeException("Could not create an instance withot user's domail");
		if (userEmail == null || userEmail == "")
			throw new RuntimeException("Could not create an instance without user's email");
		if (instance == null)
			throw new RuntimeException("Could not create an instance without instance boundary");
		InstanceEntity entity = this.instanceConverter.convertToEntity(instance);
		entity.setUserDomain(userDomain);
		entity.setUserEmail(userEmail);
		this.instances.put(entity.getUserEmail() + "@" + entity.getUserDomain(), entity);
		return this.instanceConverter.convertToBoundary(entity);
		// TODO check this method, instance ID
	}

	@Override
	public InstanceBoundary updateInstance(String userDomain, String userEmail, String instanceDomain,
			String InstanceId, InstanceBoundary update) {
		InstanceEntity entity = this.instances.get(userEmail + "@" + userDomain);

		if (entity != null) {
			if (update.getType() != null)
				entity.setType(update.getType());
			if (update.getName() != null)
				entity.setName(update.getName());
			if (update.getActive() != null)
				entity.setActive(update.getActive());
			// CreatedTimeStamp don't need to be changed
			if (update.getLocation() != null) {
				if (update.getLocation().getLat() != null)
					entity.setLat(update.getLocation().getLat());
				if (update.getLocation().getLng() != null)
					entity.setLng(update.getLocation().getLng());
			}
			if (update.getInstanceAttributes() != null)
				entity.setInstanceAttributes(update.getInstanceAttributes());
		} else {
			throw new RuntimeException("Could not find instance");
		}
		return null;
	}

	@Override
	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail) {
		return this.instances.values().stream().map(this.instanceConverter::convertToBoundary)
				.collect(Collectors.toList());
	}

	@Override
	public InstanceBoundary getSpecificInstance(String userDomain, String userEmail, String InstanceDomain,
			String instanceId) {
		return this.instanceConverter.convertToBoundary(this.instances.get(userEmail + "@" + userDomain));
	}

	@Override
	public void deleteAllInstances(String adminDomain, String adminEmail) {
		this.instances.clear();

	}

}
