package integrative.InstancesAPI;

/*
"domain":"2022a.demo",
"id":"352"
*/

public class InstanceIdBoundary {
	private String domain;
	private String id;
	
	public InstanceIdBoundary() {}
	
	public InstanceIdBoundary(String domain, String id) {
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
