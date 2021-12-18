package iob.Dao;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import iob.ActivitiesAPI.ActivityId;
import iob.data.ActivityEntity;

public interface ActivityDao extends PagingAndSortingRepository<ActivityEntity, ActivityId> {

}
