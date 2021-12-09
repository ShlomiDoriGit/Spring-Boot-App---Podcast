package iob.ActivitiesAPI;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
			"key2Subkey1":52.0
		}
	}
}
 */


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActivityBoundary {
	private ActivityId activityId;
	private String type;
	private ActivityInstance instance;
	private Date createdTimestamp;
	private InvokeBy invokedBy;
	private Map<String, Object> activityAttributes;
}
