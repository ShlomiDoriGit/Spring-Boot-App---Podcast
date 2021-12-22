package iob.Boundaries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 "lat":32.115139,
 "lng":34.817804
*/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Location {
	private Double lat;
	private Double lng;
}