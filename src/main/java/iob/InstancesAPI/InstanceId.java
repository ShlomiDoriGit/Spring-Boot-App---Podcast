package iob.InstancesAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
"domain":"2022a.demo",
"id":"352"
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstanceId {
	private String domain;
	private String id;
}
