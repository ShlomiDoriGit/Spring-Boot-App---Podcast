package iob.ActivitiesAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
"domain":"2022a.demo",
"id":"112"
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActivityId {
	private String domain;
	private String id;
}
