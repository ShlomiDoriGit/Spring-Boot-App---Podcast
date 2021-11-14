package integrative.ActivitiesAPI;

import java.util.Date;
import java.util.Map;

/*
 {
	"activityId":{
		"domain":"2022a.demo",
		"id":"112"
	},
	"type":"demoActivityType",
	"instance":{
		"instanceId":{
			"domain":"2022a.demo",
			"id":"352"
		}
	},
	"createdTimestamp":"2021-10-24T19:57:23.114+0000",
	"invokedBy":{
		"userId":{
			"domain":"2022a.demo",
			"email":"user3@demo.com"
		}
	},
	"activityAttributes":{
		"key1":"can be set to any value you wish",
		"key2":{
			"key2Subkey1":"can be nested json"
		}
	}
}
 */
public class ActivityBoundary {
	private ActivityIdBoundary activityId;
	private String type;
	private ActivityInstanceBoundary instance;
	private Date createdTimestamp;
	private InvokeByBoundary invokedBy;
	private Map<String, Object> activityAttributes;

	public ActivityBoundary() {
	}

	public ActivityBoundary(ActivityIdBoundary activityId, String type, ActivityInstanceBoundary instance,
			Date createdTimestamp, InvokeByBoundary invokedBy, Map<String, Object> activityAttributes) {
		this();
		this.activityId = activityId;
		this.type = type;
		this.instance = instance;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.activityAttributes = activityAttributes;
	}

	public ActivityIdBoundary getActivityId() {
		return activityId;
	}

	public void setActivityId(ActivityIdBoundary activityId) {
		this.activityId = activityId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ActivityInstanceBoundary getInstance() {
		return instance;
	}

	public void setInstance(ActivityInstanceBoundary instance) {
		this.instance = instance;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public InvokeByBoundary getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(InvokeByBoundary invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public void setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;
	}

}
