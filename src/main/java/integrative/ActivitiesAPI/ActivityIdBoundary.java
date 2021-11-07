package integrative.ActivitiesAPI;

/*
"domain":"2022a.demo",
"id":"112"
*/
public class ActivityIdBoundary {
	private String domain;
	private String id;
	
	public ActivityIdBoundary() {
	}
	
	public ActivityIdBoundary(String domain, String id) {
		super();
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
