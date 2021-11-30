package integrative.servicesMockup;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import integrative.InstancesAPI.InstanceBoundary;
import integrative.converters.InstanceConverter;
import integrative.data.InstanceEntity;
import integrative.logic.InstancesService;

@Service
public class InstacesServiceMockup implements InstancesService {

	private String appName;
	// KeyFormat:
	// <InstanceDomain>"@@"<InstanceId>
	private Map<String, InstanceEntity> instances;
	private InstanceConverter instanceConverter;

	@Autowired
	public void setConverter(InstanceConverter converter) {
		this.instanceConverter = converter;
	}

	@PostConstruct
	public void init() {
		this.instances = Collections.synchronizedMap(new HashMap<>());
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@Override
	public InstanceBoundary createInstance(String userDomain, String userEmail, InstanceBoundary instance) {
		InstanceEntity entity = this.instanceConverter.convertToEntity(instance);
		if (userDomain == null || userDomain == "") {
			throw new RuntimeException("Could not create an instance withot user's domail");
		}
		if (userEmail == null || userEmail == "") {
			throw new RuntimeException("Could not create an instance without user's email");
		}
		if (instance == null)
			throw new RuntimeException("Could not create an instance without instance boundary");

		// User
		if (instance.getCreatedBy().getUserId().getDomain() == null
				|| instance.getCreatedBy().getUserId().getDomain().equals("")) {
			entity.setCreatedByUserDomain(appName);
		}
		if (instance.getCreatedBy().getUserId().getEmail() == null
				|| instance.getCreatedBy().getUserId().getEmail().equals("")) {
			entity.setCreatedByUserEmail(userEmail);
		}
		// Instance
		entity.setInstanceDomain(appName);
		entity.setInstanceId(UUID.randomUUID().toString());
		// Date
		entity.setCreatedTimestamp(new Date());
		this.instances.put(entity.getInstanceDomain() + "@@" + entity.getInstanceId(), entity);
		return this.instanceConverter.convertToBoundary(entity);
	}

	@Override
	public InstanceBoundary updateInstance(String userDomain, String userEmail, String instanceDomain,
			String InstanceId, InstanceBoundary update) {
		InstanceEntity entity = this.instances.get(instanceDomain + "@@" + InstanceId);
		if (entity == null) {
			// No update for InstanceId because not found
			throw new RuntimeException("Could not find instance");
		}
		if (userDomain != null && !userDomain.equals("")) {
			entity.setCreatedByUserDomain(userDomain);
		}
		if (userEmail != null && !userDomain.equals("")) {
			entity.setCreatedByUserEmail(userEmail);
		}
		if (update.getType() != null) {
			entity.setType(update.getType());
		}
		if (update.getName() != null) {
			entity.setName(update.getName());
		}
		if (update.getActive() != null) {
			entity.setActive(update.getActive());
		}
		// CreatedTimeStamp don't need to be changed
		if (update.getLocation() != null) {
			if (update.getLocation().getLat() != null) {
				entity.setLat(update.getLocation().getLat());
			}
			if (update.getLocation().getLng() != null) {
				entity.setLng(update.getLocation().getLng());
			}
		}
		if (update.getInstanceAttributes() != null)
			entity.setInstanceAttributes(update.getInstanceAttributes());
		return this.instanceConverter.convertToBoundary(entity);
	}

	@Override
	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail) {
		return this.instances.values().stream().map(this.instanceConverter::convertToBoundary)
				.collect(Collectors.toList());
	}

	@Override
	public InstanceBoundary getSpecificInstance(String userDomain, String userEmail, String InstanceDomain,
			String instanceId) {
		return this.instanceConverter.convertToBoundary(this.instances.get(InstanceDomain + "@@" + instanceId));
	}

	@Override
	public void deleteAllInstances(String adminDomain, String adminEmail) {
		this.instances.clear();

	}

}
