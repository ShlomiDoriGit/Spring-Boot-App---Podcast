package integrative.ActivitiesAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import integrative.logic.ActivitiesService;

@RestController
public class ActivityController {

	private ActivitiesService activitiesService;
	
	@Autowired
	public ActivityController(ActivitiesService activitiesService) {
		this.activitiesService = activitiesService;
	}
	
	// Input - Operation Boundary with null operationId
	// Output - Any JSON
	

	@RequestMapping(path = "/iob/activities",
				method = RequestMethod.POST, 
				produces = MediaType.APPLICATION_JSON_VALUE,
				consumes = MediaType.APPLICATION_JSON_VALUE)
		public ActivityBoundary invokeAnInstanceActivity(@RequestBody ActivityBoundary activity) {
		return (ActivityBoundary) activitiesService.invokeActivity(activity);
		}

}
