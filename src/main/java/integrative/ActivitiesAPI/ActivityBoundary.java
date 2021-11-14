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
	private ActivityId activityId;
	private String type;
	private ActivityInstance instance;
	private Date createdTimestamp;
	private InvokeBy invokedBy;
	private Map<String, Object> activityAttributes;

	public ActivityBoundary() {
	}

	public ActivityBoundary(ActivityId activityId, String type, ActivityInstance instance,
			Date createdTimestamp, InvokeBy invokedBy, Map<String, Object> activityAttributes) {
		this();
		this.activityId = activityId;
		this.type = type;
		this.instance = instance;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.activityAttributes = activityAttributes;
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

}
