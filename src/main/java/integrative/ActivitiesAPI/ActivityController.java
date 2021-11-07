package integrative.ActivitiesAPI;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

	// Input - Operation Boundary with null operationId
	// Output - Any JSON

	@RequestMapping(path = "/iob/activities",
				method = RequestMethod.POST, 
				produces = MediaType.APPLICATION_JSON_VALUE,
				consumes = MediaType.APPLICATION_JSON_VALUE)
		public ActivityBoundary invokeAnInstanceActivity(@RequestBody ActivityBoundary activity) {
		activity.setActivityId(new ActivityIdBoundary("123","domain"));	
		return activity;
		}

}
