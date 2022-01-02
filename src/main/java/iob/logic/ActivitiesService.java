package iob.logic;

import java.util.List;

import iob.ActivitiesAPI.ActivityBoundary;

public interface ActivitiesService {

	public ActivityBoundary invokeActivity(ActivityBoundary activity);

	@Deprecated
	public List<ActivityBoundary> getAllActivities(String adminDomain, String adminEmail);

	public void deleteAllActivities(String adminDomain, String adminEmail);

}
