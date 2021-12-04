package iob.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import iob.converters.IobMapToJsonConverter;



/*
 * ACTIVITIES
 * ------------------------------------------------------------------------------------------------------------------------ -----------------------------------------------------------
 * ACTIVITY_ID  | ACTIVITY_DOMAIN| TYPE         | ACTIVITY_INSTANCE_DOMAIN| ACTIVITY_INSTANCE_ID | MESSAGE_CREATION_TOMESTAMP  | INVOKE_BY_USER_EMAIL |INVOKE_BY_USER_DOMAIN |ACTIVITY_ATTRIBUTES |ORIGIN_ID   |
 * VARCHAR(255) | VARCHAR(255)   | VARCHAR(255) | VARCHAR(255)            | VARCHAR(255)         |  TIMESTAMP                  | VARCHAR(255)         |VARCHAR(255)			 |CLOB                |VARCHAR(255)|
 * <PK>         |                |              |                         |                      |                             |                      |                      |                    |<FK>        |
 */
@Entity
@Table(name="ACTIVITIES")
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

	private ActivityEntity origin;
	private Set<ActivityEntity> responses;

	public ActivityEntity() {
		this.responses = new HashSet<>();
	}
	
	@Column(name="ACTIVITY_ID")
	@Id
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	@Column(name="ACTIVITY_DOMAIN")
	public String getActivityDomain() {
		return activityDomain;
	}

	public void setActivityDomain(String activityDomain) {
		this.activityDomain = activityDomain;
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="ACTIVITY_INSTANCE_DOMAIN")
	public String getActivityInstanceDomain() {
		return activityInstanceDomain;
	}

	public void setActivityInstanceDomain(String activityInstanceDomain) {
		this.activityInstanceDomain = activityInstanceDomain;
	}
	@Column(name="ACTIVITY_INSTANCE_ID")
	public String getActivityInstanceId() {
		return activityInstanceId;
	}

	public void setActivityInstanceId(String activityInstanceId) {
		this.activityInstanceId = activityInstanceId;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	@Column(name="INVOKE_BY_USER_EMAIL")
	public String getInvokeByUserEmail() {
		return invokeByUserEmail;
	}

	public void setInvokeByUserEmail(String invokeByUserEmail) {
		this.invokeByUserEmail = invokeByUserEmail;
	}
	@Column(name="INVOKE_BY_USER_DOMAIN")
	public String getInvokeByUserDomain() {
		return invokeByUserDomain;
	}

	public void setInvokeByUserDomain(String invokeByUserDomain) {
		this.invokeByUserDomain = invokeByUserDomain;
	}
	@Convert(converter = IobMapToJsonConverter.class)
	@Lob
	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public void setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public ActivityEntity getOrigin() {
		return origin;
	}

	public void setOrigin(ActivityEntity origin) {
		this.origin = origin;
	}
	@OneToMany(mappedBy = "origin", fetch = FetchType.LAZY)
	public Set<ActivityEntity> getResponses() {
		return responses;
	}

	public void setResponses(Set<ActivityEntity> responses) {
		this.responses = responses;
	}
	public void addResponse (ActivityEntity response) {
		this.responses.add(response);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(activityId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityEntity other = (ActivityEntity) obj;
		return Objects.equals(activityId, other.activityId);
	}

	@Override
	public String toString() {
		return "ActivityEntity [activityId=" + activityId + ", activityDomain=" + activityDomain + ", type=" + type
				+ ", activityInstanceDomain=" + activityInstanceDomain + ", activityInstanceId=" + activityInstanceId
				+ ", createdTimestamp=" + createdTimestamp + ", invokeByUserEmail=" + invokeByUserEmail
				+ ", invokeByUserDomain=" + invokeByUserDomain + ", activityAttributes=" + activityAttributes + "]";
	}

}
