package integrative.converters;

import org.springframework.stereotype.Component;

import integrative.ActivitiesAPI.ActivityBoundary;
import integrative.ActivitiesAPI.InvokeBy;
import integrative.UsersRelatedAPI.UserId;
import integrative.data.ActivityEntity;
import integrative.data.UserRole;


@Component
public class ActivityConverter {
	
	public ActivityEntity convertToEntity(ActivityBoundary boundary) {
		ActivityEntity entity = new ActivityEntity();
		
		entity.setActivityId(boundary.getActivityId());
		
		if (boundary.getType() != null) {
			entity.setType(boundary.getType());
		}else {
			entity.setType(UserRole.PLAYER.name());
		}

		entity.setInstance(boundary.getInstance());
		if (boundary.getInstance() != null) { // ? Can be null?
		entity.setInstance(boundary.getInstance());
		}else {
			entity.setType("NO_INSTANCE");
		}

		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());

		entity.setInvokedBy(boundary.getInvokedBy());
		
		if (boundary.getInvokedBy().getUserId() != null) {
			if (boundary.getInvokedBy().getUserId().getDomain() != null){
				entity.setInvokedBy(boundary.getInvokedBy());
//				entity.setInvokedBy().setDomain(boundary.getInvokedBy().getUserId().getDomain());
			}
		}
		
		entity.setActivityAttributes(boundary.getActivityAttributes());

		return entity;
	}
	
	public ActivityBoundary convertToBoundary(ActivityEntity entity) {
		ActivityBoundary boundary = new ActivityBoundary();
		
		boundary.setActivityId(entity.getActivityId());		
		boundary.setType(entity.getType());
//		boundary.setInstance(
//			new InvokeBy( new UserId(
//					entity.getDomain(),
//					entity.getEmail())));
//				entity.getFirstName(), 
//				entity.getLastName())); 
		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		boundary.setInvokedBy(entity.getInvokedBy());
		boundary.setActivityAttributes(entity.getActivityAttributes());
			
		return boundary;
	}
}
