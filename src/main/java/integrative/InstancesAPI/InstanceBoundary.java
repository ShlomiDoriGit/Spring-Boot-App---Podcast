package integrative.InstancesAPI;

import java.util.Date;
import java.util.Map;

import integrative.Boundaries.Location;
import integrative.UsersRelatedAPI.UserId;

/*
 {
	"instanceId":{
		"domain":"2022a.demo",
		"id":"352"
	},
	"type":"dummyInstanceType",
	"name":"demo instance",
	"active":true,
	"createdTimestamp":"2021-10-24T19:55:05.248+0000",
	"createdBy":{
		"userId":{
			"domain":"2022a.demo",
			"email":"user2@demo.com"
		}
	},
	"location":{
		"lat":32.115139,
		"lng":34.817804
	},
	"instanceAttributes":{
		"key1":"can be set to any value you wish",
		"key2":"you can also name the attributes any name you like",
		"key3":6.2,
		"key4":false
	}
}
 */
public class InstanceBoundary {
	private InstanceId instanceId;
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimestamp;
	private CreatedBy createdBy;
	private Location location;
	private Map<String, Object> instanceAttributes;
	
	public InstanceBoundary() {}
	
	public InstanceBoundary(InstanceId instanceId, String type, String name, Boolean active,
			Date createdTimestamp, CreatedBy createdBy, Location location,
			Map<String, Object> instanceAttributes) {
		this();
		this.instanceId = instanceId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.location = location;
		this.instanceAttributes = instanceAttributes;
	}

	public InstanceId getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(InstanceId instanceId) {
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public CreatedBy getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}

	public void setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;
	}
	
}
