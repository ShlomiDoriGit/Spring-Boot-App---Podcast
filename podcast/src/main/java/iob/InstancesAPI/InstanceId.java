package iob.InstancesAPI;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
"domain":"2022a.demo",
"id":"352"
*/

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstanceId implements Serializable {
	
	@Column(name = "INSTANCE_DOMAIN")
	private String domain;
	@Column(name = "INSTANCE_ID")
	private String id;
}
