package iob.Boundaries;
/*
 "lat":32.115139,
 "lng":34.817804
*/

public class Location {

	private Double lat;
	private Double lng;

	public Location() {
	}

	public Location(Double lat, Double lng) {
		this();
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

}