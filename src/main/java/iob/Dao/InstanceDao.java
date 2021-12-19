package iob.Dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import iob.InstancesAPI.InstanceId;

import iob.data.InstanceEntity;

public interface InstanceDao extends PagingAndSortingRepository<InstanceEntity, InstanceId> {

	public List<InstanceEntity> findAllByName(@Param("name") String name, Pageable pageable);
}
