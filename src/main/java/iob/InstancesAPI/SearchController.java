package iob.InstancesAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.EnhancedInstancesServiceWithPagination;


@RestController
public class SearchController {
	private EnhancedInstancesServiceWithPagination instances;

	@Autowired
	public SearchController(EnhancedInstancesServiceWithPagination instances) {
		super();
		this.instances = instances;
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/search/byName/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] searchByName(@PathVariable("name") String name,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.instances.searchByName(name, size, page).toArray(new InstanceBoundary[0]);

	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/search/byType/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] searchByType(@PathVariable("type") String type,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.instances.searchByType(type, size, page).toArray(new InstanceBoundary[0]);
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/search/near/{lat}/{lng}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] searchByLocation(@PathVariable("lat") double lat, @PathVariable("lng") double lng,@PathVariable("lng") double distnace,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return instances.searchByLocation(lat, lng,size, page).toArray(new InstanceBoundary[0]);
	}

	@RequestMapping(path = "/iob/instances/{userDomain}/{userEmail}/search/created/{creationWindow}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] searchByCreate(@PathVariable("creationWindow") String creationWindow,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return instances.searchByCreationWindow(creationWindow, size, page).toArray(new InstanceBoundary[0]);
	}
}
