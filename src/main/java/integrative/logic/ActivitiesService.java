package integrative.logic;

import java.util.List;

import integrative.ActivitiesAPI.ActivityBoundary;

public interface ActivitiesService {

	public Object invokeActivity(ActivityBoundary activity);

	public List<ActivityBoundary> getAllActivities(String adminSpace, String adminEmail);

	public void deleteAllActivities(String adminSpace, String adminEmail);

}
