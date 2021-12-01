package iob.servicesMockup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import iob.ActivitiesAPI.ActivityBoundary;
import iob.converters.ActivityConverter;
import iob.data.ActivityEntity;
import iob.logic.ActivitiesService;

@Service
public class ActivitiesServiceMockup implements ActivitiesService {

	private String appName;
	// KeyFormat:
	// <ActivityDomain>"@@"<ActivityId>
	private Map<String, ActivityEntity> activities;
	private ActivityConverter activityConverter;
	

	@Autowired
	public void setConverter(ActivityConverter activityConverter) {
		this.activityConverter = activityConverter;
	}

	@PostConstruct
	public void init() {
		this.activities = Collections.synchronizedMap(new HashMap<>());
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@Override
	public Object invokeActivity(ActivityBoundary activity) {
		//TODO : After Eyal's answer
		ActivityEntity entity = this.activityConverter.convertToEntity(activity);
		//entity.setActivityId(UUID.randomUUID().toString());
		
		entity.setActivityDomain(appName);
		this.activities.put(entity.getActivityDomain()+"@@"+entity.getActivityId(), entity);
		return this.activityConverter.convertToBoundary(entity);
	}

	@Override
	public List<ActivityBoundary> getAllActivities(String adminDomain, String adminEmail) {
		return this.activities.values().stream()
				.map(this.activityConverter::convertToBoundary).collect(Collectors.toList());
	}

	@Override
	public void deleteAllActivities(String adminDomain, String adminEmail) {
		this.activities.clear();
	}

}
