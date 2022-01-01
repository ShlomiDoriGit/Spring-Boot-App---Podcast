package iob.Dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import iob.InstancesAPI.InstanceId;

import iob.data.InstanceEntity;

public interface InstanceDao extends PagingAndSortingRepository<InstanceEntity, InstanceId> {

	public List<InstanceEntity> findByName(
			@Param("name") String name,
			Pageable pageable);
	public List<InstanceEntity> findByType(
			@Param("type") String type,
			Pageable pageable);
	public List<InstanceEntity> findByLatAndLng(
			@Param("lat") double lat,
			@Param("lng") double lng ,
			Pageable pageable);
//	public List<InstanceEntity> findByCreatedTimestamp(
//			@Param("creationWindow")  Date createdTimestamp,
//			Pageable pageable);
	
	public List<InstanceEntity> findAllByCreatedTimestampBetween(
			@Param("minimumValue") Date startTimestamp, @Param("maximumValue") Date endTimestamp, 
			Pageable pageable);


}
