package iob.Dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import iob.InstancesAPI.InstanceId;

//import org.springframework.data.repository.CrudRepository;

import iob.data.InstanceEntity;

public interface InstanceDao extends PagingAndSortingRepository<InstanceEntity, InstanceId> {
	// TODO
}
