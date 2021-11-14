package integrative.ActivitiesAPI;

import integrative.InstancesAPI.InstanceId;

public class ActivityInstance {
	private InstanceId instanceId;

	public ActivityInstance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActivityInstance(InstanceId instanceId) {
		super();
		this.instanceId = instanceId;
	}

	public InstanceId getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(InstanceId instanceId) {
		this.instanceId = instanceId;
	}
	
	
}
