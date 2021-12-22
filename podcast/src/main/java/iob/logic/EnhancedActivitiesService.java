package iob.logic;

import java.util.List;

import iob.ActivitiesAPI.ActivityBoundary;

public interface EnhancedActivitiesService extends ActivitiesService {

	public List<ActivityBoundary> getAllActivities(String adminDomain, String adminEmail, int page, int size);

}
