package iob.ActivitiesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.EnhancedActivitiesService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ActivityController {

	private EnhancedActivitiesService activitiesService;
	
	@Autowired
	public ActivityController(EnhancedActivitiesService activitiesService) {
		this.activitiesService = activitiesService;
	}
	
	// Input - Operation Boundary with null operationId
	// Output - Any JSON
	

	@RequestMapping(path = "/iob/activities",
				method = RequestMethod.POST, 
				produces = MediaType.APPLICATION_JSON_VALUE,
				consumes = MediaType.APPLICATION_JSON_VALUE)
		public ActivityBoundary invokeAnInstanceActivity(@RequestBody ActivityBoundary activity) {
		return activitiesService.invokeActivity(activity);
		}
	
	@RequestMapping(
			path="/iob/activities/vote",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Object votePodcast (@RequestBody ActivityBoundary command) {
		return this.activitiesService.implementPodcastCommand(command);
	}
	
	@RequestMapping(
			path="/iob/activities/listen",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Object listenPodcast (@RequestBody ActivityBoundary command) {
		return this.activitiesService.implementPodcastCommand(command);
	}

}
