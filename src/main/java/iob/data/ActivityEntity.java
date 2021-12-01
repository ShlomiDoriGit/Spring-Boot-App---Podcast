package iob.data;

import java.util.Date;
import java.util.Map;

public class ActivityEntity {
	private String activityId;
	private String activityDomain;
	private String type;
	private String activityInstanceDomain;
	private String activityInstanceId;
	private Date createdTimestamp;
	private String invokeByUserEmail;
	private String invokeByUserDomain;
	private Map<String, Object> activityAttributes;

	public ActivityEntity() {
		super();
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityDomain() {
		return activityDomain;
	}

	public void setActivityDomain(String activityDomain) {
		this.activityDomain = activityDomain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActivityInstanceDomain() {
		return activityInstanceDomain;
	}

	public void setActivityInstanceDomain(String activityInstanceDomain) {
		this.activityInstanceDomain = activityInstanceDomain;
	}

	public String getActivityInstanceId() {
		return activityInstanceId;
	}

	public void setActivityInstanceId(String activityInstanceId) {
		this.activityInstanceId = activityInstanceId;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getInvokeByUserEmail() {
		return invokeByUserEmail;
	}

	public void setInvokeByUserEmail(String invokeByUserEmail) {
		this.invokeByUserEmail = invokeByUserEmail;
	}

	public String getInvokeByUserDomain() {
		return invokeByUserDomain;
	}

	public void setInvokeByUserDomain(String invokeByUserDomain) {
		this.invokeByUserDomain = invokeByUserDomain;
	}

	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public void setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;
	}

	@Override
	public String toString() {
		return "ActivityEntity [activityId=" + activityId + ", activityDomain=" + activityDomain + ", type=" + type
				+ ", activityInstanceDomain=" + activityInstanceDomain + ", activityInstanceId=" + activityInstanceId
				+ ", createdTimestamp=" + createdTimestamp + ", invokeByUserEmail=" + invokeByUserEmail
				+ ", invokeByUserDomain=" + invokeByUserDomain + ", activityAttributes=" + activityAttributes + "]";
	}

}
