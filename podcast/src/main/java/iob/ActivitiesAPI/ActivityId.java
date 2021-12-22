package iob.ActivitiesAPI;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
"domain":"2022a.demo",
"id":"112"
*/

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActivityId implements Serializable {
	
	@Column(name = "ACTIVITY_DOMAIN")
	private String domain;
	
	@Column(name = "ACTIVITY_ID")
	private String id;
}
