package iob.data;

import java.util.Date;
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
 * INSTANCES
 * ------------------------------------------------------------------------------------------------------------------------ -----------------------------------------------------------
 * INSTANCES_DOMAIN  | INSTANCES_ID   | TYPE         | NAME         |ACTIVE     | CREATED_TIMESTAMP | CREATED_BY_USER_DOMAIN | CREATED_BY_USER_EMAIL | LAT     | LNG    | INSTANCE_ATRRIBUTES | ORIGIN_ID   |
 * VARCHAR(255)      | VARCHAR(255)   | VARCHAR(255) | VARCHAR(255) | BOOLEAN   | TIMESTAMP         | VARCHAR(255)           | VARCHAR(255)          | DOUBL   | DOUBLE | CLOB                | VARCHAR(255)|
 * <PK>              |                |              |              |           |                   |                        |                       |         |                              | <FK>        |
 */
@Entity
@Table(name="INSTANCES")
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
	
	private InstanceEntity origin;
	private Set<InstanceEntity> responses;

	public InstanceEntity() {
		super();
	}
	@Column(name="INSTANCE_DOMAIN")
	public String getInstanceDomain() {
		return instanceDomain;
	}

	public void setInstanceDomain(String instanceDomain) {
		this.instanceDomain = instanceDomain;
	}
	@Column(name="INSTANCE_ID")
	@Id
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="NAME")
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
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	@Column(name="CREATE_BY_USER_DOMAIN")
	public String getCreatedByUserDomain() {
		return createdByUserDomain;
	}

	public void setCreatedByUserDomain(String createdByUserDomain) {
		this.createdByUserDomain = createdByUserDomain;
	}
	@Column(name="CREATE_BY_USER_EMAIL")
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
	@Convert(converter = IobMapToJsonConverter.class)
	@Lob
	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}

	public void setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public InstanceEntity getOrigin() {
		return origin;
	}
	public void setOrigin(InstanceEntity origin) {
		this.origin = origin;
	}
	@OneToMany(mappedBy = "origin", fetch = FetchType.LAZY)
	public Set<InstanceEntity> getResponses() {
		return responses;
	}
	public void setResponses(Set<InstanceEntity> responses) {
		this.responses = responses;
	}
	public void addResponse (InstanceEntity response) {
		this.responses.add(response);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(instanceId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstanceEntity other = (InstanceEntity) obj;
		return Objects.equals(instanceId, other.instanceId);
	}
	@Override
	public String toString() {
		return "InstanceEntity [instanceDomain=" + instanceDomain + ", instanceId=" + instanceId + ", type=" + type
				+ ", name=" + name + ", active=" + active + ", createdTimestamp=" + createdTimestamp
				+ ", createdByUserDomain=" + createdByUserDomain + ", createdByUserEmail=" + createdByUserEmail
				+ ", lat=" + lat + ", lng=" + lng + ", instanceAttributes=" + instanceAttributes + "]";
	}
	
}
