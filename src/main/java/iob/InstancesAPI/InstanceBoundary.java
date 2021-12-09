package iob.InstancesAPI;

import java.util.Date;
import java.util.Map;

import iob.Boundaries.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstanceBoundary {
	private InstanceId instanceId;
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimestamp;
	private CreatedBy createdBy;
	private Location location;
	private Map<String, Object> instanceAttributes;
}
