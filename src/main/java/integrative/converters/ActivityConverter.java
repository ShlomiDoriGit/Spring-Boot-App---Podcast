package integrative.converters;

import org.springframework.stereotype.Component;

import integrative.ActivitiesAPI.ActivityBoundary;
import integrative.data.ActivityEntity;


@Component
public class ActivityConverter {
	
	public ActivityEntity convertToEntity(ActivityBoundary boundary) {
		ActivityEntity entity = new ActivityEntity();
		//TODO
		return entity;
	}
	
	public ActivityBoundary convertToBoundary(ActivityEntity entity) {
		ActivityBoundary boundary = new ActivityBoundary();
		//TODO
		return boundary;
	}

}
