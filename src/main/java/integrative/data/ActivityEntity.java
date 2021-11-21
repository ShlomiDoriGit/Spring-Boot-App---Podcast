package integrative.data;

import java.util.Date;
import java.util.Map;

import integrative.ActivitiesAPI.ActivityId;
import integrative.ActivitiesAPI.ActivityInstance;
import integrative.ActivitiesAPI.InvokeBy;

public class ActivityEntity {
	private ActivityId activityId;
	private String type;
	private ActivityInstance instance;
	private Date createdTimestamp;
	private InvokeBy invokedBy;
	private Map<String, Object> activityAttributes;
	
	public ActivityEntity() {
		super();
	}
	
	public ActivityId getActivityId() {
		return activityId;
	}
	public void setActivityId(ActivityId activityId) {
		this.activityId = activityId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ActivityInstance getInstance() {
		return instance;
	}
	public void setInstance(ActivityInstance instance) {
		this.instance = instance;
	}
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public InvokeBy getInvokedBy() {
		return invokedBy;
	}
	public void setInvokedBy(InvokeBy invokedBy) {
		this.invokedBy = invokedBy;
	}
	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}
	public void setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;
	}

	@Override
	public String toString() {
		return "ActivityEntity [activityId=" + activityId + ", type=" + type + ", instance=" + instance
				+ ", createdTimestamp=" + createdTimestamp + ", invokedBy=" + invokedBy + ", activityAttributes="
				+ activityAttributes + "]";
	}
	
}
