package iob.Dao;

import org.springframework.data.repository.CrudRepository;

import iob.data.ActivityEntity;

public interface ActivityDao  extends CrudRepository<ActivityEntity, String> {

}
