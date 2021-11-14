package integrative.ActivitiesAPI;

/*
"domain":"2022a.demo",
"id":"112"
*/
public class ActivityId {
	private String domain;
	private String id;
	
	public ActivityId() {
	}
	
	public ActivityId(String domain, String id) {
		this();
		this.domain = domain;
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
