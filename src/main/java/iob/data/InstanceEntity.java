package iob.data;

import java.util.Date;
import java.util.Map;

public class InstanceEntity {

	private String instanceDomain;
	private String instanceId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private String createdByUserDomain;
	private String createdByUserEmail;
	private double lat;
	private double lng;
	private Map<String, Object> instanceAttributes;

	public InstanceEntity() {
		super();
	}

	public String getInstanceDomain() {
		return instanceDomain;
	}

	public void setInstanceDomain(String instanceDomain) {
		this.instanceDomain = instanceDomain;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getCreatedByUserDomain() {
		return createdByUserDomain;
	}

	public void setCreatedByUserDomain(String createdByUserDomain) {
		this.createdByUserDomain = createdByUserDomain;
	}

	public String getCreatedByUserEmail() {
		return createdByUserEmail;
	}

	public void setCreatedByUserEmail(String createdByUserEmail) {
		this.createdByUserEmail = createdByUserEmail;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}

	public void setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;
	}

	@Override
	public String toString() {
		return "InstanceEntity [instanceDomain=" + instanceDomain + ", instanceId=" + instanceId + ", type=" + type
				+ ", name=" + name + ", active=" + active + ", createdTimestamp=" + createdTimestamp
				+ ", createdByUserDomain=" + createdByUserDomain + ", createdByUserEmail=" + createdByUserEmail
				+ ", lat=" + lat + ", lng=" + lng + ", instanceAttributes=" + instanceAttributes + "]";
	}
	
}
