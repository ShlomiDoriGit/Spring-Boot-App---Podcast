package iob.InstancesAPI;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import iob.logic.EnhancedInstancesServiceWithPagging;

@RestController
public class SearchController {
	private EnhancedInstancesServiceWithPagging instances;
	
	@Autowired
	public SearchController(EnhancedInstancesServiceWithPagging instances) {
		super();
		this.instances = instances;
	}
	
	
	@RequestMapping(path="/iob/instances/{userDomain}/{userEmail}/search/byName/{name}?size={size}&page={page}",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] searchByName(
           @PathVariable("name") String name, 
		   @RequestParam(name="size", required = false, defaultValue = "10") int size, 
		   @RequestParam(name="page", required = false, defaultValue = "0") int page){
		return this.instances.searchByName(name, size, page).toArray(new InstanceBoundary[0]);
		
	}
	@RequestMapping(path="/iob/instances/{userDomain}/{userEmail}/search/byType/{type}?size={size}&page={page}",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] searchByType(
	           @PathVariable("name") String name, 
			   @RequestParam(name="size", required = false, defaultValue = "10") int size, 
			   @RequestParam(name="page", required = false, defaultValue = "0") int page)
	{
		return this.instances.searchByType(name, size, page).toArray(new InstanceBoundary[0]);
	}
	
	
	
	@RequestMapping(path="/iob/instances/{userDomain}/{userEmail}/search/near/{lat}/{lng}/{distance}?size={size}&page={page}",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] searchByLocation(
	@PathVariable("lat") double lat,
	@PathVariable("lng") double lng ,
	@RequestParam(name="size", required = false, defaultValue = "10") int size, 
	@RequestParam(name="page", required = false, defaultValue = "0") int page)
	{
		return instances.searchByLocation(lat, lng, size, page).toArray(new InstanceBoundary[0]);
	}
	
	@RequestMapping(path="/iob/instances/{userDomain}/{userEmail}/search/created/{creationWindow}?size={size}&page={page}",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] searchByCreate(
			 @PathVariable("creationWindow")  Date createdTimestamp,
			 @RequestParam(name="size", required = false, defaultValue = "10") int size, 
		     @RequestParam(name="page", required = false, defaultValue = "0") int page)
	{
		return instances.searchByCreate(createdTimestamp, size, page).toArray(new InstanceBoundary[0]);
	}
}
