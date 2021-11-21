package integrative.converters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import integrative.ActivitiesAPI.ActivityBoundary;
import integrative.ActivitiesAPI.ActivityId;
import integrative.ActivitiesAPI.ActivityInstance;
import integrative.ActivitiesAPI.InvokeBy;
import integrative.InstancesAPI.InstanceId;
import integrative.UsersRelatedAPI.UserId;
import integrative.data.ActivityEntity;
import integrative.data.UserRole;

@Component
public class ActivityConverter {

	public ActivityEntity convertToEntity(ActivityBoundary boundary) {
		ActivityEntity entity = new ActivityEntity();
		// Default values
		entity.setActivityDomain("2022a.demo");
		entity.setActivityId("112");
		entity.setType("demoActivityType");
		entity.setActivityInstanceDomain("2022a.demo");
		entity.setActivityInstanceId("352");
		entity.setCreatedTimestamp(new Date());
		entity.setInvokeByUserDomain("2022a.demo");
		entity.setInvokeByUserEmail("user3@demo.com");
		entity.setInvokeByUserDomain("user3@demo.com");
		entity.setInvokeByUserEmail("2022a.demo");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("key1", "can be set to any value you wish");
		m.put("key2",52.0);
		entity.setActivityAttributes(m);
		
		if (boundary.getActivityId() != null) {
			if (boundary.getActivityId().getId() != null) {
				entity.setActivityId(boundary.getActivityId().getId());
			}
			if (boundary.getActivityId().getDomain() != null) {
				entity.setActivityDomain(boundary.getActivityId().getDomain());
			}
		}
		if (boundary.getType() != null) {
			entity.setType(boundary.getType());
		} else {
			entity.setType(UserRole.PLAYER.name());
		}
		if (boundary.getInstance() != null && boundary.getInstance().getInstanceId() != null) {
			if (boundary.getInstance().getInstanceId().getId() != null) {
				entity.setActivityInstanceId(boundary.getInstance().getInstanceId().getId());
			}
			if (boundary.getInstance().getInstanceId().getDomain() != null) {
				entity.setActivityInstanceDomain(boundary.getInstance().getInstanceId().getDomain());
			}
		}

		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());

		if (boundary.getInvokedBy() != null && boundary.getInvokedBy().getUserId() != null) {
			if (boundary.getInvokedBy().getUserId().getEmail() != null) {
				entity.setInvokeByUserEmail(boundary.getInvokedBy().getUserId().getEmail());
			}
			if (boundary.getInvokedBy().getUserId().getDomain() != null) {
				entity.setInvokeByUserDomain(boundary.getInvokedBy().getUserId().getDomain());
			}
		}

		entity.setActivityAttributes(boundary.getActivityAttributes());

		return entity;
	}

	public ActivityBoundary convertToBoundary(ActivityEntity entity) {
		ActivityBoundary boundary = new ActivityBoundary();

		boundary.setActivityId(new ActivityId(entity.getActivityDomain(), entity.getActivityId()));
		boundary.setType(entity.getType());
		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		boundary.setInstance(
				new ActivityInstance(new InstanceId(entity.getActivityInstanceDomain(), entity.getActivityId())));
		boundary.setInvokedBy(new InvokeBy(new UserId(entity.getInvokeByUserDomain(), entity.getInvokeByUserEmail())));
		boundary.setActivityAttributes(entity.getActivityAttributes());
		return boundary;
	}
}
