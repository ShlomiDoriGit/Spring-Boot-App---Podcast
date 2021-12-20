package iob.InstancesAPI;

import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import iob.data.InstanceEntity;
import iob.logic.EnhancedInstancesService;

@RestController
public class SearchController {
	private EnhancedInstancesService instances;
	
	public SearchController(EnhancedInstancesService instances) {
		super();
		this.instances = instances;
	}
	
	
	@RequestMapping(path="/hello/search/byCritical",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceEntity[] searchByName(
										   @PathVariable("name") String name, 
										   @RequestParam(name="size", required = false, defaultValue = "10") int size, 
											@RequestParam(name="page", required = false, defaultValue = "0") int page){
				//	return this.instances.searchByName(name,size,page).toArray(new InstanceEntity[0]);
		return null;
	}
//	public List<InstanceEntity> findByType(@Param("type") String type, Pageable pageable);
//	public List<InstanceEntity> findByLocation(@Param("lat") double lat,@Param("lng") double lng ,Pageable pageable);
//	public List<InstanceEntity> findByCreate(@Param("creationWindow")  Date createdTimestamp, Pageable pageable);
}
