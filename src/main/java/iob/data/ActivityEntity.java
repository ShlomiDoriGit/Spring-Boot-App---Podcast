package iob.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import iob.ActivitiesAPI.ActivityId;
import iob.converters.IobMapToJsonConverter;
import lombok.Data;

/*
 * ACTIVITIES
 * ------------------------------------------------------------------------------------------------------------------------ -----------------------------------------------------------
 * ACTIVITY_ID  | ACTIVITY_DOMAIN| TYPE         | ACTIVITY_INSTANCE_DOMAIN| ACTIVITY_INSTANCE_ID | MESSAGE_CREATION_TOMESTAMP  | INVOKE_BY_USER_EMAIL |INVOKE_BY_USER_DOMAIN |ACTIVITY_ATTRIBUTES |ORIGIN_ID   |
 * VARCHAR(255) | VARCHAR(255)   | VARCHAR(255) | VARCHAR(255)            | VARCHAR(255)         |  TIMESTAMP                  | VARCHAR(255)         |VARCHAR(255)			 |CLOB                |VARCHAR(255)|
 * <PK>         | <PK>           |              |                         |                      |                             |                      |                      |                    |<FK>        |
 */

@Entity
@Table(name = "ACTIVITIES")
@Data
public class ActivityEntity  {

	@EmbeddedId
	private ActivityId activityId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "ACTIVITY_INSTANCE_DOMAIN")
	private String activityInstanceDomain;
	
	@Column(name = "ACTIVITY_INSTANCE_ID")
	private String activityInstanceId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTimestamp;
	
	@Column(name = "INVOKE_BY_USER_EMAIL")
	private String invokeByUserEmail;
	
	@Column(name = "INVOKE_BY_USER_DOMAIN")
	private String invokeByUserDomain;
	
	@Convert(converter = IobMapToJsonConverter.class)
	@Lob
	private Map<String, Object> activityAttributes;

	@ManyToOne(fetch = FetchType.LAZY)
	private ActivityEntity origin;

	@OneToMany(mappedBy = "origin", fetch = FetchType.LAZY)
	private Set<ActivityEntity> responses = new HashSet<>();

	public void addResponse(ActivityEntity response) {
		this.responses.add(response);
	}

}
