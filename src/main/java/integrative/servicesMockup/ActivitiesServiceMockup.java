package integrative.servicesMockup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import integrative.ActivitiesAPI.ActivityBoundary;
import integrative.converters.ActivityConverter;
import integrative.data.ActivityEntity;
import integrative.logic.ActivitiesService;

public class ActivitiesServiceMockup implements ActivitiesService {

	private Map<String, ActivityEntity> activities;
	private ActivityConverter activityConverter;

	//Constructor
	public ActivitiesServiceMockup(ActivityConverter converter) {
		super();
		this.activities=Collections.synchronizedMap(new HashMap<>());
	}

	@Autowired
	public void setConverter(ActivityConverter activityConverter) {
		this.activityConverter = activityConverter;
	}
	
	
	@Override
	public Object invokeActivity(ActivityBoundary activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityBoundary> getAllActivities(String adminSpace, String adminEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllActivities(String adminSpace, String adminEmail) {
		// TODO Auto-generated method stub
		
	}

}
